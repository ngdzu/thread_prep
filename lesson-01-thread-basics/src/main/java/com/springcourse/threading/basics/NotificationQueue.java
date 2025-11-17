package com.springcourse.threading.basics;

import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class NotificationQueue {

    private final Deque<NotificationMessage> queue = new ConcurrentLinkedDeque<>();

    public static NotificationQueue bootstrap(Collection<NotificationMessage> seedMessages) {
        NotificationQueue queue = new NotificationQueue();
        queue.queue.addAll(seedMessages);
        return queue;
    }

    public void enqueue(NotificationMessage message) {
        queue.addLast(message);
    }

    public NotificationMessage next() {
        return queue.pollFirst();
    }

    public int pendingCount() {
        return queue.size();
    }
}
