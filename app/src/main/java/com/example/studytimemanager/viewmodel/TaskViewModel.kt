package com.example.studytimemanager.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.studytimemanager.model.Task

class TaskViewModel : ViewModel() {
    // Internal mutable task list
    private val _tasks = mutableStateListOf<Task>()

    // External read-only access to tasks
    val tasks: List<Task> get() = _tasks

    // Counter for assigning unique task IDs
    private var taskIdCounter = 0

    // Add a new task if the title is not blank
    fun addTask(title: String) {
        if (title.isNotBlank()) {
            _tasks.add(Task(id = taskIdCounter++, title = title))
        }
    }

    // Toggle the completion state of a task
    fun toggleTaskCompletion(taskId: Int) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val task = _tasks[index]
            _tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }

    // Remove a task by ID
    fun removeTask(taskId: Int) {
        _tasks.removeAll { it.id == taskId }
    }
}