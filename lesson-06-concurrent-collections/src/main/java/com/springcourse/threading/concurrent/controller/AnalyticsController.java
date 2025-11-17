package com.springcourse.threading.concurrent.controller;

import com.springcourse.threading.concurrent.domain.CampaignMetrics;
import com.springcourse.threading.concurrent.service.DataFeed;
import com.springcourse.threading.concurrent.service.MetricsAggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class AnalyticsController {

    public void runStream() throws InterruptedException {
        MetricsAggregator aggregator = new MetricsAggregator();
        List<DataFeed> feeds = List.of(
                new DataFeed("campaign-alpha", aggregator),
                new DataFeed("campaign-beta", aggregator),
                new DataFeed("campaign-gamma", aggregator));

        List<Thread> feedThreads = new ArrayList<>();
        for (DataFeed feed : feeds) {
            Thread thread = new Thread(feed, "feed-" + feed.hashCode());
            feedThreads.add(thread);
            thread.start();
        }

        long end = System.currentTimeMillis() + 2000;
        while (System.currentTimeMillis() < end) {
            aggregator.drain();
            printSnapshot(aggregator.snapshot());
            Thread.sleep(aggregator.aggregationInterval());
        }

        feeds.forEach(DataFeed::stop);
        for (Thread thread : feedThreads) {
            thread.interrupt();
            thread.join();
        }
    }

    private void printSnapshot(Map<String, CampaignMetrics> snapshot) {
        snapshot.forEach((campaign, metrics) -> {
            System.out.printf("Campaign %s clicks=%d avgDwell=%.2fms%n",
                    campaign,
                    metrics.totalClicks(),
                    metrics.averageDwell());
            metrics.clicksPerCountry().forEach((country, count) -> System.out.printf("    %s -> %d%n", country, count));
        });
        System.out.println("---");
    }
}
