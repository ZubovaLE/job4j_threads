package ru.job4j.wait;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

class SimpleBlockingQueueTest {

    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
    private final List<Integer> buffer = Collections.synchronizedList(new ArrayList<>());

    @Test
    public void testProducerConsumer() throws InterruptedException {
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(i -> {
                    try {
                        queue.offer(i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                })
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();

        assertThat(5, equalTo(buffer.size()));
        assertThat(buffer, containsInAnyOrder(0, 1, 2, 3, 4));
    }

}