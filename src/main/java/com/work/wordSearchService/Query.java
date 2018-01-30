package com.work.wordSearchService;

public class Query {
    public String fileName;
    public String word;
    public boolean recursive;

    public Query(String fileName, String word, boolean recursive) {
        this.fileName = fileName;
        this.word = word;
        this.recursive = recursive;
    }

    @Override
    public String toString() {
        return "(" + this.fileName + ", " + this.word + ", " + this.recursive + ")";
    }
}
