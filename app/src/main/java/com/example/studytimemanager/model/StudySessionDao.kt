package com.example.studytimemanager.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySessionDao {
    @Insert
    suspend fun insert(studySession: StudySession)

    @Query("SELECT * FROM study_sessions")
    suspend fun getAllSessions(): List<StudySession>

    @Query("DELETE FROM study_sessions WHERE id = :id")
    suspend fun removeSession(id: String)

    @Update
    suspend fun updateSession(studySession: StudySession)
}