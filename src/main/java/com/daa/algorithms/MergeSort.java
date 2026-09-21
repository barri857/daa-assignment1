package com.daa.algorithms;

import com.daa.metrics.Metrics;

public class MergeSort {
    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        int[] buffer = new int[a.length];
        metrics.startTimer();
        sort(a, buffer, 0, a.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void sort(int[] a, int[] buffer, int low, int high, Metrics metrics) {
        metrics.enterRecursion();
        if (high - low + 1 <= CUTOFF) {
            insertionSort(a, low, high, metrics);
            metrics.exitRecursion();
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, buffer, low, mid, metrics);
        sort(a, buffer, mid + 1, high, metrics);

        merge(a, buffer, low, mid, high, metrics);
        metrics.exitRecursion();
    }

    private static void merge(int[] a, int[] buffer, int low, int mid, int high, Metrics metrics) {
        System.arraycopy(a, low, buffer, low, high - low + 1);

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > high) {
                a[k] = buffer[i++];
            } else {
                metrics.incrementComparisons();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] a, int low, int high, Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= low) {
                metrics.incrementComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}