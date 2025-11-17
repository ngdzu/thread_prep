package com.springcourse.threading.waitnotify.controller;

import com.springcourse.threading.waitnotify.domain.PickRequest;
import com.springcourse.threading.waitnotify.queue.PickRequestQueue;
import com.springcourse.threading.waitnotify.service.PickRequestConsumer;
import com.springcourse.threading.waitnotify.service.PickRequestProducer;

import java.util.List;

public final class FulfillmentController {

    private final PickRequestQueue queue;

    public FulfillmentController(PickRequestQueue queue) {
        this.queue = queue;
    }

    public void startDemo() throws InterruptedException {
        List<PickRequest> batchOne = List.of(
                new PickRequest("order-1001", "sku-1", 2),
                new PickRequest("order-1002", "sku-2", 1),
                new PickRequest("order-1003", "sku-3", 5));
        List<PickRequest> batchTwo = List.of(
                new PickRequest("order-1004", "sku-1", 1),
                new PickRequest("order-1005", "sku-4", 2),
                new PickRequest("order-1006", "sku-5", 3));

        Thread producerOne = new Thread(new PickRequestProducer(queue, batchOne), "producer-1");
        Thread producerTwo = new Thread(new PickRequestProducer(queue, batchTwo), "producer-2");
        Thread consumerOne = new Thread(new PickRequestConsumer(queue, 3), "consumer-1");
        Thread consumerTwo = new Thread(new PickRequestConsumer(queue, 3), "consumer-2");

        producerOne.start();
        producerTwo.start();
        consumerOne.start();
        consumerTwo.start();

        producerOne.join();
        producerTwo.join();
        consumerOne.join();
        consumerTwo.join();

        System.out.printf("Queue size after demo: %d%n", queue.size());
    }
}
