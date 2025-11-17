# Lesson 01 Quiz Answers – Thread Basics

1. **Because `start` launches a new call stack.** The JVM asks the operating system to schedule the new thread, then immediately returns to the caller so both stacks can run independently. The target thread begins executing `run` once the OS allocates CPU time.
2. **B.** Extending `Thread` mixes business logic with threading concerns and prevents reuse in other execution models. `Runnable` instances can be reused (contra option A), do not get automatic names (contra C), and extending `Thread` still allows `setName` (contra D).
3. **Improved observability.** Supplying a descriptive name means log lines and stack traces clearly show which worker produced each message, making it easier to correlate actions in concurrent output.
4. **The work becomes synchronous.** Calling `run` executes the dispatcher on the main thread, so no concurrent execution occurs. The main thread blocks until the dispatcher finishes, eliminating interleaved output and defeating the lesson’s goal.
5. **It yields to the scheduler and exaggerates interleaving.** Sleeping hands control back to the OS, letting other threads run and giving you time-staggered logs. Removing the sleep would still be correct but the worker might finish so quickly that its log lines cluster together, hiding the effect of concurrency.
6. **Call `worker.join()`.** Joining ensures the main thread waits for the worker to finish before exiting. Without `join`, the JVM can terminate once only daemon threads remain, so you risk ending the program before the final dispatch message.
