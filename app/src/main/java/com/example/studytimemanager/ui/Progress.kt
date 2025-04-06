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

@Composable
fun ProgressScreen(
    studyViewModel: ProgressViewModel,
    sessionViewModel: StudySessionViewModel
) {
    var editingPlanId by remember { mutableStateOf<Int?>(null) }
    var subject by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    // Display total study time in minutes
    // Calculate total time in hours, minutes, and seconds
    val totalTimeInSeconds = sessionViewModel.totalStudyTime
    val hours = totalTimeInSeconds / 3600
    val minutes = (totalTimeInSeconds % 3600) / 60
    val seconds = totalTimeInSeconds % 60

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

        // Display total study time
        Text("Total Study Time: $hours hours, $minutes minutes, $seconds seconds", modifier = Modifier.padding(bottom = 16.dp))

        // Add or Update Study Plan
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

        // Confirm Button to Add/Update Plan
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

        // List of Study Plans
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
    }
}