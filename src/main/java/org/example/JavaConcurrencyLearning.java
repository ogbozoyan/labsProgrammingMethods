package org.example;

import org.jetbrains.annotations.NotNull;

/**
 * @author ogbozoyan
 * @since 11.10.2023
 */
public class JavaConcurrencyLearning {
    //Знакомство
    public static void main(@NotNull String[] args) throws InterruptedException {

        /*
        * Жизненный цикл потока
        *
        *                Waiting
        *                   or
        *                Blocking
        *                   or
        *                Sleeping
                         /     /\
        *               /       \
        *              \/         \
        * NEW ----> Runnable <---> Run -----> Dead
        *
        * */
        MultiThreading multiThreading = new MultiThreading(1);
        MultiThreading multiThreading2 = new MultiThreading(2);

        //start - for multiple concurrent threads
        multiThreading.start();
        multiThreading.join();
        System.out.println("here");

        multiThreading2.start();

        //second way
        MultiThreadingRunnable multiThreadingRunnable = new MultiThreadingRunnable(15);
        Thread myThread = new Thread(multiThreadingRunnable);
        myThread.start();

        for (int j = 3; j < 13; j++) {
            MultiThreading multiThreading3 = new MultiThreading(j);
            multiThreading3.start();
        }
    }

}

//first way Class extend Thread Class and override run method
class MultiThreading extends Thread {
    private final int number;

    public MultiThreading(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(number + ":" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

//second way implement Runnable interface
class MultiThreadingRunnable implements Runnable {
    private final int number;

    public MultiThreadingRunnable(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(number + ":" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}