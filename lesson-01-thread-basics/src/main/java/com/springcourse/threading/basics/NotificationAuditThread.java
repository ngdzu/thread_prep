package com.springcourse.threading.basics;

import java.time.Duration;

public final class NotificationAuditThread extends Thread {

    private final NotificationQueue queue;

    public NotificationAuditThread(NotificationQueue queue) {
        super("notification-audit");
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                System.out.printf("[%s] Pending notifications: %d%n", getName(), queue.pendingCount());
                Thread.sleep(Duration.ofMillis(200));
            }
            System.out.printf("[%s] Audit finished%n", getName());
        } catch (InterruptedException e) {
            interrupt();
            System.out.printf("[%s] Audit interrupted%n", getName());
        }
    }
}
