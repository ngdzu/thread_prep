package com.springcourse.threading.concurrent.service;

import com.springcourse.threading.concurrent.domain.ClickEvent;

import java.time.Duration;
import java.util.Random;

public final class DataFeed implements Runnable {

    private static final String[] COUNTRIES = { "US", "UK", "DE", "IN", "BR" };

    private final String campaignId;
    private final MetricsAggregator aggregator;
    private final Random random = new Random();
    private volatile boolean running = true;

    public DataFeed(String campaignId, MetricsAggregator aggregator) {
        this.campaignId = campaignId;
        this.aggregator = aggregator;
    }

    @Override
    public void run() {
        while (running) {
            String country = COUNTRIES[random.nextInt(COUNTRIES.length)];
            long dwell = 100 + random.nextInt(400);
            aggregator.publish(new ClickEvent(campaignId, country, dwell));
            try {
                Thread.sleep(Duration.ofMillis(80));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
