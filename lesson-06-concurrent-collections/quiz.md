# Lesson 06 Quiz – Concurrent Collections and Atomics

1. **Conceptual:** Why does `ConcurrentHashMap` scale better than a `Collections.synchronizedMap` under heavy writes?
2. **Multiple Choice:** Which statement about `LongAdder` is correct?
   - A. It guarantees exact read-after-write consistency.
   - B. It spreads updates across cells to reduce contention and sums them when queried.
   - C. It can only be used with synchronized blocks.
   - D. It throws `ConcurrentModificationException` when read during writes.
3. **Practical:** In the sample, why do producers enqueue events into a `ConcurrentLinkedQueue` instead of synchronizing on the analytics map directly?
4. **Scenario:** You need to compute a per-key running maximum. Which concurrent collection or atomic construct would you choose, and how would you apply it?
5. **Code Reasoning:** What guarantees does a weakly consistent iterator on `ConcurrentHashMap` provide, and why is that acceptable in the dashboard?
6. **Troubleshooting:** Metrics occasionally report negative counts. List two likely mistakes when using concurrent collections.
