package com.example.studytimemanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.studytimemanager.viewmodel.TaskViewModel
import com.example.studytimemanager.model.Task

@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {
    var taskTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Task Manager",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input Field
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

        // Add Button
        Button(
            onClick = {
                taskViewModel.addTask(taskTitle.trim())
                taskTitle = ""
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        LazyColumn {
            items(taskViewModel.tasks) { task ->
                TaskItem(
                    task = task,
                    onToggle = { taskViewModel.toggleTaskCompletion(task.id) }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = task.title,
            style = if (task.isCompleted)
                MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary)
            else
                MaterialTheme.typography.bodyLarge
        )
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onToggle() }
        )
    }
}
