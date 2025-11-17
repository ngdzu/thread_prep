# Java Thread Learning Course – Requirements

## Course Objective
Design a comprehensive, practice-focused curriculum that builds mastery of Java threading and concurrency through progressively challenging lessons, each anchored by runnable code and guided exercises.

## Course Roadmap
- Cover one core concept per lesson (e.g., thread lifecycle, synchronization primitives, executors, thread pools, concurrent collections, fork/join, reactive alternatives).
- Sequence lessons so later topics depend on knowledge from earlier ones.
- Provide a brief overview table in the root `README.md` summarizing lesson names, focus areas, and expected outcomes.

## Lesson Packaging
- Place every lesson in its own numbered folder: `lesson-01-introduction`, `lesson-02-thread-lifecycle`, etc.
- Each lesson folder must stand alone: a learner should be able to clone the repo and work on any lesson in isolation.

## Required Files Per Lesson

1. **README.md**
   - Lesson synopsis, prerequisites, and learning objectives in bullet form.
   - Step-by-step build and run instructions for the sample and practice projects using Maven commands.
   - Estimated completion time and tooling requirements (JDK version, IDE recommendations, optional profilers/visualizers).
   - Troubleshooting tips for common threading pitfalls addressed in the lesson.

2. **lesson.md** (300–500 words)
   - Deep dive into the single featured concept with real-world context.
   - Inline code excerpts that align with the sample project; highlight thread-safety concerns and debugging guidance.
   - Call out cautionary notes (e.g., deadlocks, race conditions) in dedicated “Watch Out” callouts.
   - Link back to any prerequisite lessons when referencing previously covered material.

3. **quiz.md**
   - 5–8 questions that can be answered using only `lesson.md` and the sample code.
   - Blend multiple-choice, short-answer, and scenario-driven questions that check both conceptual understanding and ability to reason about code behavior.
   - Include at least one question that asks learners to predict runtime behavior or identify a threading issue.

4. **answers.md**
   - Complete solutions with concise explanations. Reference relevant sections of `lesson.md` and code snippets when clarifying answers.
   - For scenario questions, show the reasoning path that leads to the correct conclusion.

5. **Sample Code**
   - Maven or Gradle project with standard layout (`src/main/java`, `src/main/resources`, optional `src/test/java`). Maven is preferred for consistency.
   - Domain-driven packages (controller, service, repository, etc.) that reflect the lesson scenario.
   - Demonstrate the featured concept in a runnable, production-quality example (no toy code). Each example must include logging or metrics illustrating concurrency behavior.
   - Provide minimal automated tests or verification scripts when practical to prove thread-safety or correctness.
   - Document entry points (`main` classes or scripts) in `README.md` with sample command output.

6. **practice/** folder
   - Add `practice/` to the root `.gitignore` so learner work is excluded from commits.
   - Mirror the sample project structure with scaffolded classes, TODO markers, and failing tests or assertions that drive the exercises.
   - Include a short `practice/README.md` describing the exercise goals, acceptance criteria, and how to verify success (e.g., passing tests, console output).
   - Ensure the practice build succeeds out-of-the-box, even if tests fail initially.

## Course-Wide Quality Guidelines
- Maintain consistent coding standards: Java 17+, meaningful naming, clear concurrency boundaries, and inline comments only where non-obvious behavior occurs.
- Verify every sample runs without warnings using `mvn clean verify` (or the equivalent Gradle task) before publishing.
- Gradually increase complexity: start with basic thread creation, move to synchronization, then higher-level abstractions, monitoring, and performance tuning.
- Use realistic business or systems scenarios (batch processing, real-time feeds, async services) to illustrate why each concept matters.
- Provide reflection prompts or stretch ideas at the end of each lesson to encourage exploration beyond the core requirements.

Follow the structure and tone established in the existing lessons, enhancing depth and rigor as the course progresses.