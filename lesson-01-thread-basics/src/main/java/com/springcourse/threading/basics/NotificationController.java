package com.springcourse.threading.basics;

public final class NotificationController {

    private final NotificationQueue queue;
    private final NotificationDispatcher dispatcher;

    public NotificationController(NotificationQueue queue, NotificationDispatcher dispatcher) {
        this.queue = queue;
        this.dispatcher = dispatcher;
    }

    public void runDemo() throws InterruptedException {
        Thread dispatcherThread = new Thread(dispatcher::dispatchPendingMessages, "notification-dispatcher");
        NotificationAuditThread auditThread = new NotificationAuditThread(queue);

        dispatcherThread.start();
        auditThread.start();

        System.out.printf("[%s] Workers launched%n", Thread.currentThread().getName());

        dispatcherThread.join();
        auditThread.join();
    }
}
