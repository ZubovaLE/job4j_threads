package ru.job4j.async;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] results = new Sums[n];

        for (int i = 0; i < n; i++) {
            results[i] = calculateSums(matrix, i);
        }

        return results;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        int n = matrix.length;
        Sums[] results = new Sums[n];
        CompletableFuture<Void>[] futures = new CompletableFuture[n];

        for (int i = 0; i < n; i++) {
            results[i] = new Sums();
            final int index = i;
            futures[i] = CompletableFuture.runAsync(() -> {
                results[index] = calculateSums(matrix, index);
            });
        }

        CompletableFuture.allOf(futures).join();
        return results;
    }

    private static Sums calculateSums(int[][] matrix, int index) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowSum += matrix[index][j];
            colSum += matrix[j][index];
        }
        Sums sums = new Sums();
        sums.setRowSum(rowSum);
        sums.setColSum(colSum);
        return sums;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sums {

        private int rowSum;
        private int colSum;

    }

}