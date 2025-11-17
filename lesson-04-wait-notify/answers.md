# Lesson 04 Quiz Answers – wait/notify Coordination

1. **To defend against spurious wakeups and races.** When a thread wakes it must confirm the condition is still true; another thread may have consumed the resource, or the wakeup could be unrelated.
2. **B.** `notifyAll` moves all waiting threads to the runnable queue, but they still need to reacquire the monitor lock one at a time before proceeding.
3. **It hands ownership to peers instead of spinning.** By releasing the lock when waiting, producers allow consumers to enter the critical section and free space, and vice versa, preventing mutual blocking.
4. **It works only if at most one type of thread waits at a time.** If both producers and consumers may wait on the same monitor, using `notify` risks waking a thread that cannot make progress (e.g., another producer when the queue is full), leading to deadlock.
5. **To propagate cancellation intent.** Restoring the interrupt flag lets upstream orchestrators detect that the thread was interrupted and respond appropriately (retry, stop, etc.) instead of silently absorbing the signal.
6. **Possible issues:** (a) A consumer forgot to call `notifyAll` after dequeuing, so waiting producers never wake. (b) Producers and consumers are synchronizing on different monitor objects, meaning notifications never reach the waiting threads.
