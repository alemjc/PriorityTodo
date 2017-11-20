package com.apps.alemjc.prioritytodo.content

/**
 * Created by alemjc on 11/19/17.
 */
class Todo(var map:MutableMap<String, Any?>){

    var _id: Long by map;
    var description: String by map;
    var priority: Int by map;

    constructor(id: Long, description: String, priority: Int):this(HashMap()){
        this._id = id
        this.description = description
        this.priority = priority
    }


}