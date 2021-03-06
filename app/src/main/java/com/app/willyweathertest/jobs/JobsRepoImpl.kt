package com.app.willyweathertest.jobs

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.willyweathertest.application.MyApplication
import com.app.willyweathertest.databases.DatabaseHelperImpl
import com.app.willyweathertest.databases.JobsDatabase
import com.app.willyweathertest.network.ApiClient
import com.app.willyweathertest.network.ApiInterface
import com.app.willyweathertest.network.models.Jobs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class JobsRepoImpl:JobsRepo {

    @SuppressLint("CheckResult")
    override fun getJobs(): LiveData<Pair<Boolean, ArrayList<Jobs>>> {
        val liveData= MutableLiveData<Pair<Boolean, ArrayList<Jobs>>>()
        val apiClient= ApiClient.getClient().create(ApiInterface::class.java)
        apiClient!!.fetchJobs().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(
            object : DisposableSingleObserver<ArrayList<Jobs>>() {
                @SuppressLint("CheckResult")
                override fun onSuccess(list: ArrayList<Jobs>) {
                    if (list!=null && list.size>0){
                        liveData.postValue(Pair(true,list))

                    }else{
                        liveData.postValue(Pair(false,ArrayList()))
                    }
                }

                override fun onError(e: Throwable) {
                    liveData.postValue(Pair(false,ArrayList()))

                }

            })
        return liveData
    }

    override suspend fun getJobsFromDatabase(): ArrayList<Jobs> {
        val databaseHelperImpl=DatabaseHelperImpl(JobsDatabase.getInstance(MyApplication.instance?.applicationContext!!))
        return databaseHelperImpl.getJobs()
    }

}