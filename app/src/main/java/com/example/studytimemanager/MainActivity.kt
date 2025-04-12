package com.example.studytimemanager

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studytimemanager.ui.*
import com.example.studytimemanager.ui.ProgressScreen
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

    // Pass application context directly
    val context = LocalContext.current.applicationContext as Application
    val studyViewModel = remember { ProgressViewModel(context) }
    val sessionViewModel = remember { StudySessionViewModel(context) }
    val taskViewModel: TaskViewModel = viewModel()  // Add this line to initialize taskViewModel

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Study Time Manager") })
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
                "Dashboard" -> DashboardScreen(taskViewModel = taskViewModel)  // Pass the taskViewModel here
                "Progress" -> ProgressScreen(studyViewModel = studyViewModel, sessionViewModel = sessionViewModel)
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