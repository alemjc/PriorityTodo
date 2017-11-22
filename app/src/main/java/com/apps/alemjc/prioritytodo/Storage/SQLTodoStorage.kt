package com.apps.alemjc.prioritytodo.Storage

import android.content.Context
import android.database.Cursor
import com.apps.alemjc.prioritytodo.content.Todo
import org.jetbrains.anko.db.*


/**
 * Created by alemjc on 11/19/17.
 */
class SQLTodoStorage(ctx: Context) : TodoStorage{

    private val db = SQLStorage(ctx)

    override fun create(description: String, priority: Int): Boolean {
        val _id = db.use {

            insert(TodoTable.Name, TodoTable.DESCRIPTION to description, TodoTable.PRIORITY to priority)

        }

        return _id >= 0
    }

    override fun update(_id: Long, description: String, priority: Int): Boolean {
        val result = db.use {
            update(TodoTable.Name, TodoTable.DESCRIPTION to description, TodoTable.PRIORITY to priority)
                    .whereArgs("_id = {id}", "id" to _id)
                    .exec()
        }

        return result >= 0
    }

    override fun remove(_id: Long): Boolean {
        val result:Int =  db.use {
            delete(TodoTable.Name, "_id = {id}", "id" to _id)
        }

        return result >=0
    }

    override fun getTodos(): ArrayList<Todo> {

        val columns: Array<String> = arrayOf(TodoTable.ID, TodoTable.DESCRIPTION, TodoTable.PRIORITY)
        val todos:ArrayList<Todo> = ArrayList()
        val result:Cursor = db.use {
            query(true, TodoTable.Name, columns, "*", null, null, null, null, null)
        }

        result.use {

            while(!it.isAfterLast){
                val id = result.getLong(result.getColumnIndex(TodoTable.ID))
                val description = result.getString(result.getColumnIndex(TodoTable.DESCRIPTION))
                val priority = result.getInt(result.getColumnIndex(TodoTable.PRIORITY))

                val aTodo = Todo(id, description, priority)

                todos.add(aTodo)
            }
        }


        return todos
    }

}