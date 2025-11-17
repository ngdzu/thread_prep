# Lesson 05 – ExecutorService and Futures

Manual thread creation does not scale. Spawning a new `Thread` for every image thumbnail wastes resources and complicates lifecycle management. Java’s `ExecutorService` decouples task submission from execution, letting you focus on business logic while a configurable pool manages threads. In this lesson we build a fixed-size pool that renders marketing thumbnails and returns the resulting metadata to the caller.

We start by creating the pool:

```java
ExecutorService pool = Executors.newFixedThreadPool(4, new NamedThreadFactory("thumb"));
```

The `ThreadFactory` assigns readable names, a best practice for observability. We then submit `Callable<Thumbnail>` tasks. Unlike `Runnable`, a `Callable` can return a value and throw checked exceptions. The executor immediately returns a `Future<Thumbnail>`, which represents a pending result. All tasks begin processing as soon as threads are available—no need to call `start` manually.

Retrieving results requires care. Calling `future.get()` blocks until the task completes. To avoid stalling the orchestrator forever, the sample uses `future.get(timeout, unit)`. When the timeout elapses, we log a warning and cancel the task with `future.cancel(true)`, which interrupts the worker thread. Each task checks `Thread.currentThread().isInterrupted()` and aborts image processing when cancellation is requested.

> ⚠️ Watch Out: Always shut down executors. Forgetting to call `shutdown` keeps threads alive and prevents the JVM from exiting. The sample wraps the entire pipeline in a try–finally block that invokes `shutdown`, then waits up to five seconds with `awaitTermination`. If tasks refuse to finish, `shutdownNow` attempts to cancel them.

The sample aggregates results by mapping futures to completed thumbnails and grouping them by campaign. Along the way we capture exceptions from failed tasks: `ExecutionException` wraps the root cause, so the controller unwraps it and records which campaign failed. This demonstrates that futures do not hide errors—they deliver them when you call `get`.

Choosing the right executor matters. CPU-bound work benefits from a fixed pool sized near the number of cores. I/O-bound tasks might use a larger pool or a cached pool to handle bursts. By centralizing thread management you can tune these choices without touching business code.

Finally, we show how to reuse executors: the controller submits multiple batches sequentially, but because the pool stays alive between batches, no new threads are created. Monitoring the pool’s metrics (queue size, active threads) guides scaling decisions in production.

ExecutorService is the gateway to higher-level concurrency features such as `CompletableFuture` and parallel streams. Mastering futures and pool lifecycles here will make those advanced tools far easier to reason about.
