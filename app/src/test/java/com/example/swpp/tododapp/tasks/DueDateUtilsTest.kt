package com.example.swpp.tododapp.tasks

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DueDateUtilsTest {

    @Test
    fun dueDateLabel_completedTaskWithDueDate_returnsClear() {
        // GIVEN - A completed task with a due date
        val dueDate = LocalDate.of(2026, 10, 20)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = true

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "CLEAR" is returned
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_activeTaskWithNoDueDate_returnsEmptyString() {
        // GIVEN - An active task with no due date
        val dueDate: LocalDate? = null
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - An empty string is returned
        assertEquals("", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueToday_returnsDDay() {
        // GIVEN - An active task due today
        val dueDate = LocalDate.of(2026, 10, 15)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-Day" is returned
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueInFiveDays_returnsDMinus5() {
        // GIVEN - An active task due in 5 days
        val dueDate = LocalDate.of(2026, 10, 20)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-5" is returned
        assertEquals("D-5", result)
    }

    @Test
    fun dueDateLabel_activeTaskOverdueByFiveDays_returnsDPlus5() {
        // GIVEN - An active task overdue by 5 days
        val dueDate = LocalDate.of(2026, 10, 10)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D+5" is returned
        assertEquals("D+5", result)
    }

    @Test
    fun dueDateLabel_activeTaskOverdueByOneDay_returnsDPlus1() {
        // GIVEN - An active task overdue by 1 day
        val dueDate = LocalDate.of(2026, 10, 14)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D+1" is returned
        assertEquals("D+1", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueTodayBoundary_returnsDDay() {
        // GIVEN - An active task due on the boundary date (today)
        val dueDate = LocalDate.of(2026, 10, 15)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-Day" is returned
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueInOneDay_returnsDMinus1() {
        // GIVEN - An active task due in 1 day
        val dueDate = LocalDate.of(2026, 10, 16)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-1" is returned
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_completedTaskDueToday_returnsClear() {
        // GIVEN - A completed task due today
        val dueDate = LocalDate.of(2026, 10, 15)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = true

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "CLEAR" is returned
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_activeTaskAcrossMonthBoundary_returnsDMinus1() {
        // GIVEN - An active task due on Nov 1 with today being Oct 31
        val dueDate = LocalDate.of(2026, 11, 1)
        val today = LocalDate.of(2026, 10, 31)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-1" is returned
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_activeTaskAcrossYearBoundary_returnsDMinus1() {
        // GIVEN - An active task due on Jan 1 with today being Dec 31
        val dueDate = LocalDate.of(2027, 1, 1)
        val today = LocalDate.of(2026, 12, 31)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-1" is returned
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_activeTaskAcrossLeapYearBoundary_returnsDMinus1() {
        // GIVEN - An active task due on Feb 29 with today being Feb 28 in a leap year
        val dueDate = LocalDate.of(2028, 2, 29)
        val today = LocalDate.of(2028, 2, 28)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-1" is returned
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueIn31DaysSpanningFullMonth_returnsDMinus31() {
        // GIVEN - An active task due in 31 calendar days spanning a full month
        val dueDate = LocalDate.of(2026, 11, 15)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-31" is returned
        assertEquals("D-31", result)
    }

    @Test
    fun dueDateLabel_activeTaskOverdueBy31DaysSpanningFullMonth_returnsDPlus31() {
        // GIVEN - An active task overdue by 31 calendar days spanning a full month
        val dueDate = LocalDate.of(2026, 10, 15)
        val today = LocalDate.of(2026, 11, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D+31" is returned
        assertEquals("D+31", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueIn365DaysSpanningFullYear_returnsDMinus365() {
        // GIVEN - An active task due in 365 calendar days spanning a full non-leap year
        val dueDate = LocalDate.of(2027, 10, 15)
        val today = LocalDate.of(2026, 10, 15)
        val isCompleted = false

        // WHEN - dueDateLabel is called
        val result = dueDateLabel(dueDate, today, isCompleted)

        // THEN - "D-365" is returned
        assertEquals("D-365", result)
    }
}
