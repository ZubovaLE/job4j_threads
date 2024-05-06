package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = new char[]{'-', '\\', '|', '/'};
        int processCounter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (processCounter >= process.length) {
                    processCounter = 0;
                }
                System.out.print("\rLoading: " + process[processCounter++]);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.print("\rDone");
                Thread.currentThread().interrupt();
            }
        }
    }

}