package com.example.studytimemanager.model

import androidx.room.*

@Dao
interface StudySessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: StudySession)

    @Update
    suspend fun updateSession(session: StudySession)

    @Query("DELETE FROM study_sessions WHERE id = :id")
    suspend fun removeSession(id: String)

    @Query("SELECT * FROM study_sessions ORDER BY date DESC")
    suspend fun getAllSessions(): List<StudySession>
}