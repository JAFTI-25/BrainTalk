package ru.jafti.lessons.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompleatableFutureExamples {
    public static void main(String[] args) {

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Параллельная задача!");
        });

        CompletableFuture
                .supplyAsync(() -> 10)
                .thenApply(x -> x * 2)               // Возвращает новое значение
                .thenAccept(result -> System.out.println(result)); // Просто потребляет результат

        CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> 7);

        CompletableFuture<Integer> sum = a.thenCombine(b, Integer::sum);

        var future1 = CompletableFuture
                .supplyAsync(() -> {
                    if (true) throw new RuntimeException("Boom!");
                    return 42;
                })
                .exceptionally(ex -> {
                    System.out.println("Ошибка: " + ex.getMessage());
                    return 0;
                });

        future1.orTimeout(1, TimeUnit.SECONDS)
                .exceptionally(ex -> {
                    System.out.println("Таймаут!");
                    return null;
                });
    }
}
