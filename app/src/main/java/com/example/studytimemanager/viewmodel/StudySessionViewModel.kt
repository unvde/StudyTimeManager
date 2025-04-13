package com.example.studytimemanager.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.studytimemanager.model.StudySession
import com.example.studytimemanager.model.StudySessionDao
import kotlinx.coroutines.launch
import java.util.*
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class StudySessionViewModel(
    private val studySessionDao: StudySessionDao
) : ViewModel() {

    var totalStudyTime by mutableStateOf(0)
        private set

    var currentSessionTime by mutableStateOf(0)
        private set

    var isStudying by mutableStateOf(false)
        private set

    private var tickJob: kotlinx.coroutines.Job? = null

    var history by mutableStateOf<List<StudySession>>(emptyList())
        private set

    init {
        loadHistory()
    }

    fun startSession() {
        if (!isStudying) {
            isStudying = true
            currentSessionTime = 0
            tickJob = viewModelScope.launch {
                while (isStudying) {
                    kotlinx.coroutines.delay(1000L)
                    currentSessionTime++
                }
            }
        }
    }

    fun stopSession(subject: String = "General") {
        if (isStudying) {
            isStudying = false
            totalStudyTime += currentSessionTime
            tickJob?.cancel()

            val session = StudySession(
                id = UUID.randomUUID().toString(),
                subject = subject,
                duration = currentSessionTime,
                date = Date()
            )

            viewModelScope.launch {
                studySessionDao.insert(session)
                loadHistory()
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            history = studySessionDao.getAllSessions()
            totalStudyTime = history.sumOf { it.duration }
        }
    }
    // Expose loadHistory for testing purposes
    fun loadHistoryForTest() {
        loadHistory()
    }
}