package com.daa.benchmark;

import com.daa.algorithms.MergeSort;
import com.daa.algorithms.QuickSort;
import com.daa.algorithms.QuickSelect;
import com.daa.metrics.Metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] INPUT_TYPES = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            writer.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (int n : SIZES) {
                for (String inputType : INPUT_TYPES) {
                    runBenchmarkForAlgorithm("MergeSort", inputType, n, writer);
                    runBenchmarkForAlgorithm("QuickSort", inputType, n, writer);
                    runBenchmarkForAlgorithm("QuickSelect", inputType, n, writer);
                }
            }
            System.out.println("Benchmark finished. Results saved to results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runBenchmarkForAlgorithm(String algo, String inputType, int n, PrintWriter writer) {
        long[] times = new long[RUNS];
        long[] comparisons = new long[RUNS];
        int[] depths = new int[RUNS];

        for (int run = 0; run < RUNS; run++) {
            int[] data = generateInput(inputType, n);
            Metrics metrics = new Metrics();

            if (algo.equals("MergeSort")) {
                MergeSort.sort(data, metrics);
            } else if (algo.equals("QuickSort")) {
                QuickSort.sort(data, metrics);
            } else if (algo.equals("QuickSelect")) {
                int k = n / 2;
                QuickSelect.select(data, k, metrics);
            }

            times[run] = metrics.getElapsedTimeNs();
            comparisons[run] = metrics.getComparisons();
            depths[run] = metrics.getMaxDepth();
        }

        Arrays.sort(times);
        Arrays.sort(comparisons);
        Arrays.sort(depths);

        int medianIdx = RUNS / 2;
        double timeMs = times[medianIdx] / 1_000_000.0;
        long medianComp = comparisons[medianIdx];
        int medianDepth = depths[medianIdx];

        writer.printf("%s,%s,%d,%.4f,%d,%d%n", algo, inputType, n, timeMs, medianComp, medianDepth);
    }

    private static int[] generateInput(String type, int n) {
        int[] arr = new int[n];
        Random rand = new Random(42 + n);
        switch (type) {
            case "random":
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt();
                break;
            case "sorted":
                for (int i = 0; i < n; i++) arr[i] = i;
                break;
            case "duplicates":
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(10);
                break;
        }
        return arr;
    }
}