# Lesson 01 Quiz Answers – Thread Basics

1. **Why does `Thread.start()` return immediately?**
	`Thread.start()` asks the JVM/OS to create and schedule a new native thread, then immediately returns so the caller can continue. The new thread will run the `run()` method when the OS gives it CPU time. (By contrast, calling `run()` directly executes on the current thread and does not create a new thread.)

2. **Correct choice: B.**
	Extending `Thread` mixes your business logic with threading mechanics and couples the task to a specific threading implementation. Implementing `Runnable` keeps the work separate and makes it easier to reuse or run on different executors.

3. **Why name a thread?**
	A descriptive thread name makes logs, thread dumps, and debugging output much easier to read. Names help you identify which thread produced which messages when multiple threads run concurrently.

4. **What happens if you call `run()` instead of `start()`?**
	Calling `run()` directly executes the task on the current (calling) thread — it runs synchronously and no new thread is created. `start()` requests the JVM/OS to create a new native thread that will invoke the thread's `run()` method; scheduling and the exact start time are handled by the OS/JVM. `start()` must be called once — calling `run()` directly does not create a new thread.

5. **Why does the dispatcher call `Thread.sleep(...)` and what if you remove it?**
	`Thread.sleep(...)` pauses the current thread for a short time, allowing the scheduler to run other threads and producing staggered log output. Removing the sleep will make the dispatcher loop run faster (and possibly consume more CPU); output may appear in quick bursts and concurrency effects may be less apparent. Note: `Thread.sleep` throws `InterruptedException`, so it must be handled or declared.

6. **Main finishes before worker — how to fix?**
	Call `worker.join()` so the main thread waits for the worker to finish before exiting. Without `join`, the JVM might exit when only daemon threads remain, causing the program to end before the worker completes.
