package com.apps.alemjc.prioritytodo.Interfaces

import com.apps.alemjc.prioritytodo.content.Todo

/**
 * Created by Jean Carlos Henriquez on 12/3/17.
 * Action listener for updating tasks.
 */
interface OnListActionListener {

    /**
     * Action method that would remove task from data source
     * @param id: id of task to remove.
     */
    fun removeTodo(id: Long)

    /**
     * Action method that would update task from data source. This method will update
     * task with all the values given.
     * @param id: id of task to update
     * @param description: description of task
     * @param priority: priority level of this task
     * @param status: status of task which could be pending or done
     */
    fun update(id: Long, description: String, priority: Long, status: String)

    /**
     * Action method that would update status of task.
     * @param todoItem: Entire task object containing all information
     * @param newStatus: new status to write for to do object in data source
     */
    fun statusUpdate(todoItem: Todo, newStatus: String)
    fun updateTodos()
}