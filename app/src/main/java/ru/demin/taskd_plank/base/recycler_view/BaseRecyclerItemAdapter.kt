package ru.demin.taskd_plank.base.recycler_view

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseRecyclerItemAdapter<T : BaseRecyclerItem, VH : BaseViewHolder<T>> :
    ListAdapter<T, VH>(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.sameEntityAs(newItem)
        override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem.sameContentsAs(newItem)
    }) {

    override fun getItemViewType(position: Int): Int = getItem(position).viewType

    override fun onBindViewHolder(holder: VH, position: Int): Unit = holder.bind(getItem(position), position)
}