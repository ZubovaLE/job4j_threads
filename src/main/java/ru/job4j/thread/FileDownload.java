package ru.job4j.thread;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
public class FileDownload implements Runnable {
    private final String url;
    private final int speed;

    public static void main(String[] args) throws InterruptedException {
        if (validateArgs(args)) {
            try {
                String url = args[0];
                int speed = Integer.parseInt(args[1]);
                Thread wget = new Thread(new FileDownload(url, speed));
                wget.start();
                wget.join();
            } catch (NumberFormatException e) {
                System.out.println("args[1] is not a number.");
            }
        } else {
            System.out.println("Not enough arguments provided");
        }
    }

    private static boolean validateArgs(String[] args) {
        return args.length > 1 && args[0] != null && args[1] != null;
    }

    @Override
    public void run() {
        File file = new File("tmp.xml");
        int bufferSize = 1024; //in bytes
        try (var in = new URI(url).toURL().openStream(); var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                var readTime = System.nanoTime() - downloadAt;
                var currentSpeed = bufferSize * 1000000 / readTime;  // bytes per second
                if (currentSpeed >= speed) {
                    Thread.sleep(currentSpeed / speed);
                }
            }
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}