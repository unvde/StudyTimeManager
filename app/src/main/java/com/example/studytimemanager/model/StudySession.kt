package com.example.studytimemanager.model

import java.util.Date

data class StudySession(
    val id: String,
    val subject: String,
    val duration: Int,
    val date: Date
)