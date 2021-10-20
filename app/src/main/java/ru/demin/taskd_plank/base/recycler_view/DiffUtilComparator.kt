package ru.demin.taskd_plank.base.recycler_view

interface DiffUtilComparator<T> {
    fun <T> sameEntityAs(item: T): Boolean = this == item
    fun <T> sameContentsAs(item: T): Boolean = this == item
}