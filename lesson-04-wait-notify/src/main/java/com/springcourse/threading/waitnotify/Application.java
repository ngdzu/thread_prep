package com.springcourse.threading.waitnotify;

import com.springcourse.threading.waitnotify.controller.FulfillmentController;
import com.springcourse.threading.waitnotify.queue.PickRequestQueue;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) throws InterruptedException {
        PickRequestQueue queue = new PickRequestQueue(5);
        FulfillmentController controller = new FulfillmentController(queue);
        controller.startDemo();
    }
}
