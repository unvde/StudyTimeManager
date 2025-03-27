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
import com.example.studytimemanager.model.StudyPlan
import com.example.studytimemanager.viewmodel.StudyViewModel

@Composable
fun StudyScreen(studyViewModel: StudyViewModel) {
    var subject by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Study Planner",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration (minutes)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                studyViewModel.addPlan(subject, duration.toIntOrNull() ?: 0)
                subject = ""
                duration = ""
            })
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                studyViewModel.addPlan(subject, duration.toIntOrNull() ?: 0)
                subject = ""
                duration = ""
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Study Plan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(studyViewModel.studyPlans) { plan ->
                StudyPlanItem(plan)
            }
        }
    }
}

@Composable
fun StudyPlanItem(plan: StudyPlan) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Subject: ${plan.subject}")
            Text(text = "Duration: ${plan.durationMinutes} minutes")
        }
    }
}
