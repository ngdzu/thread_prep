# Lesson 04 Quiz – wait/notify Coordination

1. **Conceptual:** Why must calls to `wait` appear inside a loop that re-checks the guard condition?
2. **Multiple Choice:** When a consumer thread calls `notifyAll` after removing an item from the queue, what happens?
   - A. All waiting threads immediately run simultaneously.
   - B. Waiting threads move to the runnable state and compete to regain the monitor lock.
   - C. Only one waiting thread is awakened; the others stay blocked.
   - D. Threads exit the monitor permanently and cannot re-enter.
3. **Practical:** How does releasing the intrinsic lock during `wait` prevent producers and consumers from deadlocking?
4. **Scenario:** Suppose a producer replaces `notifyAll` with `notify`. Under what condition would the system still work, and when might it break?
5. **Code Reasoning:** Why does the queue restore the interrupt status before propagating the exception to the caller?
6. **Troubleshooting:** You observe that producers are stuck waiting even though the queue is empty. List two likely causes.
