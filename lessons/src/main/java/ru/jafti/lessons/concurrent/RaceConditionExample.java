package ru.jafti.lessons.concurrent;

public class RaceConditionExample {

    // Общий ресурс
    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        // Задача: каждый поток увеличивает counter 100_000 раз
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter++;  // НЕ атомарно!
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Ожидается: 200_000
        System.out.println("Результат counter = " + counter);
    }
}