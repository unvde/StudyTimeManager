package com.example.studytimemanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studytimemanager.viewmodel.StudyViewModel
import com.example.studytimemanager.model.StudyPlan

@Composable
fun ProgressScreen(studyViewModel: StudyViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Study Progress",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (studyViewModel.studyPlans.isEmpty()) {
            Text("No study plans recorded yet.")
        } else {
            LazyColumn {
                items(studyViewModel.studyPlans) { plan ->
                    ProgressItem(plan)
                }
            }
        }
    }
}

@Composable
fun ProgressItem(plan: StudyPlan) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Subject: ${plan.subject}")
            Text("Duration: ${plan.durationMinutes} minutes")
        }
    }
}
