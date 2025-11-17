## Quiz — Avoiding Deadlock

1. What is the most common immediate cause of deadlock in Java programs?
   - A) Too many threads
   - B) Nested locks acquired in inconsistent orders
   - C) Garbage collection pauses
   - D) Using Executors

2. Which of the following is a recommended strategy to avoid deadlock when multiple locks are necessary?
   - A) Acquire locks in random order to spread contention
   - B) Acquire locks in a consistent global order
   - C) Use Thread.sleep between lock acquisitions
   - D) Use synchronized and ReentrantLock together

3. When should you prefer `tryLock` with a timeout over `lock()`?
   - A) When you want to block forever
   - B) When you want to back off and retry or fail fast instead of waiting indefinitely
   - C) Always — `lock()` is deprecated
   - D) Only in single-threaded programs

4. Which Java API can help avoid manual locking by providing concurrent-safe collections?
   - A) java.util.HashMap
   - B) java.io.File
   - C) java.util.concurrent
   - D) java.lang.reflect

5. True or False: Holding a lock while performing network I/O is safe and recommended.
