## Answers — Quiz: Avoiding Deadlock

1. B) Nested locks acquired in inconsistent orders

2. B) Acquire locks in a consistent global order

3. B) When you want to back off and retry or fail fast instead of waiting indefinitely

4. C) java.util.concurrent

5. False — holding locks during network I/O can lead to long blocking and increase deadlock risk; collect data inside the lock and perform I/O after releasing it.
