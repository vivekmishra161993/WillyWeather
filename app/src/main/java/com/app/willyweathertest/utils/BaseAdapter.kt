package com.app.willyweathertest.utils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    abstract val size: Int

    @get:LayoutRes
    abstract val layout: Int

    abstract fun onBindHolder(holder: BaseViewHolder, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false))

    override fun getItemCount(): Int = size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindHolder(holder, position)
        holder.binding.executePendingBindings()
    }
}