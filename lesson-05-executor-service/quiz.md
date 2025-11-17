# Lesson 05 Quiz – ExecutorService and Futures

1. **Conceptual:** What advantages does `ExecutorService` offer over manually creating threads for each task?
2. **Multiple Choice:** What happens when `future.get(2, TimeUnit.SECONDS)` times out?
   - A. The task is automatically cancelled and the thread terminates.
   - B. A `TimeoutException` is thrown, giving the caller a chance to decide what to do.
   - C. The thread pool shuts down immediately.
   - D. The future returns `null`.
3. **Practical:** How does the sample ensure worker threads are named, and why is this useful?
4. **Scenario:** A task submitted to the executor throws an `IOException`. Describe how this propagates back to the caller retrieving the future.
5. **Code Reasoning:** Why does the shutdown sequence call `shutdown`, `awaitTermination`, and then `shutdownNow` in order?
6. **Troubleshooting:** You observe that the application never exits after processing. List two possible causes related to executor misuse.
