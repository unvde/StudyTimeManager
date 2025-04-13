package com.example.studytimemanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studytimemanager.viewmodel.WordsViewModel
import com.example.studytimemanager.viewmodel.StudySessionViewModel

@Composable
fun StudyTimerScreen(
    sessionViewModel: StudySessionViewModel
) {
    val isStudying = sessionViewModel.isStudying
    val currentTime = sessionViewModel.currentSessionTime

    val wordsViewModel: WordsViewModel = viewModel()
    val word by wordsViewModel.word.collectAsState()
    val description by wordsViewModel.description.collectAsState()
    val isLoading by wordsViewModel.isLoading.collectAsState()

    val initialized = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!initialized.value) {
            initialized.value = true
            wordsViewModel.fetchWordData()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Study Time Tracker",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        val minutes = currentTime / 60
        val seconds = currentTime % 60
        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                if (isStudying) sessionViewModel.stopSession()
                else sessionViewModel.startSession()
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isStudying) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(if (isStudying) "Stop Study" else "Start Study")
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Random English word",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Word: $word", style = MaterialTheme.typography.titleMedium)
                    Text(description, style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { wordsViewModel.fetchWordData() },
                        enabled = !isLoading
                    ) {
                        Text("Refresh Word")
                    }
                }
            }
        }
    }
}