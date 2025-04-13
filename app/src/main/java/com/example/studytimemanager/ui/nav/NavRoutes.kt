package com.example.studytimemanager.ui.nav

sealed class NavRoutes(val route: String) {
    object Study : NavRoutes("study")
    object Dashboard : NavRoutes("dashboard")
    object Progress : NavRoutes("progress")
}