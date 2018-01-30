package com.work.wordSearchService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileWordOccurrences implements WordSearchService{

    private void countWordOccurrencesInLine(String line, int lineCounter, String word, String fileName, List<Result> resultList) {
        int wordIndex;
        wordIndex = line.indexOf(word);
        Result result;
        while (wordIndex >= 0) {
            result = new Result(lineCounter, wordIndex, fileName);
            resultList.add(result);
            wordIndex = line.indexOf(word, wordIndex + word.length());
        }
    }

    private List<Result> getFileWordOccurrences(String fileName, String word) {
        BufferedReader bufferedReader = null;
        List<Result> resultList = new ArrayList<>();
        int lineCounter = 1;
        try {
            FileReader fr = FileIO.openFile(fileName);
            bufferedReader = new BufferedReader(fr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                countWordOccurrencesInLine(line, lineCounter, word, fileName, resultList);
                lineCounter++;
            }
            Collections.sort(resultList);
            return resultList;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read File.", e);
        } finally {
            FileIO.closeFile(bufferedReader);
        }
    }

    @Override
    public List<Result> search(Query query) {
        return getFileWordOccurrences(query.fileName, query.word);
    }
}
