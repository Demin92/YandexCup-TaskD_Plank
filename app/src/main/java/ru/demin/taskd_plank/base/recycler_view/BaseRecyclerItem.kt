package ru.demin.taskd_plank.base.recycler_view

abstract class BaseRecyclerItem: DiffUtilComparator<BaseRecyclerItem> {
    abstract val viewType: Int
}