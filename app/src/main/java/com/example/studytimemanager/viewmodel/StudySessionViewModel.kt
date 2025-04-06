package com.example.studytimemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class StudySessionViewModel : ViewModel() {

    var totalStudyTime by mutableStateOf(0)
        private set

    var currentSessionTime by mutableStateOf(0)
        private set

    var isStudying by mutableStateOf(false)
        private set

    private var tickJob: Job? = null

    fun startSession() {
        if (!isStudying) {
            isStudying = true
            currentSessionTime = 0
            tickJob = viewModelScope.launch {
                while (isStudying) {
                    delay(1000L)
                    currentSessionTime++
                }
            }
        }
    }

    fun stopSession() {
        if (isStudying) {
            isStudying = false
            totalStudyTime += currentSessionTime
            tickJob?.cancel()
        }
    }
}