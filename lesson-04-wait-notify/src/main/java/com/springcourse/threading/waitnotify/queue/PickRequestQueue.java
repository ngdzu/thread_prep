package com.springcourse.threading.waitnotify.queue;

import com.springcourse.threading.waitnotify.domain.PickRequest;

import java.util.ArrayDeque;
import java.util.Deque;

public final class PickRequestQueue {

    private final Deque<PickRequest> buffer = new ArrayDeque<>();
    private final int capacity;

    public PickRequestQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void enqueue(PickRequest request) throws InterruptedException {
        while (buffer.size() >= capacity) {
            System.out.printf("[%s] waiting to enqueue (queue full)%n", Thread.currentThread().getName());
            wait();
        }
        buffer.addLast(request);
        System.out.printf("[%s] enqueued %s%n", Thread.currentThread().getName(), request.orderId());
        notifyAll();
    }

    public synchronized PickRequest dequeue() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.printf("[%s] waiting to dequeue (queue empty)%n", Thread.currentThread().getName());
            wait();
        }
        PickRequest request = buffer.removeFirst();
        System.out.printf("[%s] dequeued %s%n", Thread.currentThread().getName(), request.orderId());
        notifyAll();
        return request;
    }

    public synchronized int size() {
        return buffer.size();
    }
}
