package com.example.studytimemanager.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studytimemanager.ui.*
import com.example.studytimemanager.viewmodel.*

@Composable
fun AppNavGraph(
    navController: NavHostController,
    sessionViewModel: StudySessionViewModel,
    taskViewModel: TaskViewModel,
    progressViewModel: ProgressViewModel,
    wordsViewModel: WordsViewModel
) {
    NavHost(navController = navController, startDestination = NavRoutes.Study.route) {
        composable(NavRoutes.Study.route) {
            StudyTimerScreen(
                sessionViewModel = sessionViewModel,
                wordsViewModel = wordsViewModel
            )
        }
        composable(NavRoutes.Dashboard.route) {
            DashboardScreen(taskViewModel)
        }
        composable(NavRoutes.Progress.route) {
            ProgressScreen(
                studyViewModel = progressViewModel,
                sessionViewModel = sessionViewModel
            )
        }
    }
}