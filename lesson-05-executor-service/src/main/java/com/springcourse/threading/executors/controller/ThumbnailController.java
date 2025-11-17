package com.springcourse.threading.executors.controller;

import com.springcourse.threading.executors.domain.Thumbnail;
import com.springcourse.threading.executors.domain.ThumbnailRequest;
import com.springcourse.threading.executors.service.ThumbnailRenderer;
import com.springcourse.threading.executors.util.NamedThreadFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class ThumbnailController {

    public void generateThumbnails() {
        ExecutorService pool = Executors.newFixedThreadPool(4, new NamedThreadFactory("thumb"));
        List<ThumbnailRequest> requests = List.of(
                new ThumbnailRequest("fall-campaign", "images/boots.png", 320),
                new ThumbnailRequest("fall-campaign", "images/scarf.png", 320),
                new ThumbnailRequest("winter-campaign", "images/jacket.png", 320),
                new ThumbnailRequest("winter-campaign", "images/gloves.png", 320));

        Map<String, List<Thumbnail>> resultsByCampaign = new HashMap<>();
        List<Future<Thumbnail>> futures = new ArrayList<>();

        try {
            for (ThumbnailRequest request : requests) {
                futures.add(pool.submit(new ThumbnailRenderer(request)));
            }

            for (Future<Thumbnail> future : futures) {
                try {
                    Thumbnail thumbnail = future.get(2, TimeUnit.SECONDS);
                    resultsByCampaign.computeIfAbsent(thumbnail.campaignId(), key -> new ArrayList<>())
                            .add(thumbnail);
                } catch (TimeoutException timeout) {
                    future.cancel(true);
                    System.out.println("Timed out waiting for thumbnail: " + timeout.getMessage());
                } catch (ExecutionException executionException) {
                    System.out.println("Rendering failed: " + executionException.getCause());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            shutdownExecutor(pool);
        }

        resultsByCampaign.forEach((campaign, thumbnails) -> {
            double avgMillis = thumbnails.stream()
                    .map(Thumbnail::renderDuration)
                    .mapToLong(Duration::toMillis)
                    .average()
                    .orElse(0);
            System.out.printf("Campaign %s rendered %d thumbnails (avg %.1f ms)%n",
                    campaign, thumbnails.size(), avgMillis);
        });
    }

    private void shutdownExecutor(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Forcing shutdown of pool");
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
