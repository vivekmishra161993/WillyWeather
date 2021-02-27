package com.app.willyweathertest.jobs

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.willyweathertest.MainActivity
import com.app.willyweathertest.R
import com.app.willyweathertest.databases.DatabaseHelperImpl
import com.app.willyweathertest.databases.JobsDatabase
import com.app.willyweathertest.databinding.FragmentJobsBinding
import com.app.willyweathertest.interfaces.OnItemClick
import com.app.willyweathertest.jobsDetail.JobsDetailFragment
import com.app.willyweathertest.network.models.Jobs
import com.app.willyweathertest.utils.DialogUtility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel


class JobsFragment: Fragment(),OnItemClick {

    private lateinit var jobsViewModel: JobsViewModel
    private lateinit var binding: FragmentJobsBinding
    private lateinit var adapter: JobsAdapter
    private lateinit var dialogUtility: DialogUtility
    private lateinit var jobsList:ArrayList<Jobs>
    private lateinit var dataBaHelperImpl: DatabaseHelperImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJobsBinding.inflate(inflater, container, false)

        initClasses()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isInternetAvailable()) {
            if (jobsList.size == 0) {
                getJobs()
            }
        }else{
            jobsList=jobsViewModel.getJobsFromDatabase(dataBaHelperImpl)
            Handler(Looper.getMainLooper()).postDelayed({
                jobsViewModel.setJobsList(jobsList)
            }, 100)

        }
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
        dataBaHelperImpl= DatabaseHelperImpl(JobsDatabase.getInstance(requireContext()))
        (requireActivity()as MainActivity).iv_back.visibility=View.INVISIBLE

    }
    private fun getJobs() {
        dialogUtility.showProgressDialog(requireContext())
        GlobalScope.launch {
            dataBaHelperImpl.deleteAllJobs()
        }
            jobsViewModel.getJobs().observe(viewLifecycleOwner, Observer {

                if (it.first) {
                    if (it.second != null && it.second.size > 0) {
                        jobsList.clear()
                        jobsList.addAll(it.second)
                        jobsViewModel.setJobsList(jobsList)
                        adapter.notifyDataSetChanged()
                        dialogUtility.hideProgressDialog()
                        GlobalScope.launch {
                            dataBaHelperImpl.insertAllJobs(jobsList)
                        }
                    } else {
                        dialogUtility.hideProgressDialog()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.no_data),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    dialogUtility.hideProgressDialog()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_msg),
                        Toast.LENGTH_LONG
                    ).show()

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
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

}