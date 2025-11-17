package com.springcourse.threading.concurrent.service;

import com.springcourse.threading.concurrent.domain.CampaignMetrics;
import com.springcourse.threading.concurrent.domain.ClickEvent;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MetricsAggregator {

    private final ConcurrentLinkedQueue<ClickEvent> queue = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, CampaignMetrics> metrics = new ConcurrentHashMap<>();

    public void publish(ClickEvent event) {
        queue.offer(event);
    }

    public void drain() {
        ClickEvent event;
        while ((event = queue.poll()) != null) {
            metrics.computeIfAbsent(event.campaignId(), key -> new CampaignMetrics())
                    .recordClick(event.country(), event.dwellMillis());
        }
    }

    public Map<String, CampaignMetrics> snapshot() {
        return Map.copyOf(metrics);
    }

    public Duration aggregationInterval() {
        return Duration.ofMillis(100);
    }
}
