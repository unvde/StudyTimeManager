package com.example.studytimemanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.studytimemanager.model.Task
import com.example.studytimemanager.viewmodel.TaskViewModel

@Composable
fun DashboardScreen(taskViewModel: TaskViewModel) {
    var editMode by remember { mutableStateOf(false) }
    var taskTitle by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header with Edit toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium
            )
            TextButton(onClick = { editMode = !editMode }) {
                Text(if (editMode) "Done" else "Edit")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Section
        OutlinedTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("New Task") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                taskViewModel.addTask(taskTitle.trim())
                taskTitle = ""
            })
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                taskViewModel.addTask(taskTitle.trim())
                taskTitle = ""
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        LazyColumn {
            items(taskViewModel.tasks) { task ->
                TaskItem(
                    task = task,
                    onToggle = { taskViewModel.toggleTaskCompletion(task.id) },
                    onDelete = { taskViewModel.removeTask(task.id) },
                    editMode = editMode
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    editMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle() }
            )
            Text(
                text = task.title,
                style = if (task.isCompleted)
                    MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary)
                else
                    MaterialTheme.typography.bodyLarge
            )
        }
        if (editMode) {
            TextButton(onClick = { onDelete() }) {
                Text("Delete")
            }
        }
    }
}