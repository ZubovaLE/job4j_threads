package ru.job4j.wait;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

class SimpleBlockingQueueTest {

    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
    private final List<Integer> buffer = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger processedCount = new AtomicInteger(0);

    @Test
    public void testProducerConsumer() throws InterruptedException {
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.offer(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (processedCount.get() < 10) {
                    Integer value = queue.poll();
                    if (value != null) {
                        buffer.add(value);
                        processedCount.incrementAndGet();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertThat(10, equalTo(buffer.size()));
        assertThat(buffer, containsInAnyOrder(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

}