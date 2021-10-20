package ru.demin.taskd_plank.recycler

import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_add.view.*
import ru.demin.taskd_plank.R
import ru.demin.taskd_plank.base.recycler_view.BaseViewHolder

class AddItemViewHolder(parent: ViewGroup, private val eventListener: EventListener) :
    BaseViewHolder<AddItem>(parent, R.layout.item_add) {
    override fun bind(item: AddItem, position: Int) {
        itemView.item_add_root.setOnClickListener { eventListener.onAddClick() }
    }

    interface EventListener {
        fun onAddClick()
    }


}