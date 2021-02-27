package com.app.willyweathertest.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.willyweathertest.network.models.Jobs

@Database(entities = [Jobs::class], version = 1)
abstract class JobsDatabase: RoomDatabase() {
    abstract fun jobsDao(): JobsDao

    companion object {
        private var instance: JobsDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): JobsDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, JobsDatabase::class.java,
                        "jobs_database")
                        .build()

            return instance!!

        }
    }
}