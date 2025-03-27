package com.example.studytimemanager.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.studytimemanager.model.Task

class TaskViewModel : ViewModel() {
    // List to hold tasks
    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    private var taskIdCounter = 0

    // Add new task
    fun addTask(title: String) {
        if (title.isNotBlank()) {
            _tasks.add(Task(id = taskIdCounter++, title = title))
        }
    }

    // Mark task as completed
    fun toggleTaskCompletion(taskId: Int) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val task = _tasks[index]
            _tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }

    // Optional: Remove a task
    fun removeTask(taskId: Int) {
        _tasks.removeAll { it.id == taskId }
    }
}
