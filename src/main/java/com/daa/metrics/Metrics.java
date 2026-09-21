package com.daa.metrics;

public class Metrics {
    private long comparisons = 0;
    private int maxDepth = 0;
    private int currentDepth = 0;
    private long startTime = 0;
    private long elapsedTimeNs = 0;

    public void reset() {
        comparisons = 0;
        maxDepth = 0;
        currentDepth = 0;
        startTime = 0;
        elapsedTimeNs = 0;
    }

    public void startTimer() {
        this.startTime = System.nanoTime();
    }

    public void stopTimer() {
        this.elapsedTimeNs = System.nanoTime() - startTime;
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public long getComparisons() { return comparisons; }
    public int getMaxDepth() { return maxDepth; }
    public long getElapsedTimeNs() { return elapsedTimeNs; }
    public double getElapsedTimeMs() { return elapsedTimeNs / 1_000_000.0; }
}