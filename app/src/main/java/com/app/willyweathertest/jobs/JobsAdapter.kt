package com.app.willyweathertest.jobs

import com.app.willyweathertest.R
import com.app.willyweathertest.databinding.ItemJobsBinding
import com.app.willyweathertest.network.models.Jobs
import com.app.willyweathertest.utils.BaseAdapter
import com.app.willyweathertest.utils.BaseViewHolder

class JobsAdapter():BaseAdapter() {
    private val jobList=ArrayList<Jobs>()
    override val size: Int
        get() = jobList.size
    override val layout: Int
        get() = R.layout.item_jobs

    override fun onBindHolder(holder: BaseViewHolder, position: Int) {
        (holder.binding as ItemJobsBinding).apply {
            this.model=jobList[position]
        }
    }
    fun setItems(list: ArrayList<Jobs>){
        jobList.clear()
        jobList.addAll(list);
    }
}