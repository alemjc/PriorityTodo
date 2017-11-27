package com.apps.alemjc.prioritytodo.Storage

import android.content.Context
import android.database.Cursor
import com.apps.alemjc.prioritytodo.Utils.SingletonHolder
import com.apps.alemjc.prioritytodo.content.Todo
import org.jetbrains.anko.db.*


/**
 * Created by alemjc on 11/19/17.
 */
class SQLTodoStorage private constructor(ctx: Context) : TodoStorage{
    private var db:SQLStorage? = null
    init {
         db = SQLStorage(ctx)
    }


    companion object:SingletonHolder<SQLTodoStorage, Context>(::SQLTodoStorage)


    override fun create(description: String, priority: Int): Boolean {
        val _id = db!!.use {

            insert(TodoTable.NAME, TodoTable.DESCRIPTION to description, TodoTable.PRIORITY to priority,
                    TodoTable.STATUS to TodoTableValues.PENDING_STATUS)

        }

        return _id >= 0
    }

    override fun update(_id: Long, description: String, priority: Int, status:String): Boolean {
        val result = db!!.use {
            update(TodoTable.NAME, TodoTable.DESCRIPTION to description, TodoTable.PRIORITY to priority,
                    TodoTable.STATUS to status)
                    .whereArgs("_id = {id}", "id" to _id)
                    .exec()
        }

        return result >= 0
    }

    override fun remove(_id: Long): Boolean {
        val result:Int =  db!!.use {
            delete(TodoTable.NAME, "_id = {id}", "id" to _id)
        }

        return result >=0
    }

    override fun getTodos(): ArrayList<Todo> {

        val columns: Array<String> = arrayOf(TodoTable.ID, TodoTable.DESCRIPTION, TodoTable.PRIORITY)
        val todos:ArrayList<Todo> = ArrayList()
        val result:Cursor = db!!.use {
            query(true, TodoTable.NAME, columns, "*", null, null, null, null, null)
        }

        result.use {

            while(!it.isAfterLast){
                val id = result.getLong(result.getColumnIndex(TodoTable.ID))
                val description = result.getString(result.getColumnIndex(TodoTable.DESCRIPTION))
                val priority = result.getInt(result.getColumnIndex(TodoTable.PRIORITY))
                val status = result.getString(result.getColumnIndex(TodoTable.STATUS))
                val aTodo = Todo(id, description, priority, status)

                todos.add(aTodo)
            }
        }


        return todos
    }

    fun getPendingTasks(): ArrayList<Todo>{
        val columns: Array<String> = arrayOf(TodoTable.ID, TodoTable.DESCRIPTION, TodoTable.PRIORITY)
        val todos:ArrayList<Todo> = ArrayList()
        val selectionArgs:Array<String> = Array(1){
            TodoTableValues.PENDING_STATUS
        }

        val result:Cursor = db!!.use {
            query(true, TodoTable.NAME, columns, "status = ?", selectionArgs, null, null, null, null)
        }

        result.use {

            while(!it.isAfterLast){
                val id = result.getLong(result.getColumnIndex(TodoTable.ID))
                val description = result.getString(result.getColumnIndex(TodoTable.DESCRIPTION))
                val priority = result.getInt(result.getColumnIndex(TodoTable.PRIORITY))
                val status = result.getString(result.getColumnIndex(TodoTable.STATUS))
                val aTodo = Todo(id, description, priority, status)

                todos.add(aTodo)
            }
        }


        return todos
    }

    fun getDoneTasks(): ArrayList<Todo>{
        val columns: Array<String> = arrayOf(TodoTable.ID, TodoTable.DESCRIPTION, TodoTable.PRIORITY)
        val todos:ArrayList<Todo> = ArrayList()
        val selectionArgs:Array<String> = Array(1){
            TodoTableValues.DONE_STATUS
        }

        val result:Cursor = db!!.use {
            query(true, TodoTable.NAME, columns, "status = ?", selectionArgs, null, null, null, null)
        }

        result.use {

            while(!it.isAfterLast){
                val id = result.getLong(result.getColumnIndex(TodoTable.ID))
                val description = result.getString(result.getColumnIndex(TodoTable.DESCRIPTION))
                val priority = result.getInt(result.getColumnIndex(TodoTable.PRIORITY))
                val status = result.getString(result.getColumnIndex(TodoTable.STATUS))
                val aTodo = Todo(id, description, priority, status)

                todos.add(aTodo)
            }
        }


        return todos
    }


    //TODO: create function to get all done tasks.
}