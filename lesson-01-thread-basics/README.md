# Lesson 01 – Thread Basics

## Synopsis
- Understand why threads are useful in Java applications.
- Create and start threads using both `Thread` subclasses and `Runnable` instances.
- Name threads and log their work for easier debugging.

## Prerequisites
- JDK 17 or newer installed and available on your `PATH`.
- Maven 3.9+ for building the projects.
- Familiarity with Java classes, interfaces, and lambda expressions.

## Learning Objectives
By the end of this lesson you will be able to:
- Explain how the JVM schedules threads and how that differs from sequential execution.
- Write a simple worker that runs concurrently with the main thread.
- Observe interleaved output caused by the scheduler and reason about thread naming conventions.

## Sample Project
This folder contains a Maven project under `src/main/java` that simulates a background notification dispatcher.

### Build
```
mvn clean compile
```

### Run
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.basics.Application"
```

The application starts a dedicated worker thread that delivers queued notifications while the main thread reports progress. All lesson classes live in the single package `com.springcourse.threading.basics` to keep the structure focused on threading concepts rather than layering.

## Practice Exercises
Starter code lives in `practice/` with TODOs and failing tests to guide your work.

### Build Practice Project
```
cd practice
mvn clean compile
```

### Run Practice Scenario
```
mvn exec:java -Dexec.mainClass="com.springcourse.threading.basics.practice.PracticeApplication"
```

Follow the instructions in `practice/README.md` to finish the exercises.

## Estimated Time
- Reading: 20 minutes
- Sample walkthrough: 15 minutes
- Practice: 20–30 minutes

## Troubleshooting
- Ensure you have only one `java` executable on your path to avoid version mismatches.
- If output appears out of order, remember that the scheduler can interleave lines; this is expected and discussed in the lesson.
