package com.example.studytimemanager.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.studytimemanager.model.StudyPlan

class StudyViewModel : ViewModel() {
    // List to hold study plans
    private val _studyPlans = mutableStateListOf<StudyPlan>()
    val studyPlans: List<StudyPlan> get() = _studyPlans

    private var planIdCounter = 0

    // Function to add a new plan
    fun addPlan(subject: String, durationMinutes: Int) {
        if (subject.isNotBlank() && durationMinutes > 0) {
            _studyPlans.add(
                StudyPlan(
                    id = planIdCounter++,
                    subject = subject,
                    durationMinutes = durationMinutes
                )
            )
        }
    }
}
