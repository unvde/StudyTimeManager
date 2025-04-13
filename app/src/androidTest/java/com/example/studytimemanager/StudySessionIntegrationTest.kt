package com.example.studytimemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studytimemanager.model.StudySessionDao
import com.example.studytimemanager.model.StudySessionDatabase
import com.example.studytimemanager.viewmodel.StudySessionViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class StudySessionIntegrationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: StudySessionDatabase
    private lateinit var dao: StudySessionDao
    private lateinit var viewModel: StudySessionViewModel

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StudySessionDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.studySessionDao()
        viewModel = StudySessionViewModel(dao)
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInsertAndLoadStudySession() = runTest {
        viewModel.startSession()
        viewModel.stopSession("IntegrationTest")

        // 等待插入与更新历史完成
        Thread.sleep(1000)

        viewModel.loadHistoryForTest()

        Assert.assertTrue(viewModel.history.isNotEmpty())
        Assert.assertEquals("IntegrationTest", viewModel.history.first().subject)
    }
}