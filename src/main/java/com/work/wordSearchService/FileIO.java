package com.work.wordSearchService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileIO {
    private static Logger logger = LogManager.getLogger();

    public static FileReader openFile(String fileName) {
        try {
            return new FileReader(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }

    public static void closeFile(Reader fr) {
        if (fr != null) {
            try {
                fr.close();
            } catch (IOException e) {
                logger.error("Failed to close", e);
            }
        }
    }
}
