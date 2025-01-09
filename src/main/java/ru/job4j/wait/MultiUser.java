package ru.job4j.wait;

public class MultiUser {

    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        System.out.println("change from main!");
        System.out.println("change from main2!");
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.on();
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    barrier.check();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave"
        );
        master.start();
        slave.start();
    }

}