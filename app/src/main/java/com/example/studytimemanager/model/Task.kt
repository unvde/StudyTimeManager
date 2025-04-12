package com.example.studytimemanager.model

data class Task(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)