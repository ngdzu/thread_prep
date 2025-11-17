# Lesson 03 – Shared State and Synchronization

Threads become dangerous once they share data. In previous lessons each worker owned its state, so interleaving had no side effects. Here we simulate four point-of-sale terminals decrementing a shared inventory count. Without coordination, we expect an initial stock of 120 units to drop to 40 after selling twenty items per terminal. Run the unsafe version and you will often see bizarre results such as 55 or 22 units remaining. That discrepancy is a race condition: two terminals read the same snapshot, subtract one, and both write back the same value, effectively losing a sale.

Java’s first line of defense is the intrinsic lock associated with every object. When you mark a method `synchronized`, the JVM acquires the lock on `this` before entering and releases it when leaving. In the sample, `InventoryLedger.recordSale` is synchronized so only one terminal at a time can update the shared `Map<String, Integer> stockLevels`. We also guard read operations with synchronized methods to ensure they see the latest state.

Synchronized blocks give even finer control:

```java
synchronized (lock) {
    int remaining = stockLevels.get(itemSku) - quantity;
    stockLevels.put(itemSku, remaining);
}
```

By keeping the critical section small, we minimize contention yet still guarantee atomicity. The key is to guard *all* paths that touch shared data with the same lock object. Mixing different locks (for example `this` in one method and `lock` in another) creates windows where races reappear.

> ⚠️ Watch Out: Synchronization also provides *visibility*. When a thread exits a synchronized block, the JVM flushes any cached changes back to main memory. Other threads acquiring the same lock will see the updated values. Without this, even reads could return stale data.

To reveal races quickly we use high iteration counts and assertions. The sample runs both unsafe and synchronized scenarios back-to-back. The unsafe path deliberately avoids synchronization and throws an assertion error when the final count mismatches the expected value, proving the race. The safe path wraps the same code with synchronized access and succeeds, demonstrating how locks enforce the invariants.

Synchronization comes at a cost: threads now wait for one another, reducing parallelism. Monitor the log timestamps to see how terminals queue up. In production systems you should balance correctness with throughput by reducing lock scope, partitioning data, or using higher-level concurrency utilities (next lesson). But never compromise correctness—data races corrupt business state and are notoriously difficult to debug.

Before moving on, experiment with the sample. Increase the number of terminals, change the quantity per sale, or remove the synchronized keyword to observe how quickly the ledger collapses. Developing an intuition for where state is shared will guide your design choices in every concurrent system you build.
