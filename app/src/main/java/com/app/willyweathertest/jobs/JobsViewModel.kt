package com.app.willyweathertest.jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.willyweathertest.network.models.Jobs
import kotlinx.coroutines.launch

class JobsViewModel(private val repo: JobsRepo) : ViewModel() {

    fun getJobs(): LiveData<Pair<Boolean, ArrayList<Jobs>>> {
        return repo.getJobs();
    }
    fun getJobsFromDatabase():ArrayList<Jobs>{
        val jobsList=ArrayList<Jobs>()
        viewModelScope.launch {
           jobsList.addAll(repo.getJobsFromDatabase())
        }
        return jobsList
    }
}