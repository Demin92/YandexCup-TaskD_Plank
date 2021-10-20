package ru.demin.taskd_plank.recycler

import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_add.view.*
import kotlinx.android.synthetic.main.item_time.view.*
import ru.demin.taskd_plank.R
import ru.demin.taskd_plank.base.recycler_view.BaseViewHolder
import ru.demin.taskd_plank.convertToTime

class TimeItemViewHolder(parent: ViewGroup) : BaseViewHolder<TimeItem>(parent, R.layout.item_time) {
    override fun bind(item: TimeItem, position: Int) {
        itemView.item_time.text = item.timeInSeconds.convertToTime()
    }
}