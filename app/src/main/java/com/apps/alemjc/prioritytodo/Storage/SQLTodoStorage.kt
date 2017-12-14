package com.apps.alemjc.prioritytodo.Storage

import android.content.Context
import android.database.Cursor
import android.util.Log
import com.apps.alemjc.prioritytodo.Utils.SingletonHolder
import com.apps.alemjc.prioritytodo.content.Todo
import org.jetbrains.anko.db.*


/**
 * Created by Jean Carlos Herniquez on 11/19/17.
 */
class SQLTodoStorage private constructor(ctx: Context) : TodoStorage {
    private var db: SQLStorage? = null

    init {
        db = SQLStorage(ctx)

    }


    companion object : SingletonHolder<SQLTodoStorage, Context>(::SQLTodoStorage)


    override fun create(description: String, priority: Long): Boolean {
        var _id: Long = -1
        db!!.use {
            _id = insert(TodoTable.NAME, TodoTable.DESCRIPTION to description, TodoTable.PRIORITY to priority,
                    TodoTable.STATUS to TodoTableValues.PENDING_STATUS)

        }

        return _id >= 0
    }

    override fun update(_id: Long, description: String, priority: Long, status: String): Boolean {
        var result = -1
        db!!.use {
            result = update(TodoTable.NAME, TodoTable.DESCRIPTION to description, TodoTable.PRIORITY to priority,
                    TodoTable.STATUS to status)
                    .whereArgs("id = {id}", "id" to _id)
                    .exec()
        }

        return result > 0
    }

    override fun remove(_id: Long): Boolean {
        var result = -1
        db!!.use {
            result = delete(TodoTable.NAME, "id = {id}", "id" to _id)
        }

        return result > 0
    }

    override fun getTodos(): ArrayList<Todo> {

        val columns: Array<String> = arrayOf(TodoTable.ID, TodoTable.DESCRIPTION, TodoTable.PRIORITY)
        val todos: ArrayList<Todo> = ArrayList()
        db!!.use {
            val result: Cursor = query(true, TodoTable.NAME, columns, "*", null, null, null, null, null)
            val sequence: Sequence<Map<String, Any?>> = result.asMapSequence()
            sequence.forEach {
                val id = it[TodoTable.ID] as Long
                val description = it[TodoTable.DESCRIPTION] as String
                val priority = it[TodoTable.PRIORITY] as Long
                val status = it[TodoTable.STATUS] as String
                val aTodo = Todo(id, description, priority, status)

                todos.add(aTodo)
            }

            result.close()
        }


        return todos
    }

    fun getPendingTasks(): ArrayList<Todo> {

        val todos: ArrayList<Todo> = ArrayList()
        val selectionArgs: Array<String> = Array(1) {
            TodoTableValues.PENDING_STATUS
        }

        db!!.use {

            val result: Cursor = query(true, TodoTable.NAME, null, "status = ?", selectionArgs, null, null, "-" + TodoTable.PRIORITY, null)

            val sequence: Sequence<Map<String, Any?>> = result.asMapSequence()

            sequence.forEach {
                val id = it[TodoTable.ID] as Long
                val description = it[TodoTable.DESCRIPTION] as String
                val priority = it[TodoTable.PRIORITY] as Long
                val status = it[TodoTable.STATUS] as String
                val aTodo = Todo(id, description, priority, status)

                todos.add(aTodo)
            }

            result.close()
        }


        return todos
    }

    fun getDoneTasks(): ArrayList<Todo> {
        val todos: ArrayList<Todo> = ArrayList()
        val selectionArgs: Array<String> = Array(1) {
            TodoTableValues.DONE_STATUS
        }

        db!!.use {
            val result: Cursor = query(true, TodoTable.NAME, null, "status = ?", selectionArgs, null, null, null, null)
            val sequence: Sequence<Map<String, Any?>> = result.asMapSequence()

            sequence.forEach {
                val id = it[TodoTable.ID] as Long
                val description = it[TodoTable.DESCRIPTION] as String
                val priority = it[TodoTable.PRIORITY] as Long
                val status = it[TodoTable.STATUS] as String
                val aTodo = Todo(id, description, priority, status)

                todos.add(aTodo)
            }

            result.close()
        }


        return todos
    }

}