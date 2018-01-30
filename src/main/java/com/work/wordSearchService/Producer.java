package com.work.wordSearchService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private Logger logger = LogManager.getLogger(Producer.class);
    private BlockingQueue<Query> blockingQueue;
    private Query query;

    Producer(BlockingQueue<Query> blockingQueue, Query query) {
        this.blockingQueue = blockingQueue;
        this.query = query;
    }

    private void addToQueue(Query q) throws InterruptedException{
        blockingQueue.put(q);
        logger.debug("Adding query {} by producer {}", q, this);
    }


    private void execute(Query q) throws InterruptedException{
        File folder = new File(q.fileName);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles!= null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    addToQueue(new Query(q.fileName + '/' + file.getName(), q.word, false));
                } else {
                    execute(new Query(q.fileName + '/' + file.getName(), q.word, q.recursive));
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            logger.debug("hit producer:  {}", this);
            execute(query);
            logger.debug("Producer end");
        } catch (InterruptedException e) {
            StackTraceElement elements[] = e.getStackTrace();
            for (StackTraceElement element : elements) {
                logger.log(Level.WARN, element.getMethodName());
            }
            throw new RuntimeException(e);
        }
    }
}
