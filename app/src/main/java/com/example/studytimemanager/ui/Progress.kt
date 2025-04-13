package com.example.studytimemanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import com.example.studytimemanager.viewmodel.StudySessionViewModel
import com.example.studytimemanager.viewmodel.ProgressViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProgressScreen(
    studyViewModel: ProgressViewModel,
    sessionViewModel: StudySessionViewModel
) {
    var editingPlanId by remember { mutableStateOf<Int?>(null) }
    var subject by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    val totalTimeInSeconds = sessionViewModel.totalStudyTime
    val hours = totalTimeInSeconds / 3600
    val minutes = (totalTimeInSeconds % 3600) / 60
    val seconds = totalTimeInSeconds % 60

    val dateFormatter = remember {
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Progress Tracker",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Total Study Time: $hours hours, $minutes minutes, $seconds seconds",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Add or update study plan input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration (minutes)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val minutes = duration.toIntOrNull() ?: 0
                        if (editingPlanId != null) {
                            studyViewModel.updatePlan(
                                editingPlanId!!,
                                subject.trim(),
                                minutes
                            )
                        } else {
                            studyViewModel.addPlan(subject.trim(), minutes)
                        }
                        subject = ""
                        duration = ""
                        editingPlanId = null
                    }
                )
            )
        }

        Button(
            onClick = {
                val minutes = duration.toIntOrNull() ?: 0
                if (editingPlanId != null) {
                    studyViewModel.updatePlan(
                        editingPlanId!!,
                        subject.trim(),
                        minutes
                    )
                } else {
                    studyViewModel.addPlan(subject.trim(), minutes)
                }
                subject = ""
                duration = ""
                editingPlanId = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (editingPlanId != null) "Update Plan" else "Add Plan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Study Plans",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn {
            items(studyViewModel.plans) { plan ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${plan.subject} - ${plan.durationMinutes} mins",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Row {
                        TextButton(onClick = {
                            editingPlanId = plan.id
                            subject = plan.subject
                            duration = plan.durationMinutes.toString()
                        }) {
                            Text("Edit")
                        }
                        TextButton(onClick = { studyViewModel.removePlan(plan.id) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Session History",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn {
            items(sessionViewModel.history) { session ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Subject: ${session.subject}")
                        Text(text = "Duration: ${session.duration} sec")
                        Text(text = "Date: ${dateFormatter.format(session.date)}")
                    }
                }
            }
        }
    }
}