package com.daa.algorithms;

import com.daa.metrics.Metrics;
import java.util.Random;

public class QuickSelect {
    private static final Random random = new Random();

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input array or index k out of bounds.");
        }
        metrics.startTimer();
        int result = select(a, 0, a.length - 1, k, metrics);
        metrics.stopTimer();
        return result;
    }

    private static int select(int[] a, int low, int high, int k, Metrics metrics) {
        while (low <= high) {
            metrics.enterRecursion();
            if (low == high) {
                metrics.exitRecursion();
                return a[low];
            }

            int pivotIdx = low + random.nextInt(high - low + 1);
            swap(a, low, pivotIdx);

            int[] p = partition3Way(a, low, high, metrics);

            if (k >= p[0] && k <= p[1]) {
                metrics.exitRecursion();
                return a[k];
            } else if (k < p[0]) {
                metrics.exitRecursion();
                high = p[0] - 1;
            } else {
                metrics.exitRecursion();
                low = p[1] + 1;
            }
        }
        throw new IllegalStateException("Should not reach here");
    }

    private static int[] partition3Way(int[] a, int low, int high, Metrics metrics) {
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