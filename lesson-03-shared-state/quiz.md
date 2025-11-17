# Lesson 03 Quiz – Shared State and Synchronization

1. **Conceptual:** What conditions must be met for a race condition to occur in the inventory ledger sample?

2. **Multiple Choice:** Which statement best describes the visibility guarantees provided by `synchronized`?
   - A. Synchronization prevents threads from ever running simultaneously.
   - B. Exiting a synchronized block flushes writes to main memory so the next thread entering sees up-to-date values.
   - C. Synchronization only protects writes; reads are still eventually consistent.
   - D. Synchronization guarantees faster execution by optimizing CPU caches.

3. **Practical:** Why does the sample include both an unsafe and a synchronized ledger run, and what conclusion should you draw from the difference in results?

4. **Scenario:** Two developers guard writes with `synchronized(this)` but leave reads unsynchronized because "reads are harmless." Describe the risk.

5. **Code Reasoning:** In the synchronized ledger, why do we narrow the critical section instead of guarding the entire `sell` method body?

6. **Troubleshooting:** You still observe incorrect totals even after adding `synchronized` to the selling method. List two potential causes.
