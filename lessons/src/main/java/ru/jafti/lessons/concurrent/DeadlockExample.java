package ru.jafti.lessons.concurrent;

public class DeadlockExample {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock1...");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                synchronized (lock2) {
                    System.out.println("Thread 1: Holding lock1 and lock2...");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Holding lock2...");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                synchronized (lock1) {
                    System.out.println("Thread 2: Holding lock2 and lock1...");
                }
            }
        });

        thread1.setName("T1");
        thread1.setName("T2");

        thread1.start();
        thread2.start();
    }
}