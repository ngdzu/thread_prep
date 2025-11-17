# Concurrency Cheatsheet — classes covered in this course

This cheatsheet collects the important concurrency and threading classes mentioned in the lessons. For each class you'll find a short description and a minimal snippet showing the most useful methods.

---

## java.lang.Thread
- Purpose: low-level thread of execution.
- Common methods:

```java
Thread t = new Thread(runnable, "worker-1");
t.start();
t.join(); // wait until finished
t.join(1000); // join with timeout (ms)
t.interrupt(); // set interrupt flag
Thread current = Thread.currentThread();
Thread.State s = t.getState();
```

---

## java.lang.Runnable / java.util.concurrent.Callable
- Runnable: no return, Callable: return value + checked exceptions.

```java
Runnable r = () -> System.out.println("run");
Callable<String> c = () -> "result";
```

---

## java.util.concurrent.ExecutorService / Executors
- Purpose: manage pools of threads and submit tasks.

```java
ExecutorService pool = Executors.newFixedThreadPool(4);
Future<String> f = pool.submit(callable);
pool.execute(() -> { /* fire-and-forget */ });
pool.shutdown();
pool.awaitTermination(5, TimeUnit.SECONDS);
pool.shutdownNow();
```

---

## java.util.concurrent.Future
- Represents asynchronous result.

```java
Future<T> f = executor.submit(callable);
T val = f.get();
T withTimeout = f.get(1, TimeUnit.SECONDS);
f.cancel(true); // attempt to interrupt
f.isDone();
f.isCancelled();
```

---

## java.util.concurrent.CompletableFuture
- Async pipeline helper and composition.

```java
CompletableFuture.supplyAsync(() -> fetch())
    .thenApply(x -> transform(x))
    .exceptionally(ex -> fallback())
    .join();
```

---

## java.util.concurrent.BlockingQueue (LinkedBlockingQueue / ArrayBlockingQueue)
- Bounded/unbounded queue for producer-consumer.

```java
BlockingQueue<E> q = new LinkedBlockingQueue<>();
q.put(item); // blocks if full
E e = q.take(); // blocks if empty
q.offer(item); // immediate, false if bounded & full
q.poll(); // immediate or null if empty
```

---

## java.util.concurrent.ConcurrentLinkedQueue
- Non-blocking, unbounded queue for handoff (used in class sample).

```java
ConcurrentLinkedQueue<E> q = new ConcurrentLinkedQueue<>();
q.offer(e); // non-blocking, returns true for success
E e = q.poll(); // removes head or null
E head = q.peek();
```

---

## java.util.concurrent.ConcurrentHashMap
- Concurrent map with atomic per-key operations.

```java
ConcurrentHashMap<K,V> map = new ConcurrentHashMap<>();
map.putIfAbsent(key, value);
map.computeIfAbsent(key, k -> new Value());
map.compute(key, (k, v) -> v == null ? value : combine(v, value));
map.merge(key, value, (oldV, newV) -> mergeFunction(oldV, newV));
map.forEach((k,v) -> { /* concurrent traversal, weakly consistent */ });
```

---

## java.util.Collections.synchronizedMap
- Simple wrapper that synchronizes each method. Use only for low concurrency.

```java
Map<K,V> m = Collections.synchronizedMap(new HashMap<>());
synchronized(m) { // synchronize during iteration or compound ops
    for (Map.Entry<K,V> e : m.entrySet()) {}
}
```

---

## java.util.concurrent.atomic.LongAdder
- High-throughput counter for frequent concurrent increments.

```java
LongAdder adder = new LongAdder();
adder.increment();
adder.add(5);
long value = adder.sum();
long sample = adder.sumThenReset();
```

Notes: excellent for counters where small staleness is OK.

---

## java.util.concurrent.atomic.LongAccumulator
- Like LongAdder but for a custom associative operation (e.g., max).

```java
LongAccumulator max = new LongAccumulator(Math::max, Long.MIN_VALUE);
max.accumulate(42);
long currentMax = max.longValue();
```

---

## java.util.concurrent.atomic.AtomicLong / AtomicInteger / AtomicReference
- Atomic, CAS-based single-value updates.

```java
AtomicLong a = new AtomicLong(0);
a.incrementAndGet();
a.addAndGet(5);
boolean ok = a.compareAndSet(5, 10);
long prev = a.getAndSet(0);

AtomicReference<MyObject> ref = new AtomicReference<>(null);
ref.compareAndSet(oldVal, newVal);
```

---

## java.util.concurrent.locks.ReentrantLock and Condition
- ReentrantLock offers explicit lock control and Condition objects.

```java
ReentrantLock lock = new ReentrantLock();
lock.lock();
try { /* critical section */ } finally { lock.unlock(); }
if (lock.tryLock()) { /* no wait */ }
if (lock.tryLock(500, TimeUnit.MILLISECONDS)) { /* wait with timeout */ }

Condition cond = lock.newCondition();
cond.await(); // inside lock
cond.signal();
```

---

## java.util.concurrent.locks.ReentrantReadWriteLock
- Separates read and write locks for better parallelism.

```java
ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
rw.readLock().lock();
try { /* many readers allowed */ } finally { rw.readLock().unlock(); }
rw.writeLock().lock();
try { /* exclusive */ } finally { rw.writeLock().unlock(); }
```

---

## java.util.concurrent.CountDownLatch
- Waits until count reaches zero.

```java
CountDownLatch latch = new CountDownLatch(3);
// each worker:
latch.countDown();
// waiting thread:
latch.await();
```

---

## java.util.concurrent.Semaphore
- Controls access to limited resources (permits).

```java
Semaphore s = new Semaphore(3);
s.acquire();
try { /* use resource */ } finally { s.release(); }
if (s.tryAcquire()) { /* no wait */ }
```

---

## java.util.concurrent.CyclicBarrier
- Synchronizes threads to a common barrier point, reusable.

```java
CyclicBarrier b = new CyclicBarrier(4);
b.await(); // each party waits until all arrive
```

---

## java.lang.Object.wait/notify/notifyAll
- Low-level coordination for threads waiting on object monitors.

```java
synchronized(monitor) {
    while (!condition) { monitor.wait(); }
    // do work
    monitor.notify(); // or notifyAll()
}
```

---

## java.lang.management.ThreadMXBean & ThreadInfo
- Programmatic thread dump and deadlock detection.

```java
ThreadMXBean t = ManagementFactory.getThreadMXBean();
long[] deadlocked = t.findDeadlockedThreads();
ThreadInfo[] infos = t.getThreadInfo(deadlocked, true, true);
for (ThreadInfo ti : infos) System.out.println(ti);
```

---

## java.util.concurrent.CopyOnWriteArrayList
- Thread-safe for iteration-heavy workloads; writes copy the array.

```java
CopyOnWriteArrayList<E> list = new CopyOnWriteArrayList<>();
list.add(e);
for (E e : list) { /* safe during concurrent modification */ }
```

---

Notes and guidance
- Prefer high-level concurrent constructs (collections, executors) over low-level monitors.
- Use atomic classes for lock-free per-variable updates.
- When multiple locks are required, define and enforce a lock-ordering protocol or use tryLock with timeout.
- Use read/write locks when many reads and few writes occur.
- For metrics and counters prefer `LongAdder` or `LongAccumulator` when throughput matters; use `AtomicLong` when immediate exact semantics are required.

---

If you want, I can add runnable toy examples for a subset (e.g., Deadlock -> fix using ordering, LongAdder benchmarking vs AtomicLong, per-key max with LongAccumulator) and tests for them. Which example should I add first?
