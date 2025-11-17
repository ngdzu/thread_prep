package com.springcourse.threading.concurrent;

import com.springcourse.threading.concurrent.controller.AnalyticsController;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) throws InterruptedException {
        AnalyticsController controller = new AnalyticsController();
        controller.runStream();
    }
}
