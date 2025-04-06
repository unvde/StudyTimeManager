package com.example.studytimemanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studytimemanager.viewmodel.StudySessionViewModel

@Composable
fun StudyTimerScreen(
    sessionViewModel: StudySessionViewModel
) {
    val isStudying = sessionViewModel.isStudying
    val currentTime = sessionViewModel.currentSessionTime

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
                if (isStudying) {
                    sessionViewModel.stopSession()
                } else {
                    sessionViewModel.startSession()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isStudying) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(if (isStudying) "Stop Study" else "Start Study")
        }
    }
}