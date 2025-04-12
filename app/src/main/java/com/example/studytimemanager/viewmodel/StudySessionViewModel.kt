package com.example.studytimemanager.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class StudySessionViewModel(application: Application) : AndroidViewModel(application) {

    var isStudying by mutableStateOf(false)
    var currentSessionTime by mutableStateOf(0)

    fun startSession() {
        isStudying = true
        viewModelScope.launch {
            // Add logic for tracking session time
        }
    }

    fun stopSession() {
        isStudying = false
        // Optionally save the session to the database
    }
}