package com.apps.alemjc.prioritytodo.content

import com.apps.alemjc.prioritytodo.Storage.TodoTableValues

/**
 * Created by alemjc on 11/19/17.
 */
class Todo(var map:MutableMap<String, Any?>){

    var _id: Long by map
    var description: String by map
    var priority: Int by map
    var status:String by map

    constructor(id: Long, description: String, priority: Int):this(id, description, priority, TodoTableValues.PENDING_STATUS)

    constructor(id: Long, description: String, priority: Int, status:String):this(HashMap()){
        this._id = id
        this.description = description
        this.priority = priority
        this.status = status
    }


}