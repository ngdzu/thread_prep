# Lesson 02 Quiz – Thread Lifecycle Management

1. **Conceptual:** In which lifecycle state is a thread immediately after you construct it but before you call `start`, and why?
2. **Multiple Choice:** What does it mean when `Thread.getState()` returns `TIMED_WAITING`?
   - A. The thread is blocked on I/O and cannot be interrupted.
   - B. The thread is waiting for a monitor lock to become available.
   - C. The thread is sleeping or waiting with a timeout and will become runnable after the timeout.
   - D. The thread has finished executing `run`.
3. **Practical:** Why does the sample call `join` with a timeout instead of waiting indefinitely, and how does it respond when the timeout elapses?
4. **Scenario:** The controller decides to cancel an in-flight export. Describe how an interrupt propagates through the `ReportWorker` and what the worker must do to cooperate.
5. **Code Reasoning:** What exception is thrown if you invoke `start` on a thread that has already terminated, and what lifecycle rule does this protect?
6. **Troubleshooting:** You observe that the progress reporter logs `RUNNABLE` for the worker even though the worker is clearly inside `sleep`. Give two reasons why this might happen.
