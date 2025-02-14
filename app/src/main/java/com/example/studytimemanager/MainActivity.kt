package com.example.studytimemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studytimemanager.ui.theme.StudyTimeManagerTheme

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
    var selectedScreen by remember { mutableStateOf("Dashboard") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Study Time Manager") }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    selected = (selectedScreen == "Dashboard"),
                    onClick = { selectedScreen = "Dashboard" },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Dashboard"
                        )
                    },
                    label = { Text("Dashboard") }
                )
                BottomNavigationItem(
                    selected = (selectedScreen == "Tasks"),
                    onClick = { selectedScreen = "Tasks" },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Tasks"
                        )
                    },
                    label = { Text("Tasks") }
                )
                BottomNavigationItem(
                    selected = (selectedScreen == "Study"),
                    onClick = { selectedScreen = "Study" },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Study"
                        )
                    },
                    label = { Text("Study") }
                )
                BottomNavigationItem(
                    selected = (selectedScreen == "Progress"),
                    onClick = { selectedScreen = "Progress" },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.ShowChart,
                            contentDescription = "Progress"
                        )
                    },
                    label = { Text("Progress") }
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                "Dashboard" -> DashboardScreen()
                "Tasks" -> TaskManagerScreen()
                "Study" -> StudyPlannerScreen()
                "Progress" -> ProgressAnalyticsScreen()
            }
        }
    }
}

@Composable
fun DashboardScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Dashboard", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text("Upcoming Tasks:")
        LazyColumn {
            items(listOf("Math Homework", "CS Project", "History Essay")) { task ->
                Text(task, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun TaskManagerScreen() {
    var taskText by remember { mutableStateOf("") }
    val tasks = remember { mutableStateListOf("Math Homework", "CS Project", "History Essay") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Task Manager", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = taskText,
            onValueChange = { taskText = it },
            label = { Text("New Task") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (taskText.isNotBlank()) {
                    tasks.add(taskText)
                    taskText = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add Task")
        }

        LazyColumn {
            items(tasks) { task ->
                Text(task, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun StudyPlannerScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Study Planner", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text("Schedule Study Sessions (Placeholder)")
    }
}

@Composable
fun ProgressAnalyticsScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Progress Analytics", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text("Track Study Progress (Placeholder)")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudyTimeManagerTheme {
        StudyTimeManagerApp()
    }
}
