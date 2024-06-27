package ru.job4j.pools;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@RequiredArgsConstructor
public class ParallelSearchTask<T> extends RecursiveTask<Long> {

    private static final ForkJoinPool pool = new ForkJoinPool();
    private final T[] array;
    private final T key;
    private final int start;
    private final int end;

    public long findIndexByKey(T[] array, T key) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearchTask<T>(array, key, 0, array.length));
    }

    private int linearSearch(T[] array, T key, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i].equals(key)) {
                return i;
            }
        }
        return -1; // Элемент не найден
    }

    @Override
    protected Long compute() {
        if (end - start <= 10) {
            return (long) linearSearch(array, key, start, end);
        } else {
            int mid = start + (end - start) / 2;
            ParallelSearchTask<T> task1 = new ParallelSearchTask<T>(array, key, start, mid);
            ParallelSearchTask<T> task2 = new ParallelSearchTask<T>(array, key, mid, end);

            task1.fork();
            task2.fork();
            Long result2 = task2.compute();
            Long result1 = task1.join();

            if (result1 != -1) {
                return result1;
            } else {
                return result2;
            }
        }
    }

}