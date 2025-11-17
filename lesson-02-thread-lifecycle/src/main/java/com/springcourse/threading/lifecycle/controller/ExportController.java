package com.springcourse.threading.lifecycle.controller;

import com.springcourse.threading.lifecycle.service.ProgressReporter;
import com.springcourse.threading.lifecycle.service.ReportWorker;

import java.time.Duration;

public final class ExportController {

    private final ReportWorker worker;
    private final ProgressReporter reporter;

    public ExportController(ReportWorker worker, ProgressReporter reporter) {
        this.worker = worker;
        this.reporter = reporter;
    }

    public void runExport() throws InterruptedException {
        Thread workerThread = new Thread(worker, "report-worker");
        System.out.printf("[%s] Worker initial state: %s%n",
                Thread.currentThread().getName(), workerThread.getState());

        Thread reporterThread = reporter.monitor(workerThread);

        workerThread.start();
        reporterThread.start();

        long timeoutMillis = Duration.ofSeconds(5).toMillis();
        workerThread.join(timeoutMillis);

        if (workerThread.isAlive()) {
            System.out.printf("[%s] Worker still running after timeout, requesting interrupt%n",
                    Thread.currentThread().getName());
            workerThread.interrupt();
            workerThread.join();
        }

        reporterThread.join();
        System.out.printf("[%s] Worker final state: %s%n",
                Thread.currentThread().getName(), workerThread.getState());
    }
}
