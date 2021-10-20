package ru.demin.taskd_plank

import ru.demin.taskd_plank.base.recycler_view.BaseRecyclerItem

data class ViewState(
    val items: List<BaseRecyclerItem>,
    val attemptState: AttemptState? = null
) {
    sealed class AttemptState {
        object Prepare: AttemptState()
        class InProgress(val currentTimeInSeconds: Int): AttemptState()
    }
}