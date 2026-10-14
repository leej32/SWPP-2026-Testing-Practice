
# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Binds a task's D-day label to a TextView by updating both its text and visibility. The TextView must always reflect the currently bound task, including when a previously visible label needs to be cleared. Reusing a TextView must never leave a stale label from an earlier task or state.

**Pre-conditions**
- `textView` is a valid, non-null `TextView` instance.
- `task` may be null.
- The TextView may contain text and visibility settings from a previously bound task.

**Post-conditions**
- Success cases
  - Sets `textView.text` to `"CLEAR"` and `textView.visibility` to `View.VISIBLE` when `task` is completed, regardless of its due date. `[Integration]`
    - Tip: Given textView initially GONE and a completed task with dueDate = 2026-10-20, when setDueDateLabel is called, then textView.text is "CLEAR" and visibility is VISIBLE.
  - Sets `textView.text` to `"CLEAR"` and `textView.visibility` to `View.VISIBLE` when a completed task has no due date. `[Integration]`
    - Tip: Given textView initially GONE and a completed task with dueDate = null, when setDueDateLabel is called, then textView.text is "CLEAR" and visibility is VISIBLE.
  - Sets `textView.text` to `"D-Day"` and `textView.visibility` to `View.VISIBLE` when an active task is due today. `[Integration]`
    - Tip: Given an active task with dueDate = LocalDate.now(), when setDueDateLabel is called, then textView.text is "D-Day" and visibility is VISIBLE.
  - Sets `textView.text` to `"D-5"` and `textView.visibility` to `View.VISIBLE` when an active task is due in five days. `[Integration]`
    - Tip: Given an active task with dueDate = LocalDate.now().plusDays(5), when setDueDateLabel is called, then textView.text is "D-5" and visibility is VISIBLE.
  - Sets `textView.text` to `"D+3"` and `textView.visibility` to `View.VISIBLE` when an active task is overdue by three days. `[Integration]`
    - Tip: Given an active task with dueDate = LocalDate.now().minusDays(3), when setDueDateLabel is called, then textView.text is "D+3" and visibility is VISIBLE.
  - Sets `textView.text` to `""` and `textView.visibility` to `View.GONE` when `task` is null. `[Integration]`
    - Tip: Given textView initially showing "D-5" with visibility VISIBLE and task = null, when setDueDateLabel is called, then textView.text is "" and visibility is GONE.
  - Sets `textView.text` to `""` and `textView.visibility` to `View.GONE` when `task` is active and has no due date. `[Integration]`
    - Tip: Given textView initially showing "D-5" with visibility VISIBLE and an active task with dueDate = null, when setDueDateLabel is called, then textView.text is "" and visibility is GONE.

- Error cases
  - None. `[Integration]`

- Edge cases
  - Sets `"D+1"` and `View.VISIBLE` for an active task overdue by exactly one day. `[Integration]`
    - Tip: Given an active task with dueDate = LocalDate.now().minusDays(1), when setDueDateLabel is called, then textView.text is "D+1" and visibility is VISIBLE.
  - Sets `"D-Day"` and `View.VISIBLE` for an active task due today. `[Integration]`
    - Tip: Given an active task with dueDate = LocalDate.now(), when setDueDateLabel is called, then textView.text is "D-Day" and visibility is VISIBLE.
  - Sets `"D-1"` and `View.VISIBLE` for an active task due tomorrow. `[Integration]`
    - Tip: Given an active task with dueDate = LocalDate.now().plusDays(1), when setDueDateLabel is called, then textView.text is "D-1" and visibility is VISIBLE.
  - Clears a stale `"CLEAR"` label when a completed task with no due date becomes active. `[Integration]`
    - Tip: Given a TextView initially showing "CLEAR" with visibility VISIBLE, first bind a completed task with dueDate = null, then bind the same task after changing isCompleted to false. The final text must be "" and visibility must be GONE.
  - Clears a stale due-date label when rebinding from a dated active task to an undated active task. `[Integration]`
    - Tip: Given a TextView initially showing "D-5" with visibility VISIBLE, bind an active task with dueDate = null. The final text must be "" and visibility must be GONE.
  - Updates the label when completion status changes from active to completed. `[Integration]`
    - Tip: Given an active task with dueDate = LocalDate.now().plusDays(5) initially showing "D-5", bind the completed version of the same task. The final text must be "CLEAR" and visibility must be VISIBLE.

**Undefined behavior**
- None.
