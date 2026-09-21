package com.daa.algorithms;

import com.daa.metrics.Metrics;
import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        metrics.startTimer();
        sort(a, 0, a.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void sort(int[] a, int low, int high, Metrics metrics) {
        while (low < high) {
            metrics.enterRecursion();
            int pivotIdx = low + random.nextInt(high - low + 1);
            swap(a, low, pivotIdx);

            int[] p = partition3Way(a, low, high, metrics);

            if (p[0] - low < high - p[1]) {
                sort(a, low, p[0] - 1, metrics);
                metrics.exitRecursion();
                low = p[1] + 1;
            } else {
                sort(a, p[1] + 1, high, metrics);
                metrics.exitRecursion();
                high = p[0] - 1;
            }
        }
    }

    public static int[] partition3Way(int[] a, int low, int high, Metrics metrics) {
        int pivot = a[low];
        int lt = low;
        int gt = high;
        int i = low + 1;

        while (i <= gt) {
            metrics.incrementComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else if (a[i] > pivot) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}