package com.example.studytimemanager.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studytimemanager.model.StudySession
import com.example.studytimemanager.model.StudySessionDao
import com.example.studytimemanager.model.StudySessionDatabase
import com.example.studytimemanager.model.StudyPlan
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlinx.coroutines.launch

class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    // Initialize studySessionDao
    private val studySessionDao: StudySessionDao = StudySessionDatabase.getDatabase(application).studySessionDao()

    // Internal mutable state for plans
    private val _plans = mutableStateListOf<StudyPlan>()
    val plans: List<StudyPlan> get() = _plans

    // Update plan by ID
    fun updatePlan(id: String, subject: String, durationMinutes: Int) {
        val planIndex = _plans.indexOfFirst { it.id == id.toInt() }  // Convert String to Int
        if (planIndex != -1 && subject.isNotBlank() && durationMinutes > 0) {
            val updatedPlan = _plans[planIndex].copy(subject = subject, durationMinutes = durationMinutes)  // Keep duration in minutes
            _plans[planIndex] = updatedPlan  // Update the plan in the local list

            // Convert StudyPlan to StudySession before passing to updateSession
            val currentDateTime = LocalDateTime.now()
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formattedDate = currentDateTime.format(dateFormatter)

            // Convert the date String to Date
            val date = java.sql.Date.valueOf(formattedDate)  // Convert String to Date

            // Convert durationMinutes to seconds before passing to StudySession
            val studySession = StudySession(
                id = updatedPlan.id.toString(),  // Convert id to String
                subject = updatedPlan.subject,
                duration = updatedPlan.durationMinutes * 60,  // Convert duration from minutes to seconds
                date = date  // Pass the current date as the date
            )

            viewModelScope.launch {
                studySessionDao.updateSession(studySession)  // Correct method name: updateSession
            }
        }
    }

    // Remove plan by ID
    fun removePlan(id: String) {
        val planToRemove = _plans.find { it.id == id.toInt() }  // Convert String to Int
        planToRemove?.let {
            _plans.remove(planToRemove)
            viewModelScope.launch {
                studySessionDao.removeSession(id)  // Use id as String
            }
        }
    }

    // Add a new plan
    fun addPlan(subject: String, durationMinutes: Int) {
        if (subject.isNotBlank() && durationMinutes > 0) {
            val newPlan = StudyPlan(
                id = (_plans.size + 1), // Ensure the ID is a Int
                subject = subject,
                durationMinutes = durationMinutes * 60 // Duration in seconds
            )
            _plans.add(newPlan)  // Add to the local list
            viewModelScope.launch {
                // Convert the StudyPlan to StudySession before inserting into DB
                val newSession = StudySession(
                    id = newPlan.id.toString(),
                    subject = newPlan.subject,
                    duration = newPlan.durationMinutes,
                    date = Date()  // Current time
                )
                studySessionDao.insert(newSession)  // Insert into the database
            }
        }
    }
}