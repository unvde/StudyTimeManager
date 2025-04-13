package com.example.studytimemanager

import com.example.studytimemanager.viewmodel.TaskViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TaskViewModelTest {

    private lateinit var viewModel: TaskViewModel

    @Before
    fun setup() {
        viewModel = TaskViewModel()
    }

    @Test
    fun testAddTask_increasesTaskList() {
        val initialSize = viewModel.tasks.size
        viewModel.addTask("Test Task")
        assertEquals(initialSize + 1, viewModel.tasks.size)
    }

    @Test
    fun testToggleTaskCompletion() {
        viewModel.addTask("Toggle Test")
        val task = viewModel.tasks.first()
        val original = task.isCompleted
        viewModel.toggleTaskCompletion(task.id)
        assertNotEquals(original, task.isCompleted)
    }

    @Test
    fun testRemoveTask_decreasesSize() {
        viewModel.addTask("To be removed")
        val task = viewModel.tasks.first()
        viewModel.removeTask(task.id)
        assertTrue(viewModel.tasks.none { it.id == task.id })
    }
}