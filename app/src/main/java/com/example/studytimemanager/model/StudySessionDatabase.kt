package com.example.studytimemanager.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudySession::class], version = 1, exportSchema = false)
abstract class StudySessionDatabase : RoomDatabase() {
    abstract fun studySessionDao(): StudySessionDao

    companion object {
        @Volatile
        private var INSTANCE: StudySessionDatabase? = null

        fun getDatabase(context: Context): StudySessionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudySessionDatabase::class.java,
                    "study_session_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}