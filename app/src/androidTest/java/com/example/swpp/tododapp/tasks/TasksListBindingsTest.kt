package com.example.swpp.tododapp.tasks

import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.swpp.tododapp.data.Task
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@SmallTest
@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {

    private lateinit var textView: TextView

    @Before
    fun setUp() {
        textView = TextView(ApplicationProvider.getApplicationContext())
        textView.visibility = View.GONE
        textView.text = ""
    }

    @Test
    fun setDueDateLabel_completedTaskWithDueDate_setsTextClearAndVisibilityVisible() {
        // GIVEN - A TextView initially GONE and a completed task with a due date
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = true,
            dueDate = LocalDate.of(2026, 10, 20)
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "CLEAR" and visibility is VISIBLE
        assertEquals("CLEAR", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_completedTaskWithNoDueDate_setsTextClearAndVisibilityVisible() {
        // GIVEN - A TextView initially GONE and a completed task with no due date
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = true,
            dueDate = null
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "CLEAR" and visibility is VISIBLE
        assertEquals("CLEAR", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskDueToday_setsTextDDayAndVisibilityVisible() {
        // GIVEN - An active task due today
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = LocalDate.now()
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "D-Day" and visibility is VISIBLE
        assertEquals("D-Day", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskDueInFiveDays_setsTextDMinus5AndVisibilityVisible() {
        // GIVEN - An active task due in 5 days
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = LocalDate.now().plusDays(5)
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "D-5" and visibility is VISIBLE
        assertEquals("D-5", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskOverdueByThreeDays_setsTextDPlus3AndVisibilityVisible() {
        // GIVEN - An active task overdue by 3 days
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = LocalDate.now().minusDays(3)
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "D+3" and visibility is VISIBLE
        assertEquals("D+3", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_nullTaskOnPreviouslyVisibleTextView_clearsTextAndSetsVisibilityGone() {
        // GIVEN - A TextView initially showing "D-5" with VISIBLE
        textView.text = "D-5"
        textView.visibility = View.VISIBLE

        // WHEN - setDueDateLabel is called with null task
        setDueDateLabel(textView, null)

        // THEN - textView.text is "" and visibility is GONE
        assertEquals("", textView.text.toString())
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskWithNoDueDateOnPreviouslyVisibleTextView_clearsTextAndSetsVisibilityGone() {
        // GIVEN - A TextView initially showing "D-5" with VISIBLE and an active task with no due date
        textView.text = "D-5"
        textView.visibility = View.VISIBLE
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = null
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "" and visibility is GONE
        assertEquals("", textView.text.toString())
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskOverdueByOneDay_setsTextDPlus1AndVisibilityVisible() {
        // GIVEN - An active task overdue by 1 day
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = LocalDate.now().minusDays(1)
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "D+1" and visibility is VISIBLE
        assertEquals("D+1", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskDueTodayBoundary_setsTextDDayAndVisibilityVisible() {
        // GIVEN - An active task due today (boundary)
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = LocalDate.now()
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "D-Day" and visibility is VISIBLE
        assertEquals("D-Day", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskDueTomorrow_setsTextDMinus1AndVisibilityVisible() {
        // GIVEN - An active task due tomorrow
        val task = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = LocalDate.now().plusDays(1)
        )

        // WHEN - setDueDateLabel is called
        setDueDateLabel(textView, task)

        // THEN - textView.text is "D-1" and visibility is VISIBLE
        assertEquals("D-1", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_completedTaskWithNoDueDateReboundAsActive_clearsTextAndSetsVisibilityGone() {
        // GIVEN - A TextView initially showing "CLEAR" from a completed task with no due date
        val completedTask = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = true,
            dueDate = null
        )
        setDueDateLabel(textView, completedTask)
        assertEquals("CLEAR", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)

        // WHEN - The same task becomes active and setDueDateLabel is called again
        val activeTask = completedTask.copy(isCompleted = false)
        setDueDateLabel(textView, activeTask)

        // THEN - textView.text is "" and visibility is GONE
        assertEquals("", textView.text.toString())
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_datedActiveTaskReboundToUndatedActiveTask_clearsTextAndSetsVisibilityGone() {
        // GIVEN - A TextView initially showing "D-5" from a dated active task
        val datedTask = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = LocalDate.now().plusDays(5)
        )
        setDueDateLabel(textView, datedTask)
        assertEquals("D-5", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)

        // WHEN - Rebound to an active task without a due date
        val undatedTask = Task(
            title = "Task 2",
            description = "Description",
            isCompleted = false,
            dueDate = null
        )
        setDueDateLabel(textView, undatedTask)

        // THEN - textView.text is "" and visibility is GONE
        assertEquals("", textView.text.toString())
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskReboundAsCompleted_updatesTextToClearAndVisibilityVisible() {
        // GIVEN - A TextView initially showing "D-5" from an active task
        val activeTask = Task(
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            dueDate = LocalDate.now().plusDays(5)
        )
        setDueDateLabel(textView, activeTask)
        assertEquals("D-5", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)

        // WHEN - Rebound to the completed version of the same task
        val completedTask = activeTask.copy(isCompleted = true)
        setDueDateLabel(textView, completedTask)

        // THEN - textView.text is "CLEAR" and visibility is VISIBLE
        assertEquals("CLEAR", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }
}
