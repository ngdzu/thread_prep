package com.springcourse.threading.waitnotify.service;

import com.springcourse.threading.waitnotify.domain.PickRequest;
import com.springcourse.threading.waitnotify.queue.PickRequestQueue;

import java.time.Duration;
import java.util.List;

public final class PickRequestProducer implements Runnable {

    private final PickRequestQueue queue;
    private final List<PickRequest> requests;

    public PickRequestProducer(PickRequestQueue queue, List<PickRequest> requests) {
        this.queue = queue;
        this.requests = requests;
    }

    @Override
    public void run() {
        try {
            for (PickRequest request : requests) {
                queue.enqueue(request);
                Thread.sleep(Duration.ofMillis(150));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("[%s] Producer interrupted%n", Thread.currentThread().getName());
        }
    }
}
