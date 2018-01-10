package com.apps.alemjc.prioritytodo.Storage

import com.apps.alemjc.prioritytodo.content.Todo

/**
 * Created by Jean Carlos Henriquez on 11/19/17.
 *
 * Interface for tasks data source.
 *
 * The aim with this interface was to make it simple
 * to upgrade the application for the use of a different data storage
 */
interface TodoStorage {

    /**
     * Used to create new data in the data storage
     * @param description: This is the description of the new task
     * @param priority: initial priority of task to create
     */
    fun create(description: String, priority: Long): Boolean

    /**
     * Would update a task with the information given
     * @param _id: Unique identifier of task that would be used to search the task for updating
     * @param description: new description for task, if this is not to be updated then send the same description for task
     * @param priority: new priority for task, if this is not to be updated then send old task description
     * @param status: new status for task, if this is not to be updated then send old status
     */
    fun update(_id: Long, description: String, priority: Long, status: String): Boolean

    /**
     * Would remove task from data storage
     *
     * @param _id: Unique identifier that is used to search for the task in order to remove it.
     */
    fun remove(_id: Long): Boolean

    /**
     * Gets all tasks from data source
     *
     * @return: all tasks as ArrayList
     */
    fun getTodos(): ArrayList<Todo>

}