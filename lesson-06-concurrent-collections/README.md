# Lesson 06 – Concurrent Collections and Atomics

## Synopsis
- Introduce non-blocking data structures such as `ConcurrentHashMap` and `ConcurrentLinkedQueue`.
- Use atomic classes to perform lock-free counters and accumulators.
- Build a metric aggregation service that stays responsive under concurrent updates.

## Prerequisites
- Completion of Lessons 01–05 or equivalent grasp of threads, synchronization, and executors.
- JDK 17+ and Maven 3.9+.
- Familiarity with Java collections and optional streams.

## Learning Objectives
You will be able to:
- Choose the right concurrent collection for read-heavy versus write-heavy workloads.
- Combine `ConcurrentHashMap` with `compute`/`merge` operations to update shared state safely.
- Apply `AtomicLong` and `LongAdder` for high-throughput counters.

## Sample Project
This Maven project powers a live analytics dashboard that ingests click metrics from multiple feeds using a thread-safe map and atomics.

### Build
```
mvn clean compile
```

### Run
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.concurrent.Application"
```

Watch how the aggregation remains consistent even under fast, concurrent updates.

## Practice Exercises
`practice/` contains scaffolding for extending the dashboard with rolling windows and reset operations.

### Build Practice Project
```
cd practice
mvn clean compile
```

### Run Practice Scenario
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.concurrent.practice.PracticeApplication"
```

## Estimated Time
- Reading: 30 minutes
- Sample walkthrough: 20 minutes
- Practice: 30–40 minutes

## Troubleshooting
- If counts look inconsistent, confirm you are using atomic arithmetic (`merge`, `compute`, `LongAdder`) instead of read-modify-write with plain integers.
- Avoid mixing synchronized wrappers (`Collections.synchronizedMap`) with concurrent collections; stick to one approach.
