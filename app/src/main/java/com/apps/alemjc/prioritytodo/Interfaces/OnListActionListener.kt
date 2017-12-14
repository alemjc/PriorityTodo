package com.apps.alemjc.prioritytodo.Interfaces

import com.apps.alemjc.prioritytodo.content.Todo

/**
 * Created by Jean Carlos Henriquez on 12/3/17.
 */
interface OnListActionListener {
    fun removeTodo(id: Long)
    fun update(id: Long, description: String, priority: Long, status: String)
    fun statusUpdate(todoItem: Todo, newStatus: String)
    fun updateTodos()
}