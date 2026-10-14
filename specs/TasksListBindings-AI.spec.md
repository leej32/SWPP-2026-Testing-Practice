# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Binds a task's D-day label to a TextView by setting its text and making the View visible if a non-empty label exists for the task.

**Pre-conditions**
- `textView` is a valid, non-null `TextView` instance.

**Post-conditions**
- Success cases
  - Sets `textView.text` to `"CLEAR"` and `textView.visibility` to `View.VISIBLE` when `task` is completed (`isCompleted = true`). `[Integration]`
    - Tip: Given textView with initial visibility View.GONE and task with isCompleted = true and dueDate = 2026-10-20, when setDueDateLabel is called, then textView.text is "CLEAR" and textView.visibility is View.VISIBLE.
  - Sets `textView.text` to `"D-Day"` and `textView.visibility` to `View.VISIBLE` when `task` is active and due today (`dueDate` equals today's date). `[Integration]`
    - Tip: Given textView with initial visibility View.GONE and task with isCompleted = false and dueDate = LocalDate.now(), when setDueDateLabel is called, then textView.text is "D-Day" and textView.visibility is View.VISIBLE.
  - Sets `textView.text` to `"D-N"` and `textView.visibility` to `View.VISIBLE` when `task` is active and due N days in the future relative to today's date. `[Integration]`
    - Tip: Given textView with initial visibility View.GONE and task with isCompleted = false and dueDate = LocalDate.now().plusDays(5), when setDueDateLabel is called, then textView.text is "D-5" and textView.visibility is View.VISIBLE.
  - Sets `textView.text` to `"D+N"` and `textView.visibility` to `View.VISIBLE` when `task` is active and overdue by N days relative to today's date. `[Integration]`
    - Tip: Given textView with initial visibility View.GONE and task with isCompleted = false and dueDate = LocalDate.now().minusDays(3), when setDueDateLabel is called, then textView.text is "D+3" and textView.visibility is View.VISIBLE.
  - Leaves `textView.text` and `textView.visibility` unchanged when `task` is `null`. `[Integration]`
    - Tip: Given textView with initial text "" and visibility View.GONE and task = null, when setDueDateLabel is called, then textView.text is "" and textView.visibility is View.GONE.
  - Leaves `textView.text` and `textView.visibility` unchanged when `task` is active and has no due date (`dueDate = null`). `[Integration]`
    - Tip: Given textView with initial text "" and visibility View.GONE and task with isCompleted = false and dueDate = null, when setDueDateLabel is called, then textView.text is "" and textView.visibility is View.GONE.
- Error cases
  - None. `[Integration]`
- Edge cases
  - Sets `textView.text` to `"D+1"` and `textView.visibility` to `View.VISIBLE` for an active task overdue by exactly 1 day relative to today's date (overdue boundary). `[Integration]`
    - Tip: Given textView with initial visibility View.GONE and task with isCompleted = false and dueDate = LocalDate.now().minusDays(1), when setDueDateLabel is called, then textView.text is "D+1" and textView.visibility is View.VISIBLE.
  - Sets `textView.text` to `"D-Day"` and `textView.visibility` to `View.VISIBLE` for an active task due on today's date (due today boundary). `[Integration]`
    - Tip: Given textView with initial visibility View.GONE and task with isCompleted = false and dueDate = LocalDate.now(), when setDueDateLabel is called, then textView.text is "D-Day" and textView.visibility is View.VISIBLE.
  - Sets `textView.text` to `"D-1"` and `textView.visibility` to `View.VISIBLE` for an active task due in exactly 1 day relative to today's date (future boundary). `[Integration]`
    - Tip: Given textView with initial visibility View.GONE and task with isCompleted = false and dueDate = LocalDate.now().plusDays(1), when setDueDateLabel is called, then textView.text is "D-1" and textView.visibility is View.VISIBLE.

**Undefined behavior**
- None.
