package com.app.willyweathertest.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.willyweathertest.network.models.Jobs


@Dao
interface JobsDao {
    @Insert
    suspend fun insertAllJobs(jobs:List<Jobs>)

    @Query("SELECT * FROM jobs")
    suspend fun getAllJobs(): List<Jobs>

    @Query("DELETE FROM jobs")
    suspend fun deleteAllJobs()
}