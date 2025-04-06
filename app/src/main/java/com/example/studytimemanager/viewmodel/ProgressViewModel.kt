package com.example.studytimemanager.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.studytimemanager.model.StudyPlan

class ProgressViewModel : ViewModel() {
    private val _plans = mutableStateListOf<StudyPlan>()
    val plans: List<StudyPlan> get() = _plans

    private var planIdCounter = 0

    fun addPlan(subject: String, durationMinutes: Int) {
        if (subject.isNotBlank() && durationMinutes > 0) {
            _plans.add(
                StudyPlan(
                    id = planIdCounter++,
                    subject = subject,
                    durationMinutes = durationMinutes
                )
            )
        }
    }

    fun removePlan(id: Int) {
        _plans.removeAll { it.id == id }
    }

    fun updatePlan(id: Int, subject: String, durationMinutes: Int) {
        val index = _plans.indexOfFirst { it.id == id }
        if (index != -1 && subject.isNotBlank() && durationMinutes > 0) {
            _plans[index] = _plans[index].copy(subject = subject, durationMinutes = durationMinutes)
        }
    }

    fun getPlanById(id: Int): StudyPlan? {
        return _plans.find { it.id == id }
    }
}