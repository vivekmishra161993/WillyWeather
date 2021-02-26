package com.app.willyweathertest.jobsDetail

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.willyweathertest.databinding.FragmentJobDetailsBinding
import com.app.willyweathertest.network.models.Jobs

class JobsDetailFragment: Fragment() {
    private lateinit var binding: FragmentJobDetailsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentJobDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClasses()

    }
    private fun initClasses() {
        binding.lifecycleOwner = this
        val bundle = this.arguments
        if (bundle != null) {
            val job = bundle.getParcelable<Parcelable>("jobDetail")
            binding.model= job as Jobs?
        }
        binding.executePendingBindings()

    }

}