package com.app.willyweathertest.jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.willyweathertest.databases.DatabaseHelperImpl
import com.app.willyweathertest.interfaces.OnItemClick
import com.app.willyweathertest.network.models.Jobs
import kotlinx.coroutines.launch

class JobsViewModel(private val repo: JobsRepo) : ViewModel() {
    private lateinit var jobsAdapter:JobsAdapter

    fun getJobs(): LiveData<Pair<Boolean, ArrayList<Jobs>>> {
        return repo.getJobs();
    }

    fun getJobsAdapter(onItemClick: OnItemClick): JobsAdapter {
        jobsAdapter=JobsAdapter(onItemClick)
        return jobsAdapter

    }

    fun setJobsList(orderList: ArrayList<Jobs>){
        jobsAdapter.setItems(orderList)
        jobsAdapter.notifyDataSetChanged()

    }
    fun getJobsFromDatabase(dataBaHelperImpl:DatabaseHelperImpl):ArrayList<Jobs>{
        val jobsList=ArrayList<Jobs>()
        viewModelScope.launch {
           jobsList.addAll(dataBaHelperImpl.getJobs())
        }
        return jobsList

    }


}