package com.apps.alemjc.prioritytodo.Storage

import com.apps.alemjc.prioritytodo.content.Todo

/**
 * Created by alemjc on 11/19/17.
 */
interface TodoStorage {

    fun create(description:String, priority: Int): Boolean
    fun update(_id:Long, description: String, priority: Int, status: String):Boolean
    fun remove(_id: Long):Boolean
    fun getTodos():ArrayList<Todo>

}