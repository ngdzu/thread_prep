# Lesson 01 – Thread Basics

Java applications start with a single thread: the main thread that executes `public static void main`. When work piles up—processing queued notifications, handling I/O, or refreshing dashboards—waiting for one task to finish before starting the next wastes the CPU. Threads let us overlap tasks so that while one piece of work waits for a network response, another can crunch data. In this first lesson we will focus on answering two questions: how do we create threads and how do we reason about their execution?

The JVM offers two primary entry points. You can extend `Thread` and override `run`, or you can implement `Runnable` and pass it to a `Thread` constructor. The difference is mostly about intent: extending `Thread` couples your logic to the thread API, while `Runnable` keeps the work unit reusable. In the sample project the `NotificationDispatcher` implements `Runnable` so it can be executed by either a raw `Thread` or, later, an executor. We start it with:

```java
Thread worker = new Thread(dispatcher, "notification-dispatcher");
worker.start();
```

Naming threads is vital. When you pass the second argument to the `Thread` constructor, logging frameworks include that name so you can trace related messages. Without meaningful names, debugging interleaved output becomes guesswork.

Thread scheduling is nondeterministic. The JVM delegates to the operating system, which may decide to pause one thread, run another, or even keep the current thread running based on priority and core availability. To highlight that behavior, the sample sleeps briefly between messages:

```java
Thread.sleep(Duration.ofMillis(300));
```

Sleeping yields control back to the scheduler. When the sleep expires, the thread queues to run again. Because the dispatcher and the main thread both print to the console, you will see their lines interleaved differently on each run. The main thread keeps executing immediately after calling `start`, proving that `start` is asynchronous.

> ⚠️ Watch Out: Do not call `run` directly. Invoking `run` skips the JVM’s thread lifecycle and runs synchronously on the caller. Always use `start` when you intend true concurrency.

To coordinate with worker completion we call `join`:

```java
worker.join();
```

`join` blocks the caller until the worker finishes, helping the sample wait for the final status message before exiting. Later lessons will explore richer coordination patterns, but `join` is a dependable first tool.

Finally, consider the implications of shared state. In this lesson we only read from a thread-safe queue, so we do not need locks yet. Still, always ask: “Which data lives longer than a single thread?” If multiple threads mutate the same object, you must plan for synchronization. For now, keep each worker focused on independent tasks, log generously, and observe how simple thread creation already opens the door to responsive applications.
