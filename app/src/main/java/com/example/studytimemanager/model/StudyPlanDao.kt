package com.example.studytimemanager.model

import androidx.room.*

@Dao
interface StudyPlanDao {
    @Query("SELECT * FROM study_plan")
    suspend fun getAll(): List<StudyPlanEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: StudyPlanEntity)

    @Update
    suspend fun update(plan: StudyPlanEntity)

    @Delete
    suspend fun delete(plan: StudyPlanEntity)
}