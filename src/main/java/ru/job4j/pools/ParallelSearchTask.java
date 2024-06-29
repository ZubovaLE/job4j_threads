package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Class for parallel search of an element's index in an array.
 * If the subarray size is not greater than threshold, a linear search is used.
 *
 * @param <T> The type of the array elements.
 */
public class ParallelSearchTask<T> extends RecursiveTask<Integer> {

    private static final ForkJoinPool pool = new ForkJoinPool();
    private static final int THRESHOLD = 10;
    private final T[] array;
    private final T key;
    private final int start;
    private final int end;

    private ParallelSearchTask(T[] array, T key, int start, int end) {
        this.array = array;
        this.key = key;
        this.start = start;
        this.end = end;
    }

    /**
     * Executes the parallel search task.
     * This method splits the task into subtasks if the size of the array segment is above the threshold.
     * Otherwise, it performs a linear search on the segment.
     *
     * @param array The array in which we want to find the key.
     * @param key   The key to be searched for.
     * @param from  The starting index for the search (inclusive).
     * @param to    The ending index for the search (exclusive).
     * @param <T>   The generic type parameter representing the type of elements in the array.
     * @return the index of the found key, or -1 if the key is not found.
     */
    public static <T> int findIndexByKey(T[] array, T key, int from, int to) {
        return pool.invoke(new ParallelSearchTask<T>(array, key, from, to));
    }

    private int linearSearch(T[] array, T key, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            return linearSearch(array, key, start, end);
        } else {
            int mid = start + (end - start) / 2;
            ParallelSearchTask<T> task1 = new ParallelSearchTask<T>(array, key, start, mid);
            ParallelSearchTask<T> task2 = new ParallelSearchTask<T>(array, key, mid, end);

            task1.fork();
            Integer result2 = task2.compute();
            Integer result1 = task1.join();

            return result1 != -1 ? result1 : result2;
        }
    }

}