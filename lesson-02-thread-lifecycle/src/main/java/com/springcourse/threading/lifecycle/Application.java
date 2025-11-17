package com.springcourse.threading.lifecycle;

import com.springcourse.threading.lifecycle.controller.ExportController;
import com.springcourse.threading.lifecycle.domain.ExportJob;
import com.springcourse.threading.lifecycle.repository.ExportQueue;
import com.springcourse.threading.lifecycle.service.ProgressReporter;
import com.springcourse.threading.lifecycle.service.ReportWorker;

import java.util.List;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) throws InterruptedException {
        ExportQueue queue = ExportQueue.seed(List.of(
                new ExportJob("customer-ledger", 6),
                new ExportJob("cash-flow", 4),
                new ExportJob("revenue-summary", 5)));

        ReportWorker worker = new ReportWorker(queue);
        ProgressReporter reporter = new ProgressReporter();
        ExportController controller = new ExportController(worker, reporter);

        controller.runExport();
    }
}
