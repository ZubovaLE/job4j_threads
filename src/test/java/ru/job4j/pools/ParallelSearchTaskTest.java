package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParallelSearchTaskTest {

    @Test
    public void linearSearchWhenIntegerArray() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};

        int result = ParallelSearchTask.findIndexByKey(array, 3, 0, array.length);

        assertEquals(2, result);
    }

    @Test
    public void linearSearchWhenIntegerArrayAndFoundTwoKeysThenReturnFirst() {
        Integer[] array = new Integer[]{1, 3, 3, 4, 5, 6};

        int result = ParallelSearchTask.findIndexByKey(array, 3, 0, array.length);

        assertEquals(1, result);
    }

    @Test
    public void linearSearchFromMidWhenIntegerArray() {
        Integer[] array = new Integer[]{1, 3, 3, 4, 5, 6};

        int result = ParallelSearchTask.findIndexByKey(array, 3, 2, array.length);

        assertEquals(2, result);
    }

    @Test
    public void linearSearchWhenStringArray() {
        String[] array = new String[]{"a", "key", "c", "d", "e"};

        int result = ParallelSearchTask.findIndexByKey(array, "key", 0, array.length);

        assertEquals(1, result);
    }

    @Test
    public void linearSearchWhenStringArrayAndFoundTwoKeysThenReturnFirst() {
        String[] array = new String[]{"a", "b", "key", "c", "key"};

        int result = ParallelSearchTask.findIndexByKey(array, "key", 0, array.length);

        assertEquals(2, result);
    }

    @Test
    public void linearSearchFromMidWhenWhenStringArray() {
        String[] array = new String[]{"a", "key", "c", "key", "d"};

        int result = ParallelSearchTask.findIndexByKey(array, "key", 2, array.length);

        assertEquals(3, result);
    }

    @Test
    public void recursiveSearchWhenIntegerArray() {
        Integer[] array = new Integer[20];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        int result = ParallelSearchTask.findIndexByKey(array, 13, 0, array.length);

        assertEquals(13, result);
    }

    @Test
    public void recursiveSearchWhenStringArray() {
        String[] array = new String[20];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(i);
        }

        int result = ParallelSearchTask.findIndexByKey(array, "13", 0, array.length);

        assertEquals(13, result);
    }

    @Test
    public void findIndexByKeyWhenNoSuchKey() {
        Integer[] array = {1, 2, 3, 4, 5};

        int result = ParallelSearchTask.findIndexByKey(array, 6, 0, array.length);

        assertEquals(-1, result);
    }

}