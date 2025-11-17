package com.springcourse.threading.waitnotify.service;

import com.springcourse.threading.waitnotify.domain.PickRequest;
import com.springcourse.threading.waitnotify.queue.PickRequestQueue;

import java.time.Duration;

public final class PickRequestConsumer implements Runnable {

    private final PickRequestQueue queue;
    private final int itemsToProcess;

    public PickRequestConsumer(PickRequestQueue queue, int itemsToProcess) {
        this.queue = queue;
        this.itemsToProcess = itemsToProcess;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < itemsToProcess; i++) {
                PickRequest request = queue.dequeue();
                Thread.sleep(Duration.ofMillis(200));
                System.out.printf("[%s] completed %s%n", Thread.currentThread().getName(), request.orderId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("[%s] Consumer interrupted%n", Thread.currentThread().getName());
        }
    }
}
