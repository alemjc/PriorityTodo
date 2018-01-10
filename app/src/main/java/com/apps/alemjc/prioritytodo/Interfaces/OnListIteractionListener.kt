package com.apps.alemjc.prioritytodo.Interfaces

/**
 * Created by Jean Carlos Henriquez on 12/3/17.
 */
interface OnListIteractionListener {
    /**
     * Action method responsible for updating the pending tasks view
     */
    fun updatePendingTasksView()

    /**
     * Action method responsible for updating the done tasks view
     */
    fun updateDoneTasksView()
}