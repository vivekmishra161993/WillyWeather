package com.app.willyweathertest.databases

import com.app.willyweathertest.network.models.Jobs

class DatabaseHelperImpl(private val database: JobsDatabase):DatabaseHelper {
    override suspend fun getJobs(): ArrayList<Jobs> = database.jobsDao().getAllJobs() as ArrayList<Jobs>

    override suspend fun insertAllJobs(jobs: ArrayList<Jobs>)=database.jobsDao().insertAllJobs(jobs)
    override suspend fun deleteAllJobs() =database.jobsDao().deleteAllJobs()
}