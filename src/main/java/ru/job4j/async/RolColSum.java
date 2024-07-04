package ru.job4j.async;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        int[] rowSums = new int[n];
        int[] colSums = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rowSums[i] += matrix[i][j];
                colSums[j] += matrix[i][j];
            }
        }

        Sums[] result = new Sums[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Sums(rowSums[i], colSums[i]);
        }

        return result;
    }


//    public static Sums[] asyncSum(int[][] matrix) {
//
//    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Sums {

        private int rowSum;
        private int colSum;

    }

}