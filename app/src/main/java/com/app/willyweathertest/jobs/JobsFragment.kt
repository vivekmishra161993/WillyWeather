package com.app.willyweathertest.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.willyweathertest.R
import com.app.willyweathertest.databinding.FragmentJobsBinding
import com.app.willyweathertest.interfaces.OnItemClick
import com.app.willyweathertest.jobsDetail.JobsDetailFragment
import com.app.willyweathertest.network.models.Jobs
import com.app.willyweathertest.utils.DialogUtility
import org.koin.androidx.viewmodel.ext.android.getViewModel


class JobsFragment: Fragment(),OnItemClick {

    private lateinit var jobsViewModel: JobsViewModel
    private lateinit var binding: FragmentJobsBinding
    private lateinit var adapter: JobsAdapter
    private lateinit var dialogUtility: DialogUtility
    private lateinit var jobsList:ArrayList<Jobs>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentJobsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClasses()
        getJobs()

    }
    private fun initClasses() {
        jobsViewModel = getViewModel()
        dialogUtility= DialogUtility.getInstance()
        binding.viewModel = jobsViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        binding.rvJobs.layoutManager = LinearLayoutManager(context)
        adapter = jobsViewModel.getJobsAdapter(this)
        binding.rvJobs.adapter = adapter
        jobsList= ArrayList()
    }
    private fun getJobs() {
        dialogUtility.showProgressDialog(requireContext())
            jobsViewModel.getJobs().observe(this, Observer {

                if (it.first) {
                    if (it.second != null && it.second.size > 0) {
                        jobsList.clear()
                        jobsList.addAll(it.second)
                        jobsViewModel.setJobsList(jobsList)
                        adapter.notifyDataSetChanged()
                        dialogUtility.hideProgressDialog()
                    } else {
                        dialogUtility.hideProgressDialog()
                        Toast.makeText(requireContext(), getString(R.string.no_data), Toast.LENGTH_LONG).show()
                    }

                } else {
                    dialogUtility.hideProgressDialog()
                    Toast.makeText(requireContext(), getString(R.string.error_msg), Toast.LENGTH_LONG).show()

                }
            })
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("jobDetail", jobsList[position])
        val fragment=JobsDetailFragment()
        fragment.arguments=bundle
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack("JobsFragment")
        transaction.commit()
    }


}