package ru.jafti.lessons.concurrent;

public class SyncExample {

    private final Object lock = new Object(); // объект-монитор

    public void doWork() {
        synchronized (lock) {
            // Только один поток может быть здесь в момент времени
            System.out.println("Критическая секция: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // эмуляция работы
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}