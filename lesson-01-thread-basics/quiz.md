# Lesson 01 Quiz – Thread Basics

1. **Conceptual:** Why does calling `Thread.start()` immediately return control to the caller, and what happens under the hood after it returns?
2. **Multiple Choice:** Which statement is true when comparing `Thread` subclasses to `Runnable` implementations?
   - A. `Runnable` instances cannot be reused once a thread finishes.
   - B. Extending `Thread` tightly couples work logic to threading mechanics.
   - C. `Runnable` implementations are automatically named after their class.
   - D. Extending `Thread` prevents you from setting a thread name.
3. **Practical:** In the sample, what is the primary benefit of passing a custom name to the `Thread` constructor?
4. **Scenario:** Suppose a developer replaces `worker.start()` with `worker.run()` in the sample. Describe the resulting behavior compared with the original implementation.
5. **Code Reasoning:** The dispatcher calls `Thread.sleep(Duration.ofMillis(300))`. What does this accomplish, and how would removing the sleep affect the sample output?
6. **Troubleshooting:** If your console output shows the main thread finishing before the worker prints “Dispatch complete,” what adjustment should you make, and why?
