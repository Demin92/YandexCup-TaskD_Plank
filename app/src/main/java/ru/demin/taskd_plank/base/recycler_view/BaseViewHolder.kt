package ru.demin.taskd_plank.base.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T : BaseRecyclerItem>(
    parentView: ViewGroup, @LayoutRes layoutRes: Int
) : RecyclerView.ViewHolder(LayoutInflater.from(parentView.context).inflate(layoutRes, parentView, false)),
    LayoutContainer {
    abstract fun bind(item: T, position: Int)

    override val containerView: View?
        get() = itemView
}