# Lesson 04 – wait/notify Coordination

## Synopsis
- Coordinate producers and consumers using intrinsic locks with `wait`, `notify`, and `notifyAll`.
- Build bounded queues that block producers when capacity is reached.
- Avoid missed signals and spurious wakeups with proper guard conditions.

## Prerequisites
- Completion of Lessons 01–03 or equivalent understanding of thread basics, lifecycle, and synchronization.
- JDK 17+ and Maven 3.9+.
- Comfort with `synchronized` blocks and exception handling.

## Learning Objectives
After this lesson you will be able to:
- Implement a blocking queue using intrinsic locks and condition checks.
- Explain why `while` loops are required when awaiting conditions.
- Diagnose deadlocks caused by improper notification ordering.

## Sample Project
The Maven project models order fulfillment with producers adding pick requests and consumers completing them using a hand-rolled queue.

### Build
```
mvn clean compile
```

### Run
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.waitnotify.Application"
```

Observe how producers block when the queue is full and resume only after consumers free capacity.

## Practice Exercises
`practice/` asks you to extend the queue with timeout support and fairness guarantees.

### Build Practice Project
```
cd practice
mvn clean compile
```

### Run Practice Scenario
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.waitnotify.practice.PracticeApplication"
```

## Estimated Time
- Reading: 35 minutes
- Sample walkthrough: 25 minutes
- Practice: 30–45 minutes

## Troubleshooting
- If threads appear stuck, dump thread stacks (`jcmd <pid> Thread.print`) to confirm they are waiting on the expected monitor.
- Ensure every `wait` resides in a loop that re-checks the guard condition after waking.
