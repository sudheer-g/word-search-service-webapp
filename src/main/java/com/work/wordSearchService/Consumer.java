package com.work.wordSearchService;

import org.apache.logging.log4j.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable {
    private Logger logger = LogManager.getLogger(Consumer.class);
    private List<Result> resultList;
    private BlockingQueue<Query> blockingQueue;
    private AtomicBoolean end;

    Consumer(BlockingQueue<Query> blockingQueue, List<Result> resultList, AtomicBoolean end) {
        this.blockingQueue = blockingQueue;
        this.resultList = resultList;
        this.end = end;
    }

    private Query takeFromQueue() throws InterruptedException {
        Query q = blockingQueue.poll(100,TimeUnit.MILLISECONDS);
        logger.debug("Consumer {} took: {}", this, q);
        return q;
    }

    private void execute() throws InterruptedException {
        FileWordOccurrences fo = new FileWordOccurrences();
        Query q;
        while (true) {
            q = takeFromQueue();
            if (q != null) {
                try {
                    List<Result> results = fo.search(q);
                    resultList.addAll(results);
                } catch (Exception e) {
                    logger.warn("Failed to search for query {}", q);
                }
            }
            if (blockingQueue.isEmpty() && end.get()) {
                break;
            }
        }
    }

    @Override
    public void run() {
        try {
            execute();
            logger.debug("Consumer End");
        } catch (InterruptedException e) {
            StackTraceElement elements[] = e.getStackTrace();
            for (StackTraceElement element : elements) {
                logger.warn(element.getMethodName());
            }
            throw new RuntimeException(e);
        }
    }
}
