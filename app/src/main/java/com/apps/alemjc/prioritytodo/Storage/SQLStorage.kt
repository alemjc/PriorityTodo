package com.apps.alemjc.prioritytodo.Storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by alemjc on 11/19/17.
 */
class SQLStorage(ctx: Context): ManagedSQLiteOpenHelper(ctx, SQLStorage.DB_NAME, null, SQLStorage.VERSION) {

    companion object {
        val DB_NAME = "todo.db"
        val VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.createTable(TodoTable.Name, true,
                TodoTable.ID to INTEGER + PRIMARY_KEY,
                TodoTable.DESCRIPTION to TEXT,
                TodoTable.PRIORITY to INTEGER)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.dropTable(TodoTable.Name, true)
        onCreate(p0)
    }
}