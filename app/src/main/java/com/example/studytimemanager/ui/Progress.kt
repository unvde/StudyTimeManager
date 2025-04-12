package com.example.studytimemanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studytimemanager.model.StudyPlan
import com.example.studytimemanager.viewmodel.ProgressViewModel
import com.example.studytimemanager.viewmodel.StudySessionViewModel

@Composable
fun ProgressScreen(
    studyViewModel: ProgressViewModel,
    sessionViewModel: StudySessionViewModel
) {
    // Use viewModel's mutable state directly
    val plans = studyViewModel.plans // Access the viewModel's plans

    val isStudying = sessionViewModel.isStudying
    val currentTime = sessionViewModel.currentSessionTime

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Study Progress",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Display study session information
        Text(
            text = "Current study time: ${currentTime / 60} minutes",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Display each study plan item
        LazyColumn {
            itemsIndexed(plans) { index, plan ->
                StudyPlanItem(
                    plan = plan,
                    onDelete = {
                        studyViewModel.removePlan(plan.id.toString())
                    },
                    onUpdate = {
                        studyViewModel.updatePlan(
                            id = plan.id.toString(),
                            subject = plan.subject,
                            durationMinutes = plan.durationMinutes / 60
                        )
                    }
                )
            }
        }

        // Add new study plan button
        Button(
            onClick = {
                studyViewModel.addPlan(
                    subject = "New Plan",
                    durationMinutes = 30
                )
            },
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text("Add New Plan")
        }
    }
}

@Composable
fun StudyPlanItem(
    plan: StudyPlan,
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = plan.subject,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${plan.durationMinutes / 60} minutes",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onUpdate) {
                    Text("Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}