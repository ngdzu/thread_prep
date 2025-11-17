# Lesson 04 – wait/notify Coordination

High-level concurrency utilities are built on top of low-level primitives. Java’s intrinsic monitor lock provides not only mutual exclusion but also condition management via `wait`, `notify`, and `notifyAll`. In this lesson we build a bounded queue for order picking to see how threads can signal each other without busy-waiting.

Our `PickRequestQueue` exposes two synchronized methods: `enqueue` and `dequeue`. When producers call `enqueue`, they check whether the queue has room. If it is full, they call `wait()` inside a `while` loop. `wait` releases the intrinsic lock and suspends the thread until another thread invokes `notifyAll` on the same object. When a consumer removes an item, it calls `notifyAll` to awaken waiting producers, which immediately re-acquire the lock and re-check the loop condition. The `while` is crucial because wakeups are not guaranteed to match signals; threads may wake spuriously or find that another producer beat them to the free slot.

Consumers follow the mirror pattern. Calling `dequeue` while the queue is empty causes the thread to wait until a producer adds work. When an item arrives, `notifyAll` wakes the consumers, and one wins the race to claim the newly available work.

> ⚠️ Watch Out: Always guard `wait` with `while`, never `if`. Spurious wakeups and competing threads can violate assumptions if you drop directly into your logic after a single check.

The sample adds logging around each state transition: queuing, waiting for space, and resuming. Producers print `[producer-X] waiting (queue full)` when capacity is reached, then `[producer-X] resumed` once a consumer drains an item. Consumers likewise show when they are idle versus processing. The main controller starts two producer threads and two consumer threads, illustrating how the queue maintains balance even under uneven workloads.

We also handle interrupts carefully. If either `enqueue` or `dequeue` catches `InterruptedException`, we restore the interrupt status (`Thread.currentThread().interrupt()`) and propagate a custom `InterruptedException` to the caller. This lets higher levels decide whether to retry or cancel pending work.

By instrumenting the queue, we can verify there is no busy-waiting; threads that cannot proceed promptly release the lock and let their peers work. This design is the foundation for more advanced constructs such as `BlockingQueue`, `CountDownLatch`, and `Semaphore`. Understanding the low-level mechanics clarifies why those utilities require certain patterns (e.g., while guards) and how to troubleshoot when coordination goes wrong.

Experiment after reading: remove the `notifyAll` call or replace the `while` loops with `if` statements to see how the system either deadlocks or permits underflows. Observing these failure modes is essential to internalizing the discipline required when using wait/notify directly.

## Recommendations

- For a bounded blocking queue prefer a `BlockingDeque` (for example `LinkedBlockingDeque` or `ArrayBlockingQueue`) and use the provided `put`/`take` (or `putLast`/`takeFirst`) methods. These classes are thread-safe, provide capacity management and blocking semantics out of the box, and are simpler and less error-prone than hand-rolled `synchronized` + `wait`/`notifyAll` coordination.
- If you need custom semantics and keep using `synchronized` + `wait`/`notifyAll`, follow these rules:
	- Always guard `wait()` with a `while` loop (not `if`) to handle spurious wakeups and re-check conditions after reacquiring the monitor.
	- Prefer `notifyAll()` over `notify()` when multiple producer and consumer threads are present to avoid waking the wrong kind of thread and creating missed signals.
	- Document which monitor is used for coordination (e.g. `this` or a dedicated lock object) so future mainteners know what to synchronize on.

These simple choices reduce bugs and make reasoning about producer/consumer code much easier.
