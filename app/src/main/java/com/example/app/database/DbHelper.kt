package com.example.app.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val queries = Queries()
    private val creteTablseUsers = queries.SQL_CREATE_TABLE_USERS
    private val createTableTodos = queries.SQL_CREATE_TABLE_TODOS
    private val createTrigger = queries.ON_INSERT_TRIGGER
    private val insertTestTodos = queries.LOAD_TEST_TODOS


    val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${"users"}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(creteTablseUsers)
        db.execSQL(createTableTodos)
        db.execSQL(createTrigger)
        db.execSQL(insertTestTodos)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Todos.db"
    }
}