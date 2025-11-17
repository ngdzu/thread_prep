## Lesson 07 — Avoiding Deadlock in Java

This lesson covers practical recommendations to avoid deadlock in Java programs. It explains common causes, prevention strategies, and gives small, focused code examples you can apply immediately.

### What is deadlock?
Deadlock happens when two or more threads are permanently blocked, each waiting for a lock held by another thread in the cycle. In Java this usually involves intrinsic locks (synchronized) or explicit locks (instances of java.util.concurrent.locks.Lock).

### Common causes
- Nested locks acquired in inconsistent orders across threads.
- Holding locks while performing blocking operations (I/O, network, sleep, wait on external condition).
- Mixing different locking mechanisms without a clear protocol (synchronized + ReentrantLock).

---

### Practical prevention techniques

1) Prefer high-level concurrent abstractions

- Use collections from `java.util.concurrent` (ConcurrentHashMap, ConcurrentLinkedQueue, BlockingQueue) instead of manual synchronization.
- Use `ExecutorService`, `CompletableFuture`, or actor-like designs (single-threaded executor) to confine state changes to a single thread when appropriate.

2) Keep lock scope minimal

- Only hold locks for the minimal critical section: gather data while locked, release, then do I/O or long-running work.

3) Establish a global lock ordering

- If multiple locks must be acquired, always acquire them in a stable, pre-determined order (for example: by unique id). This prevents cyclic waiting.

Example (deterministic ordering):

```java
public static void transfer(Account from, Account to, int amount) {
    Account first = from.getId() < to.getId() ? from : to;
    Account second = first == from ? to : from;
    synchronized (first) {
        synchronized (second) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }
}
```

Notes: use a stable unique id (not mutable fields) to order locks.

4) Use timed, non-blocking acquisition (tryLock)

- When you need multiple locks, use `ReentrantLock.tryLock(long, TimeUnit)` to back off and retry instead of waiting forever.

Example (tryLock with timeout and backoff):

```java
boolean acquireBoth(ReentrantLock a, ReentrantLock b) throws InterruptedException {
    while (true) {
        if (a.tryLock(500, TimeUnit.MILLISECONDS)) {
            try {
                if (b.tryLock(500, TimeUnit.MILLISECONDS)) {
                    try {
                        return true; // both locks acquired
                    } finally {
                        b.unlock();
                    }
                }
            } finally {
                a.unlock();
            }
        }
        // backoff, maybe randomize to reduce contention
        Thread.sleep(10);
    }
}
```

5) Avoid calling external code while holding locks

- Never perform blocking I/O, call callbacks, or acquire other subsystem resources while holding a lock. External code may try to acquire the same lock or block indefinitely.

6) Reduce lock granularity (lock striping)

- Instead of a single coarse lock for a big data structure, use finer-grained locks (stripes) to reduce contention and lower deadlock probability.

7) Prefer immutable state and lock-free patterns where possible

- Immutable objects and atomic updaters (AtomicInteger, AtomicReference) remove the need for locks for many patterns.

---

### Detecting deadlocks at runtime

You can detect deadlocks programmatically using the `ThreadMXBean`:

```java
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
long[] deadlocked = tmxb.findDeadlockedThreads();
if (deadlocked != null) {
    ThreadInfo[] infos = tmxb.getThreadInfo(deadlocked, true, true);
    for (ThreadInfo ti : infos) {
        System.err.println(ti);
    }
}
```

You can also use `jstack <pid>`, `jcmd <pid> Thread.print`, VisualVM, or Java Mission Control to analyze thread dumps.

---

### Checklist for code reviews

- Are locks acquired in a consistent global order?
- Is the critical section minimal and free of I/O or external calls?
- Could a `java.util.concurrent` collection or executor remove the need for manual locks?
- If multiple locks are needed, is `tryLock` with a timeout considered?
- Are there clear unit or stress tests exercising concurrent paths?

---

### Small exercises (recommended)

1. Find a small class that uses two synchronized blocks and rewrite it to use a consistent lock order.
2. Replace a synchronized producer-consumer queue with a `LinkedBlockingQueue` and an `ExecutorService`.
3. Add a watchdog thread that periodically uses `ThreadMXBean.findDeadlockedThreads()` and logs a dump if deadlock is found.

---

### Further reading

- Java Concurrency in Practice (Brian Goetz) — classic guidance.
- Oracle Java docs: java.util.concurrent package and `ThreadMXBean`.

End of lesson.
