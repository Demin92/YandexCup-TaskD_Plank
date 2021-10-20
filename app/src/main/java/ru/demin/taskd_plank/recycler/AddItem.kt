package ru.demin.taskd_plank.recycler

import ru.demin.taskd_plank.base.recycler_view.BaseRecyclerItem

class AddItem: BaseRecyclerItem() {
    override val viewType = AttemptsAdapter.ViewType.ADD.value
}