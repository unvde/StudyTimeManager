package com.example.studytimemanager.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudyPlanEntity::class], version = 1, exportSchema = false)
abstract class StudyPlanDatabase : RoomDatabase() {
    abstract fun studyPlanDao(): StudyPlanDao

    companion object {
        @Volatile
        private var INSTANCE: StudyPlanDatabase? = null

        fun getDatabase(context: Context): StudyPlanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyPlanDatabase::class.java,
                    "study_plan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}