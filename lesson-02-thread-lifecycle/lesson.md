# Lesson 02 – Thread Lifecycle Management

Every `Thread` instance moves through a well-defined lifecycle. Understanding where a thread sits at any moment explains why certain operations succeed or fail. This lesson tracks three collaborating threads in a simulated export pipeline: the main orchestrator, a `ReportWorker` that builds CSV files, and a `ProgressReporter` that keeps the user informed.

A thread begins in the `NEW` state immediately after construction. In the sample, we log `worker.getState()` before calling `start()` so you can see `NEW` printed. Once `start` executes, the JVM transitions the thread to `RUNNABLE`, meaning it is eligible to run on a CPU core. A `RUNNABLE` thread may actually be running or may be waiting for a timeslice; the distinction is intentionally opaque because scheduling is handled by the OS.

Long-running exports rarely execute in one burst. We simulate staged work with `Thread.sleep(Duration.ofMillis(400))`. Sleep pushes the thread into `TIMED_WAITING`, indicating that the scheduler should park the thread until the timer expires. During this time other threads, such as the progress reporter, can run freely. The sample prints snapshots like `ReportWorker state: TIMED_WAITING`, illustrating that the thread is alive but intentionally paused.

> ⚠️ Watch Out: `sleep` throws `InterruptedException`. Always catch it, restore the interrupt status with `Thread.currentThread().interrupt()`, and exit gracefully if the work cannot continue. Swallowing the interrupt leaves higher-level orchestrators unaware that cancellation was requested.

`join` is our coordination tool. Calling `worker.join()` blocks the caller until the worker reaches `TERMINATED`. In GUI or server applications you should not block indefinitely; instead, use a timeout: `worker.join(Duration.ofSeconds(5).toMillis())`. The sample passes a timeout and inspects the boolean result to decide whether to trigger a soft interrupt.

Interrupts signal intent to cancel. They do not forcibly stop a thread; rather, they flip the thread’s interrupted flag. Blocking methods such as `sleep` and `wait` react by throwing `InterruptedException`. The `ReportWorker` catches this, restores the flag, and logs a cancellation message so the controller can escalate if needed. The progress reporter periodically checks `worker.getState()` to demonstrate how a companion thread can make decisions based on lifecycle information.

Finally, when a thread finishes its `run` method, it transitions to `TERMINATED`. Attempts to restart it with `start()` throw `IllegalThreadStateException`. This is by design—threads represent single-use execution paths. If you need to run the same logic again, construct a new thread or reuse a `Runnable` with a fresh `Thread` instance.

By the end of this lesson you should feel comfortable reading lifecycle logs, timing `join`, and using interrupts for graceful shutdowns. These patterns set the stage for advanced coordination primitives in later lessons.
