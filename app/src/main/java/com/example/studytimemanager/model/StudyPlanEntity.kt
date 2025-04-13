package com.example.studytimemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_plan")
data class StudyPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val durationMinutes: Int
)