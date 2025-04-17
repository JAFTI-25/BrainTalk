package ru.jafti.lessons.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ProductProcessor {

    public static void main(String[] args) {
        List<String> productIds = Arrays.asList(
                "prod-1", "prod-2", "prod-3", "prod-4", "prod-5",
                "prod-6", "prod-7", "prod-8", "prod-9", "prod-10"
        );

        List<CompletableFuture<Void>> futures = productIds.stream()
                .map(ProductProcessor::processProductAsync)
                .toList();

        // Ждём завершения всех задач
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> System.out.println("All products processed"))
                .join(); // блокирует основной поток до завершения
    }

    private static CompletableFuture<Void> processProductAsync(String productId) {
        return CompletableFuture
                .supplyAsync(() -> fetchProductInfo(productId))
                .thenApplyAsync(ProductProcessor::calculatePrice)
                .thenAccept(price -> {
                    System.out.printf("Product %s: $%d processed by %s%n",
                            productId, price, Thread.currentThread().getName());
                });
    }

    private static String fetchProductInfo(String productId) {
        sleepRandom(1000, 2000);
        return "Info about " + productId;
    }

    private static int calculatePrice(String productInfo) {
        sleepRandom(500, 1500);
        return new Random().nextInt(100, 500); // "расчёт" цены
    }

    private static void sleepRandom(int minMs, int maxMs) {
        try {
            int delay = new Random().nextInt(maxMs - minMs + 1) + minMs;
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}