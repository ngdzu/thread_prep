# Lesson 03 – Shared State and Synchronization

## Synopsis
- Identify race conditions when multiple threads mutate shared data.
- Apply `synchronized` blocks and intrinsic locks to protect critical sections.
- Use volatile reads and snapshot reporting to reason about visibility across threads.

## Prerequisites
- Completion of Lessons 01 and 02 or equivalent knowledge of thread creation and lifecycle management.
- JDK 17+ and Maven 3.9+.
- Comfort with Java collections and basic exception handling.

## Learning Objectives
You will learn to:
- Spot symptoms of data races by comparing expected versus actual inventory counts.
- Guard state mutations with synchronized methods/blocks.
- Combine logging and assertions to verify correctness under concurrent load.

## Sample Project
The Maven project simulates point-of-sale terminals updating shared inventory levels.

### Build
```
mvn clean compile
```

### Run
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.sharedstate.Application"
```

The console contrasts unsafe versus synchronized updates so you can see how locking restores correctness.

## Practice Exercises
`practice/` contains failing tests that expose a race condition in an order allocation service. Fix it by applying proper synchronization and visibility guarantees.

### Build Practice Project
```
cd practice
mvn clean compile
```

### Run Practice Scenario
```
mvn -q -DskipTests exec:java -Dexec.mainClass="com.springcourse.threading.sharedstate.practice.PracticeApplication"
```

## Estimated Time
- Reading: 30 minutes
- Sample walkthrough: 20 minutes
- Practice: 30–40 minutes

## Troubleshooting
- If results vary run-to-run, double-check that all shared mutations occur inside synchronized blocks.
- Use `-ea` (enable assertions) when running to get immediate feedback from built-in checks.
