package com.example.studytimemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studytimemanager.model.StudySessionDao

class StudySessionViewModelFactory(
    private val dao: StudySessionDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudySessionViewModel(dao) as T
    }
}