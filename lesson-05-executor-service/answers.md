# Lesson 05 Quiz Answers – ExecutorService and Futures

1. **Thread pooling, lifecycle management, and decoupling.** Executors reuse a bounded number of threads, isolate scheduling concerns from business logic, and expose unified shutdown semantics.
2. **B.** `get` throws `TimeoutException`, giving the caller the option to retry, cancel, or continue waiting. It does not cancel the task automatically.
3. **Custom `ThreadFactory`.** The sample passes a factory that prefixes thread names with `thumb-`, making log analysis and thread dumps easier.
4. **Wrapped in `ExecutionException`.** When the caller invokes `get`, the executor rethrows failures wrapped in `ExecutionException`. The sample unwraps `getCause()` to log the original `IOException`.
5. **Graceful escalation.** `shutdown` stops new submissions and lets tasks finish. `awaitTermination` waits for completion. If the timeout expires, `shutdownNow` interrupts running tasks as a last resort.
6. **Possible issues:** (a) `shutdown` was never called, leaving non-daemon threads alive. (b) Tasks caught `InterruptedException` but failed to restore the interrupt flag, preventing shutdown from succeeding.
