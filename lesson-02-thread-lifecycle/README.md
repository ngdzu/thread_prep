# Lesson 02 – Thread Lifecycle Management

## Synopsis
- Observe every major thread state (`NEW`, `RUNNABLE`, `TIMED_WAITING`, `TERMINATED`).
- Use `join`, `sleep`, and `interrupt` to coordinate exports without blocking the UI thread.
- Capture lifecycle transitions with logging to build intuition about scheduling.

## Prerequisites
- Completion of Lesson 01 or equivalent experience creating and starting threads.
- JDK 17+ and Maven 3.9+.
- Basic familiarity with `try`/`catch` and logging in Java.

## Learning Objectives
After this lesson you will:
- Describe the journey of a thread from creation through termination.
- Use `Thread::join` with timeouts to avoid hanging indefinitely.
- Interrupt sleeping threads safely and restore interrupt status when handing control back up the stack.

## Sample Project
The Maven project simulates a data export pipeline that spawns report workers and a progress reporter.

### Build
```
mvn clean compile
```

### Run
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.lifecycle.Application"
```

Watch the console for lifecycle status snapshots that highlight how threads transition between states during long-running exports.

## Practice Exercises
`practice/` contains scaffolding that guides you through coordinating thread shutdown using timeouts and interrupts.

### Build Practice Project
```
cd practice
mvn clean compile
```

### Run Practice Scenario
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.lifecycle.practice.PracticeApplication"
```

Consult `practice/README.md` for detailed instructions.

## Estimated Time
- Reading: 25 minutes
- Sample walkthrough: 20 minutes
- Practice: 25–35 minutes

## Troubleshooting
- If you see an `IllegalThreadStateException`, double-check that you are not calling `start` on the same `Thread` instance twice.
- Use `jcmd <pid> Thread.print` (optional advanced exercise) to inspect thread states outside of the application logs.
