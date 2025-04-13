package com.example.studytimemanager

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunchesWithStudyPage() {
        composeTestRule.onNodeWithText("Study Time Tracker", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun bottomNavNavigatesToDashboard() {
        composeTestRule
            .onNodeWithContentDescription("Dashboard", useUnmergedTree = true)
            .performClick()

        composeTestRule.waitUntilExists(
            hasText("Dashboard"),
            timeoutMillis = 5000
        )

        composeTestRule
            .onAllNodesWithText("Dashboard", useUnmergedTree = true)[0]
            .assertExists()
    }

    @Test
    fun bottomNavNavigatesToProgress() {
        composeTestRule
            .onNodeWithContentDescription("Progress", useUnmergedTree = true)
            .performClick()

        composeTestRule.waitUntilExists(
            hasText("Progress Tracker"),
            timeoutMillis = 5000
        )

        composeTestRule
            .onNodeWithText("Progress Tracker", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun startStopButtonChangesText() {
        val startButton = composeTestRule
            .onNodeWithText("Start Study", useUnmergedTree = true)
        startButton.assertExists()
        startButton.performClick()

        // 等待状态变更后的 Stop Study 出现
        composeTestRule.waitUntilExists(
            hasText("Stop Study"),
            timeoutMillis = 5000
        )

        composeTestRule
            .onNodeWithText("Stop Study", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun wordCardDisplays() {
        composeTestRule.waitUntilExists(
            hasText("Random English word"),
            timeoutMillis = 5000
        )

        composeTestRule
            .onNodeWithText("Random English word", useUnmergedTree = true)
            .assertExists()
    }
}

// 通用工具函数：等待节点出现
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.waitUntilExists(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = 3000
) {
    this.waitUntil(timeoutMillis) {
        this.onAllNodes(matcher, useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
    }
}