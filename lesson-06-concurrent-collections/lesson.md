# Lesson 06 – Concurrent Collections and Atomics

Locks are powerful but can bottleneck systems when many threads compete for the same monitor. Java’s concurrent collections provide thread-safe operations without coarse-grained locking. They combine fine-grained locking, lock striping, or non-blocking algorithms to maintain throughput. In this lesson we build a streaming analytics dashboard that tallies click-through data from multiple feeds using `ConcurrentHashMap` and `LongAdder`.

`ConcurrentHashMap` divides its internals into segments so unrelated keys can be updated simultaneously. Instead of manually synchronizing, we use atomic methods such as `compute` and `merge`. For example, updating the per-campaign metrics looks like:

```java
counters.computeIfAbsent(campaignId, key -> new CampaignMetrics())
    .recordClick(country, durationMillis);
```

Inside `CampaignMetrics`, we store a `ConcurrentHashMap<String, LongAdder>` keyed by country. `LongAdder` spreads increments across multiple counters and aggregates them lazily, reducing contention under heavy write loads compared with `AtomicLong`. To add a click we call `clicksByCountry.computeIfAbsent(country, key -> new LongAdder()).increment();`.

> ⚠️ Watch Out: Do not mix atomic updates with external synchronization. If you wrap concurrent maps with `synchronized` blocks you negate their throughput advantages and risk deadlocks.

The sample spawns three data feed threads, each publishing synthetic clicks. They enqueue events onto a `ConcurrentLinkedQueue`, and an aggregator thread drains the queue at 100 ms intervals. Using a queue avoids locking during handoff—the producer simply `offer`s and the consumer `poll`s. The aggregator updates the metrics map, and the controller periodically snapshots the state for display. Because `ConcurrentHashMap` guarantees weakly consistent iterators, we can iterate safely without stopping producers; we may see data twice or miss data that arrives mid-iteration, but we never throw `ConcurrentModificationException`.

We also calculate rolling averages using `AtomicLong` for total duration and `LongAdder` for counts. Atomics support lock-free arithmetic: incrementing a `LongAdder` is constant time even under contention, while `AtomicLong` offers compare-and-set operations when you need to enforce invariants.

Compared with previous lessons, we no longer coordinate threads with explicit locks or wait/notify; instead we leverage data structures designed for concurrency. The trade-off is understanding their semantics—`ConcurrentHashMap` doesn’t provide global ordering, and `LongAdder` sacrifices immediate consistency for scalability. That is acceptable in analytics pipelines where slight staleness is tolerable.

Experiment by increasing the feed throughput or the number of keys. Observe how `LongAdder` keeps latency low, then swap it with `AtomicLong` or a synchronized map to measure the difference. Recognizing when high-level concurrent structures suffice will help you avoid premature use of lower-level primitives in production systems.
