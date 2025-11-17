package com.springcourse.threading.basics.service;

import com.springcourse.threading.basics.domain.NotificationMessage;
import com.springcourse.threading.basics.repository.NotificationQueue;

import java.time.Duration;

public final class NotificationDispatcher {

    private final NotificationQueue queue;

    public NotificationDispatcher(NotificationQueue queue) {
        this.queue = queue;
    }

    public void dispatchPendingMessages() {
        try {
            NotificationMessage message;
            while ((message = queue.next()) != null) {
                System.out.printf("[%s] Dispatching %s%n", Thread.currentThread().getName(), message.shortLabel());
                Thread.sleep(Duration.ofMillis(300));
            }
            System.out.printf("[%s] Dispatch complete%n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("[%s] Dispatcher interrupted%n", Thread.currentThread().getName());
        }
    }
}
