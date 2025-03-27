package com.example.studytimemanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studytimemanager.viewmodel.TaskViewModel

@Composable
fun DashboardScreen(taskViewModel: TaskViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Upcoming Tasks:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(taskViewModel.tasks) { task ->
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
            }
        }
    }
}
