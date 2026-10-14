
# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Builds and returns the D-day label string shown next to a task in the list ("CLEAR", "", "D-Day", "D-N", or "D+N") based on the task's completion status, due date, and reference today date. The number of days must represent the total elapsed calendar days between the two dates, including across month and year boundaries.

**Pre-conditions**
- `today` is a valid, non-null `LocalDate`.
- `dueDate` is either `null` or a valid `LocalDate`.
- Date differences are calculated using total calendar days, not the day component of a year-month-day period.
- Completion status takes precedence over due date calculations.

**Post-conditions**
- Success cases
  - Returns `"CLEAR"` when `isCompleted` is `true`, regardless of whether `dueDate` is present or `null`. `[Unit]`
    - Tip: Given dueDate = 2026-10-20, today = 2026-10-15, and isCompleted = true, when dueDateLabel is called, then it returns "CLEAR".
  - Returns `""` (empty string) when `isCompleted` is `false` and `dueDate` is `null`. `[Unit]`
    - Tip: Given dueDate = null, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "".
  - Returns `"D-Day"` when `isCompleted` is `false` and `dueDate` equals `today`. `[Unit]`
    - Tip: Given dueDate = 2026-10-15, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "D-Day".
  - Returns `"D-N"` (where N is the total positive number of calendar days between `today` and `dueDate`) when `isCompleted` is `false` and `dueDate` is in the future relative to `today`. `[Unit]`
    - Tip: Given dueDate = 2026-10-20, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "D-5".
  - Returns `"D+N"` (where N is the total positive number of calendar days past `dueDate` relative to `today`) when `isCompleted` is `false` and `dueDate` is in the past relative to `today`. `[Unit]`
    - Tip: Given dueDate = 2026-10-10, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "D+5".

- Error cases
  - None. `[Unit]`

- Edge cases
  - Returns `"D+1"` for an active task overdue by exactly 1 calendar day (right next to boundary on past side). `[Unit]`
    - Tip: Given dueDate = 2026-10-14, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "D+1".
  - Returns `"D-Day"` for an active task due on the exact same date as `today` (at the boundary). `[Unit]`
    - Tip: Given dueDate = 2026-10-15, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "D-Day".
  - Returns `"D-1"` for an active task due in exactly 1 calendar day (right next to boundary on future side). `[Unit]`
    - Tip: Given dueDate = 2026-10-16, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "D-1".
  - Returns `"CLEAR"` for a completed task due on `today` (boundary between completed and active task). `[Unit]`
    - Tip: Given dueDate = 2026-10-15, today = 2026-10-15, and isCompleted = true, when dueDateLabel is called, then it returns "CLEAR".
  - Returns `"D-1"` across a month boundary when `today` is the last day of a month and `dueDate` is the first day of the following month. `[Unit]`
    - Tip: Given dueDate = 2026-11-01, today = 2026-10-31, and isCompleted = false, when dueDateLabel is called, then it returns "D-1".
  - Returns `"D-1"` across a year boundary when `today` is Dec 31 and `dueDate` is Jan 1 of the next year. `[Unit]`
    - Tip: Given dueDate = 2027-01-01, today = 2026-12-31, and isCompleted = false, when dueDateLabel is called, then it returns "D-1".
  - Returns `"D-1"` across a leap year boundary when `today` is Feb 28 of a leap year and `dueDate` is Feb 29. `[Unit]`
    - Tip: Given dueDate = 2028-02-29, today = 2028-02-28, and isCompleted = false, when dueDateLabel is called, then it returns "D-1".

  - Returns `"D-31"` when an active task is due exactly 31 calendar days in the future, spanning a full calendar month. `[Unit]`
    - Tip: Given dueDate = 2026-11-15, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "D-31".

  - Returns `"D+31"` when an active task is overdue by exactly 31 calendar days, spanning a full calendar month. `[Unit]`
    - Tip: Given dueDate = 2026-10-15, today = 2026-11-15, and isCompleted = false, when dueDateLabel is called, then it returns "D+31".

  - Returns `"D-365"` when an active task is due exactly 365 calendar days in the future, spanning a full non-leap-year interval. `[Unit]`
    - Tip: Given dueDate = 2027-10-15, today = 2026-10-15, and isCompleted = false, when dueDateLabel is called, then it returns "D-365".

**Undefined behavior**
- None.
