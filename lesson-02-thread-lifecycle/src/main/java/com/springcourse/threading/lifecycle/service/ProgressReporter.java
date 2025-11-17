package com.springcourse.threading.lifecycle.service;

import java.time.Duration;

public final class ProgressReporter {

    public Thread monitor(Thread worker) {
        return new Thread(() -> {
            try {
                while (worker.isAlive()) {
                    System.out.printf("[%s] Worker state: %s%n",
                            Thread.currentThread().getName(), worker.getState());
                    Thread.sleep(Duration.ofMillis(250));
                }
                System.out.printf("[%s] Worker state: %s%n",
                        Thread.currentThread().getName(), worker.getState());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("[%s] Reporter interrupted%n", Thread.currentThread().getName());
            }
        }, "progress-reporter");
    }
}
