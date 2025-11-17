package com.springcourse.threading.basics;

import java.util.List;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) throws InterruptedException {
        NotificationQueue queue = NotificationQueue.bootstrap(List.of(
                new NotificationMessage("acct-1001", "Welcome bonus posted"),
                new NotificationMessage("acct-2020", "Security alert - new sign-in"),
                new NotificationMessage("acct-3030", "Spend summary available")));

        NotificationDispatcher dispatcher = new NotificationDispatcher(queue);
        NotificationController controller = new NotificationController(queue, dispatcher);

        System.out.printf("[%s] Starting notification demo...%n", Thread.currentThread().getName());
        controller.runDemo();
        System.out.printf("[%s] Demo finished.%n", Thread.currentThread().getName());
    }
}
