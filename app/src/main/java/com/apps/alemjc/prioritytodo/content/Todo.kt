package com.apps.alemjc.prioritytodo.content

import com.apps.alemjc.prioritytodo.Storage.TodoTableValues

/**
 * Created by Jean Carlos Henriquez on 11/19/17.
 * Holds task information in memory in order to display to the user.
 */
class Todo(map: MutableMap<String, Any?>) {

    var _id: Long by map
    var description: String by map
    var priority: Long by map
    var status: String by map

    constructor(id: Long, description: String, priority: Long) : this(id, description, priority, TodoTableValues.PENDING_STATUS)

    constructor(id: Long, description: String, priority: Long, status: String) : this(HashMap()) {
        this._id = id
        this.description = description
        this.priority = priority
        this.status = status
    }


}