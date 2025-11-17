# Lesson 05 – ExecutorService and Futures

## Synopsis
- Replace manual thread management with `ExecutorService` abstractions.
- Submit `Callable` tasks, gather `Future` results, and manage timeouts.
- Build graceful shutdown procedures that drain work without leaking threads.

## Prerequisites
- Completion of Lessons 01–04 or equivalent understanding of thread creation and synchronization.
- JDK 17+ and Maven 3.9+.
- Familiarity with Java collections and exception handling.

## Learning Objectives
You will learn to:
- Choose an appropriate executor type (fixed, cached, work-stealing) for workload characteristics.
- Submit CPU and I/O-bound tasks and collect results while handling checked exceptions.
- Shut down executors cleanly using `shutdown`/`awaitTermination`/`shutdownNow`.

## Sample Project
The Maven project renders image thumbnails using a fixed thread pool and collects results via `Future` objects.

### Build
```
mvn clean compile
```

### Run
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.executors.Application"
```

Logs show task submission, concurrent execution, and aggregation of results with timeout handling, including average render durations rounded to a single decimal place.

## Practice Exercises
`practice/` scaffolds an email digest service where you must choose pool sizes, add cancellation, and propagate failures.

### Build Practice Project
```
cd practice
mvn clean compile
```

### Run Practice Scenario
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.executors.practice.PracticeApplication"
```

## Estimated Time
- Reading: 30 minutes
- Sample walkthrough: 20 minutes
- Practice: 30–45 minutes

## Troubleshooting
- If tasks hang during shutdown, verify that `shutdown` and `awaitTermination` are invoked and that tasks respect interrupts.
- Use `ThreadFactory` to label pools, aiding log correlation when multiple executors run concurrently.
