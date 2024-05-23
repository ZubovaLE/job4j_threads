package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamExample {
    public static void main(String[] args) {
        System.out.println("Example one: multiplying a list of integers in a parallel stream");
        List<Integer> exampleOne = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = exampleOne.parallelStream();
        System.out.println("Stream is parallel: " + stream.isParallel());
        Optional<Integer> multiplication = stream.reduce((a, b) -> a * b);
        System.out.println(multiplication.get());

        System.out.println("\nExample two: converting a parallel stream to a sequential one");
        IntStream parallel = IntStream.range(1, 100).parallel();
        System.out.println("Stream is parallel: " + parallel.isParallel());
        IntStream sequential = parallel.sequential();
        System.out.println("Stream is parallel: " + sequential.isParallel());

        System.out.println("\nExample three: checking that the order of elements is not saved (peak() method)");
        List<Integer> exampleThree = Arrays.asList(1, 2, 3, 4, 5);
        exampleThree.stream().parallel().peek(System.out::println).toList();

        System.out.println("\nExample four: checking that the order of elements is not saved (foreach)");
        List<Integer> exampleFour = Arrays.asList(1, 2, 3, 4, 5);
        exampleFour.stream().parallel().forEach(System.out::println);

        System.out.println("\nExample five: using forEachOrdered() method to save the order of elements");
        List<Integer> exampleFive = Arrays.asList(1, 3, 4, 5, 2);
        exampleFive.stream().parallel().forEachOrdered(System.out::println);
    }

}
