package ru.jafti.lessons.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadSimulator {
    public static void main(String[] args) throws InterruptedException {
        List<String> urls = Arrays.asList(
            "http://example.com/1", "http://example.com/2", "http://example.com/3",
            "http://example.com/4", "http://example.com/5", "http://example.com/6",
            "http://example.com/7", "http://example.com/8", "http://example.com/9",
            "http://example.com/10"
        );

        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(urls.size());
        Random random = new Random();

        for (String url : urls) {
            executor.submit(() -> {
                try {
                    long start = System.currentTimeMillis();
                    int delay = 1000 + random.nextInt(2000); // 1-3 сек
                    Thread.sleep(delay);
                    long end = System.currentTimeMillis();
                    System.out.println("Downloaded from " + url + " in " + (end - start) +
                            " ms by thread " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // ждём окончания всех задач
        executor.shutdown();
        System.out.println("All downloads completed");
    }
}