package ru.jafti.lessons.concurrent;

public class WaitNotifyExample {

    private static final Object lock = new Object();
    private static boolean condition = false;

    public static void main(String[] args) {

        Thread waiter = new Thread(() -> {
            synchronized (lock) {
                while (!condition) {
                    try {
                        System.out.println("Жду сигнала...");
                        lock.wait(); // поток уходит в ожидание и освобождает монитор
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Получен сигнал, продолжаем выполнение.");
            }
        });

        Thread notifier = new Thread(() -> {
            try {
                Thread.sleep(2000); // эмуляция задержки
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (lock) {
                condition = true;
                lock.notify(); // будит один поток, ждущий этот монитор
                System.out.println("Сигнал отправлен.");
            }
        });

        waiter.start();
        notifier.start();
    }
}
