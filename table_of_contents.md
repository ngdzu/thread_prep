# Java Thread Learning Course

| Lesson | Folder                           | Core Concept                                                         | Sample Scenario                                                  | Practice Focus                                    |
| ------ | -------------------------------- | -------------------------------------------------------------------- | ---------------------------------------------------------------- | ------------------------------------------------- |
| 1      | lesson-01-thread-basics          | Why threads matter, creating and naming threads                      | Background notification dispatcher using `Thread` and `Runnable` | Add logging and alternate workloads               |
| 2      | lesson-02-thread-lifecycle       | Thread states, lifecycle management, joining, sleeping, interrupting | Data export pipeline coordinating worker threads                 | Sequence tasks and handle interrupts              |
| 3      | lesson-03-shared-state           | Race conditions, `synchronized` blocks, visibility guarantees        | Inventory counter updated by sales terminals                     | Prove and fix data races                          |
| 4      | lesson-04-wait-notify            | Intrinsic locks, wait/notify patterns, condition management          | Producer/consumer queue for order fulfillment                    | Extend coordination to handle back-pressure       |
| 5      | lesson-05-executor-service       | `ExecutorService`, `Callable`, futures, graceful shutdown            | Image thumbnail rendering service using thread pools             | Tune pool sizes and add cancellation              |
| 6      | lesson-06-concurrent-collections | Atomic classes, concurrent maps, safe read/write patterns            | Real-time analytics dashboard aggregations                       | Introduce new metrics with thread-safe structures |

The course progresses from foundational thread creation to higher-level concurrency utilities, ensuring each lesson builds directly on prior knowledge.
