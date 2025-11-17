# Lesson 06 Quiz Answers – Concurrent Collections and Atomics

1. **Segmented locking.** `ConcurrentHashMap` uses finer-grained synchronization so different keys can be updated in parallel, whereas a synchronized map serializes all access on a single monitor.
2. **B.** `LongAdder` maintains multiple counters (cells) to reduce contention and sums them when queried. It prioritizes throughput over immediate consistency.
3. **To decouple producers from shared state.** The queue provides a non-blocking buffer so producers can publish quickly without contending for the map’s locks, allowing a dedicated aggregator to batch updates efficiently.
4. **`ConcurrentHashMap` with `compute`.** Store the maximum per key in the map and use `map.compute(key, (k, current) -> current == null ? value : Math.max(current, value));` to update atomically.
5. **No structural failures, eventual visibility.** Weakly consistent iterators reflect some state of the map during traversal without throwing exceptions, even while writes occur. For monitoring dashboards that refresh frequently, slight staleness is acceptable.
6. **Possible mistakes:** (a) Using non-atomic read-modify-write patterns (read, increment, put) instead of `merge`/`compute`. (b) Mixing regular `HashMap` or mutable values without proper synchronization inside `ConcurrentHashMap`, leading to race conditions.
