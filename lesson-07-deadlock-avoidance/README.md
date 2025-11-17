# Lesson 07 — Avoiding Deadlock

Short summary and resources for avoiding deadlock in Java. See `lesson.md` for detailed guidance, examples, detection snippet, and exercises.

Key takeaways:

- Prefer `java.util.concurrent` and Executor-based designs over manual locks.
- Acquire multiple locks in a stable global order when necessary.
- Use `tryLock` with timeout and backoff to avoid indefinite waiting.
- Keep critical sections short and avoid I/O while holding locks.
