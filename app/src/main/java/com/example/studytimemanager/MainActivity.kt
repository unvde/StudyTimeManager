package com.example.studytimemanager

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studytimemanager.model.StudySessionDatabase
import com.example.studytimemanager.ui.nav.AppNavGraph
import com.example.studytimemanager.ui.nav.NavRoutes
import com.example.studytimemanager.ui.theme.StudyTimeManagerTheme
import com.example.studytimemanager.ui.theme.PrimaryDark
import com.example.studytimemanager.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTimeManagerTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val dao = StudySessionDatabase.getDatabase(context).studySessionDao()
                val sessionViewModel: StudySessionViewModel = viewModel(
                    factory = StudySessionViewModelFactory(dao)
                )
                val taskViewModel = remember { TaskViewModel() }
                val studyViewModel: ProgressViewModel = viewModel(
                    factory = ProgressViewModelFactory(context.applicationContext as Application)
                )
                val wordsViewModel: WordsViewModel = viewModel()

                StudyTimeManagerApp(
                    navController = navController,
                    sessionViewModel = sessionViewModel,
                    taskViewModel = taskViewModel,
                    progressViewModel = studyViewModel,
                    wordsViewModel = wordsViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyTimeManagerApp(
    navController: NavHostController,
    sessionViewModel: StudySessionViewModel,
    taskViewModel: TaskViewModel,
    progressViewModel: ProgressViewModel,
    wordsViewModel: WordsViewModel,
) {
    val currentRoute = remember { mutableStateOf(NavRoutes.Study.route) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Study Time Manager") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                NavigationBarItem(
                    selected = currentRoute.value == NavRoutes.Study.route,
                    onClick = {
                        currentRoute.value = NavRoutes.Study.route
                        navController.navigate(NavRoutes.Study.route) { popUpTo(0) }
                    },
                    icon = { Icon(Icons.Filled.Timer, contentDescription = "Study") },
                    label = { Text("Study") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        indicatorColor = PrimaryDark
                    )
                )
                NavigationBarItem(
                    selected = currentRoute.value == NavRoutes.Dashboard.route,
                    onClick = {
                        currentRoute.value = NavRoutes.Dashboard.route
                        navController.navigate(NavRoutes.Dashboard.route) { popUpTo(0) }
                    },
                    icon = { Icon(Icons.Filled.List, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        indicatorColor = PrimaryDark
                    )
                )
                NavigationBarItem(
                    selected = currentRoute.value == NavRoutes.Progress.route,
                    onClick = {
                        currentRoute.value = NavRoutes.Progress.route
                        navController.navigate(NavRoutes.Progress.route) { popUpTo(0) }
                    },
                    icon = { Icon(Icons.Filled.ShowChart, contentDescription = "Progress") },
                    label = { Text("Progress") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        indicatorColor = PrimaryDark
                    )
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AppNavGraph(
                navController = navController,
                sessionViewModel = sessionViewModel,
                taskViewModel = taskViewModel,
                progressViewModel = progressViewModel,
                wordsViewModel = wordsViewModel
            )
        }
    }
}