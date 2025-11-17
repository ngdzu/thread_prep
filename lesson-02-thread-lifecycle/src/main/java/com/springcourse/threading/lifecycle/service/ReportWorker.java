package com.springcourse.threading.lifecycle.service;

import com.springcourse.threading.lifecycle.domain.ExportJob;
import com.springcourse.threading.lifecycle.repository.ExportQueue;

import java.time.Duration;

public final class ReportWorker implements Runnable {

    private final ExportQueue queue;

    public ReportWorker(ExportQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            ExportJob job;
            while ((job = queue.nextJob()) != null) {
                System.out.printf("[%s] Starting export %s (%d steps)%n",
                        Thread.currentThread().getName(), job.name(), job.steps());

                for (int step = 1; step <= job.steps(); step++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.printf("[%s] Cancelled before completing %s%n",
                                Thread.currentThread().getName(), job.name());
                        return;
                    }
                    Thread.sleep(Duration.ofMillis(400));
                    System.out.printf("[%s] Export %s step %d/%d complete%n",
                            Thread.currentThread().getName(), job.name(), step, job.steps());
                }
            }
            System.out.printf("[%s] All exports processed%n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("[%s] Worker interrupted, shutting down%n", Thread.currentThread().getName());
        }
    }
}
