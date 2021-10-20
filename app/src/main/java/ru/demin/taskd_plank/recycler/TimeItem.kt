package ru.demin.taskd_plank.recycler

import ru.demin.taskd_plank.base.recycler_view.BaseRecyclerItem

class TimeItem(val timeInSeconds: Int): BaseRecyclerItem() {
    override val viewType = AttemptsAdapter.ViewType.TIME.value
}