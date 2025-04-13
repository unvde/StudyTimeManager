package com.example.studytimemanager.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudySessionViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudySessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudySessionViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}