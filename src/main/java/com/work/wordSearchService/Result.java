package com.work.wordSearchService;

public class Result implements Comparable<Result> {
    private int lineNumber;
    private int positionNumber;
    private String fileName;

    Result(int x, int y, String fileName) {
        this.lineNumber = x;
        this.positionNumber = y;
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "(" + lineNumber + ", " + positionNumber + "," + fileName + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Result)) {
            return false;
        }

        Result result = (Result) obj;
        return this.lineNumber == result.lineNumber && this.positionNumber == result.positionNumber && this.fileName.equals(result.fileName);
    }

    @Override
    public int hashCode() {
        return this.fileName.hashCode() ^ this.positionNumber ^ this.lineNumber;
    }

    @Override
    public int compareTo(Result o) {
        int compare = this.fileName.compareTo(o.fileName);
        if (compare == 0) {
            if (this.lineNumber == o.lineNumber) {
                return this.positionNumber - o.positionNumber;
            } else {
                return this.lineNumber - o.lineNumber;
            }
        }
        return compare;
    }
}
