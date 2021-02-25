package com.app.willyweathertest.jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.willyweathertest.network.models.Jobs

class JobsViewModel(private val repo: JobsRepo) : ViewModel() {
    private var jobsAdapter = JobsAdapter()

    fun getJobs(): LiveData<Pair<Boolean, ArrayList<Jobs>>> {
        return repo.getJobs();
    }

    fun getJobsAdapter(): JobsAdapter {
        return jobsAdapter

    }

    fun setJobsList(orderList: ArrayList<Jobs>){
        jobsAdapter.setItems(orderList)

    }

}