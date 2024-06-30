package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParallelSearchTaskTest {

    @Test
    public void findIndexByKeyWhenInteger() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};

        long result = ParallelSearchTask.findIndexByKey(array, 3, 0, array.length);

        assertEquals(2, result);
    }

    @Test
    public void findIndexByKeyWhenIntegerAndTwoKeysThenFindFirst() {
        Integer[] array = new Integer[]{1, 3, 3, 4, 5, 6};

        long result = ParallelSearchTask.findIndexByKey(array, 3, 0, array.length);

        assertEquals(1, result);
    }

    @Test
    public void findIndexByKeyFromMidWhenInteger() {
        Integer[] array = new Integer[]{1, 3, 3, 4, 5, 6};

        long result = ParallelSearchTask.findIndexByKey(array, 3, 2, array.length);

        assertEquals(2, result);
    }

    @Test
    public void findIndexByKeyWhenString() {
        String[] array = new String[]{"a", "key", "c", "d", "e"};

        long result = ParallelSearchTask.findIndexByKey(array, "key", 0, array.length);

        assertEquals(1, result);
    }

    @Test
    public void findIndexByKeyWhenStringAndTwoKeysThenFindFirst() {
        String[] array = new String[]{"a", "b", "key", "c", "key"};

        long result = ParallelSearchTask.findIndexByKey(array, "key", 0, array.length);

        assertEquals(2, result);
    }

    @Test
    public void findIndexByKeyFromMidWhenString() {
        String[] array = new String[]{"a", "key", "c", "key", "d"};

        long result = ParallelSearchTask.findIndexByKey(array, "key", 2, array.length);

        assertEquals(3, result);
    }

}