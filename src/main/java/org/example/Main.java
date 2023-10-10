package org.example;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws RuntimeException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        System.out.println("Hello world from main thread");
        AtomicInteger i = new AtomicInteger();
        while (i.get() < 10) {
            executorService.submit(() -> System.out.println("Hello world from new thread, i is: " + i));
            executorService.submit(() -> System.out.println("Hello world from new thread 2, i is: " + i));
            i.getAndIncrement();
        }
        executorService.shutdown();
        System.out.println("Hello world from main thread after new");
    }
}
