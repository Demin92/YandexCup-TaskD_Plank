package ru.demin.taskd_plank.recycler

import android.view.ViewGroup
import ru.demin.taskd_plank.base.recycler_view.BaseRecyclerItem
import ru.demin.taskd_plank.base.recycler_view.BaseRecyclerItemAdapter
import ru.demin.taskd_plank.base.recycler_view.BaseViewHolder
import java.lang.IllegalArgumentException

class AttemptsAdapter(private val eventListener: EventListener): BaseRecyclerItemAdapter<BaseRecyclerItem, BaseViewHolder<BaseRecyclerItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseRecyclerItem> {
        return when(viewType) {
            ViewType.ADD.value -> AddItemViewHolder(parent, eventListener)
            ViewType.TIME.value -> TimeItemViewHolder(parent)
            else -> throw IllegalArgumentException()
        } as BaseViewHolder<BaseRecyclerItem>
    }

    enum class ViewType(val value: Int){
        ADD(0), TIME(1)
    }

    interface EventListener: AddItemViewHolder.EventListener
}