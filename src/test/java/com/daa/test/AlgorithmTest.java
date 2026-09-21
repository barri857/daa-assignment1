package com.daa.test;

import com.daa.algorithms.MergeSort;
import com.daa.algorithms.QuickSort;
import com.daa.algorithms.QuickSelect;
import com.daa.metrics.Metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class AlgorithmTest {

    @Test
    void testMergeSortCorrectness() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int[] arr1 = rand.ints(500, 0, 10000).toArray();
            int[] arr2 = arr1.clone();

            MergeSort.sort(arr1, new Metrics());
            Arrays.sort(arr2);

            assertArrayEquals(arr2, arr1);
        }
    }

    @Test
    void testQuickSortCorrectness() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int[] arr1 = rand.ints(500, 0, 10000).toArray();
            int[] arr2 = arr1.clone();

            QuickSort.sort(arr1, new Metrics());
            Arrays.sort(arr2);

            assertArrayEquals(arr2, arr1);
        }
    }

    @Test
    void testQuickSelectCorrectness() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int[] arr1 = rand.ints(500, 0, 10000).toArray();
            int[] arr2 = arr1.clone();

            int k = rand.nextInt(arr1.length);
            int selected = QuickSelect.select(arr1, k, new Metrics());

            Arrays.sort(arr2);
            assertEquals(arr2[k], selected);
        }
    }

    @Test
    void testEdgeCases() {
        Metrics metrics = new Metrics();

        int[] empty = new int[0];
        MergeSort.sort(empty, metrics);
        QuickSort.sort(empty, metrics);
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(empty, 0, metrics));

        int[] single = {42};
        MergeSort.sort(single, metrics);
        assertEquals(42, single[0]);

        int[] allEqual = {5, 5, 5, 5, 5};
        QuickSort.sort(allEqual, metrics);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, allEqual);

        int[] sorted = {1, 2, 3, 4, 5};
        MergeSort.sort(sorted, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    void testQuickSortDepthCheck() {
        int n = 100_000;
        int[] sortedArray = new int[n];
        for (int i = 0; i < n; i++) sortedArray[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(sortedArray, metrics);

        double maxAllowedDepth = 2 * (Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "Depth " + metrics.getMaxDepth() + " exceeded limit " + maxAllowedDepth);
    }
}