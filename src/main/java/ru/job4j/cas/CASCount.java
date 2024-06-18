package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int prev;
        int next;
        do {
            prev = count.get();
            next = prev + 1;
        } while (!count.compareAndSet(prev, next));
    }

    public int get() {
        return count.get();
    }

}