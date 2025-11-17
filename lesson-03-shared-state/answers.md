# Lesson 03 Quiz Answers – Shared State and Synchronization

1. **Concurrent mutation without coordination.** Multiple threads must read and write the same shared data, and at least one path must be a write. Without synchronization they can interleave in a way that corrupts the inventory count.
Or A race condition requires at least two threads performing a *non-atomic* **read-modify-write** (e.g., read a value, compute a new value, write it back) on the same shared state without proper synchronization.

2. **B.** Leaving a synchronized block publishes the thread’s writes so the next thread acquiring the same lock sees a consistent snapshot. The other options either exaggerate the effect or describe unrelated properties.
3. **To contrast corrupted and correct outcomes.** The unsafe run highlights the race by producing erratic totals. The synchronized run applies the same workload with locking and yields the expected balance, demonstrating that proper synchronization restores correctness.
4. **Reads can observe stale or partially updated state.** Without synchronization, a reader can see cached values from before a write completed, leading to incorrect decisions (e.g., selling items that are out of stock) or violating invariants relied upon elsewhere.
5. **To minimize contention while still guarding the invariant.** Only the operations that read-modify-write the shared map need to be serialized. Keeping the synchronized block tight preserves more parallelism for other work.
6. **Possible issues:** 
- Wrong lock or inconsistent locking: different code paths may synchronize on different objects (e.g., `synchronized(this)` on different instances), which provides no mutual exclusion. Ensure all accesses to the same shared data use the same monitor or use a single lock object.
- Some paths still use the unsafe API or mutate internal structures without synchronization (or use non-thread-safe collections): parts of the code may be calling `remainingUnsafe` or directly manipulating fields, or using collections like HashMap without locking.

Also check for other issues: double bookkeeping bugs, multiple ledger instances, or failing to synchronize composite operations that span multiple fields.
