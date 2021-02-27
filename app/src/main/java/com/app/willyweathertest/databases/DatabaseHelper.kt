package com.app.willyweathertest.databases

import com.app.willyweathertest.network.models.Jobs

interface DatabaseHelper {
    suspend fun getJobs(): ArrayList<Jobs>

    suspend fun insertAllJobs(jobs: ArrayList<Jobs>)

    suspend fun deleteAllJobs()
}