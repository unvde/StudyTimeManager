package com.example.studytimemanager.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studytimemanager.model.StudyPlan
import com.example.studytimemanager.model.StudyPlanDao
import com.example.studytimemanager.model.StudyPlanDatabase
import com.example.studytimemanager.model.StudyPlanEntity
import kotlinx.coroutines.launch

class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val studyPlanDao: StudyPlanDao =
        StudyPlanDatabase.getDatabase(application).studyPlanDao()

    private val _plans = mutableStateListOf<StudyPlan>()
    val plans: List<StudyPlan> get() = _plans

    init {
        loadPlansFromDb()
    }

    private fun loadPlansFromDb() {
        viewModelScope.launch {
            val entities = studyPlanDao.getAll()
            _plans.clear()
            _plans.addAll(entities.map {
                StudyPlan(id = it.id, subject = it.subject, durationMinutes = it.durationMinutes)
            })
        }
    }

    fun addPlan(subject: String, durationMinutes: Int) {
        if (subject.isNotBlank() && durationMinutes > 0) {
            viewModelScope.launch {
                val entity = StudyPlanEntity(subject = subject, durationMinutes = durationMinutes)
                studyPlanDao.insert(entity)
                loadPlansFromDb()
            }
        }
    }

    fun updatePlan(id: Int, subject: String, durationMinutes: Int) {
        val planIndex = _plans.indexOfFirst { it.id == id }
        if (planIndex != -1 && subject.isNotBlank() && durationMinutes > 0) {
            viewModelScope.launch {
                val entity = StudyPlanEntity(
                    id = id,
                    subject = subject,
                    durationMinutes = durationMinutes
                )
                studyPlanDao.update(entity)
                loadPlansFromDb()
            }
        }
    }

    fun removePlan(id: Int) {
        val plan = _plans.find { it.id == id }
        plan?.let {
            viewModelScope.launch {
                val entity = StudyPlanEntity(
                    id = it.id,
                    subject = it.subject,
                    durationMinutes = it.durationMinutes
                )
                studyPlanDao.delete(entity)
                loadPlansFromDb()
            }
        }
    }
}