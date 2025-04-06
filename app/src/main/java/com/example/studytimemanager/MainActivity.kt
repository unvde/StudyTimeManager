package com.example.studytimemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.studytimemanager.ui.*
import com.example.studytimemanager.ui.theme.StudyTimeManagerTheme
import com.example.studytimemanager.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTimeManagerTheme {
                StudyTimeManagerApp()
            }
        }
    }
}

@Composable
fun StudyTimeManagerApp() {
    var selectedScreen by remember { mutableStateOf("Study") }

    val taskViewModel = remember { TaskViewModel() }
    val studyViewModel = remember { ProgressViewModel() }
    val sessionViewModel = remember { StudySessionViewModel() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Study Time Manager") })
        },
        bottomBar = {
            BottomNavigation(modifier = Modifier.fillMaxWidth().navigationBarsPadding()) {
                BottomNavigationItem(
                    selected = (selectedScreen == "Study"),
                    onClick = { selectedScreen = "Study" },
                    icon = { Icon(Icons.Filled.Timer, contentDescription = "Study") },
                    label = { Text("Study") }
                )
                BottomNavigationItem(
                    selected = (selectedScreen == "Dashboard"),
                    onClick = { selectedScreen = "Dashboard" },
                    icon = { Icon(Icons.Filled.List, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") }
                )
                BottomNavigationItem(
                    selected = (selectedScreen == "Progress"),
                    onClick = { selectedScreen = "Progress" },
                    icon = { Icon(Icons.Filled.ShowChart, contentDescription = "Progress") },
                    label = { Text("Progress") }
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                "Study" -> StudyTimerScreen(sessionViewModel = sessionViewModel)
                "Dashboard" -> DashboardScreen(taskViewModel)
                "Progress" -> ProgressScreen(studyViewModel, sessionViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudyTimeManagerTheme {
        StudyTimeManagerApp()
    }
}