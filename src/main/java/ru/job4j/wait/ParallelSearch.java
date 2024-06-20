package ru.job4j.wait;

public class ParallelSearch {

    private final static SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
    private static volatile boolean producerFinished = false;

    public static void main(String[] args) {

        final Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    Integer value = queue.poll();
                    if (value != null) {
                        System.out.println(value);
                    }
                    if (producerFinished) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();

        new Thread(
                () -> {
                    try {
                        for (int index = 0; index != 3; index++) {
                            queue.offer(index);
                            if (index == 2) {
                                producerFinished = true;
                            }
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        ).start();
    }

}