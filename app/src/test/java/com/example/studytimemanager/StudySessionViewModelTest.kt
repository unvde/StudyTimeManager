package com.example.studytimemanager

import com.example.studytimemanager.model.StudySession
import com.example.studytimemanager.model.StudySessionDao
import com.example.studytimemanager.viewmodel.StudySessionViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class StudySessionViewModelTest {

    private lateinit var viewModel: StudySessionViewModel
    private lateinit var fakeDao: StudySessionDao
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeDao = mockk(relaxed = true)
        viewModel = StudySessionViewModel(fakeDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testStartSession_setsIsStudyingTrue() = runTest {
        viewModel.startSession()
        assertTrue(viewModel.isStudying)
    }

    @Test
    fun testStopSession_setsIsStudyingFalse_andSaves() = runTest {
        viewModel.startSession()
        advanceTimeBy(2000)

        viewModel.stopSession()

        // Ensure coroutine finishes insert
        advanceUntilIdle()

        assertTrue(!viewModel.isStudying)

        coVerify {
            fakeDao.insert(withArg {
                assertEquals("General", it.subject)
                assertTrue(it.duration >= 2)
            })
        }
    }

    @Test
    fun testSessionTimeIncreases() = runTest {
        viewModel.startSession()
        advanceTimeBy(5000)
        assertTrue(viewModel.currentSessionTime in 4..5)
    }

    @Test
    fun testLoadHistory_setsTotalStudyTime() = runTest {
        val mockHistory = listOf(
            StudySession("1", "A", 100, Date()),
            StudySession("2", "B", 200, Date())
        )
        coEvery { fakeDao.getAllSessions() } returns mockHistory

        viewModel.loadHistoryForTest()
        advanceUntilIdle()

        assertEquals(300, viewModel.totalStudyTime)
        assertEquals(2, viewModel.history.size)
    }
}