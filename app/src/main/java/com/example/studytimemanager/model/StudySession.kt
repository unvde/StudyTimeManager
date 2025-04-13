package com.example.studytimemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "study_sessions")
data class StudySession(
    @PrimaryKey val id: String,
    val subject: String,
    val duration: Int,
    val date: Date
)