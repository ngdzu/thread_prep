package com.springcourse.threading.lifecycle.repository;

import com.springcourse.threading.lifecycle.domain.ExportJob;

import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class ExportQueue {

    private final Deque<ExportJob> jobs = new ConcurrentLinkedDeque<>();

    public static ExportQueue seed(Collection<ExportJob> seed) {
        ExportQueue queue = new ExportQueue();
        queue.jobs.addAll(seed);
        return queue;
    }

    public ExportJob nextJob() {
        return jobs.pollFirst();
    }

    public int remainingJobs() {
        return jobs.size();
    }
}
