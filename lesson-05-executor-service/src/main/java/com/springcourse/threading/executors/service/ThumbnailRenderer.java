package com.springcourse.threading.executors.service;

import com.springcourse.threading.executors.domain.Thumbnail;
import com.springcourse.threading.executors.domain.ThumbnailRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;

public final class ThumbnailRenderer implements Callable<Thumbnail> {

    private final ThumbnailRequest request;

    public ThumbnailRenderer(ThumbnailRequest request) {
        this.request = request;
    }

    @Override
    public Thumbnail call() throws Exception {
        Instant start = Instant.now();
        try {
            for (int i = 0; i < 5; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Rendering interrupted for " + request.imagePath());
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        }
        Duration duration = Duration.between(start, Instant.now());
        System.out.printf("[%s] Rendered %s in %d ms%n",
                Thread.currentThread().getName(), request.imagePath(), duration.toMillis());
        return new Thumbnail(request.campaignId(), request.imagePath(), duration);
    }
}
