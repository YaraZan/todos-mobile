package com.example.app.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SqliteService(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val queries = Queries()

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(queries.SQL_CREATE_TABLE_USERS)
        db.execSQL(queries.SQL_CREATE_TABLE_TODOS)
        db.execSQL(queries.LOAD_TEST_TODOS)
        db.execSQL(queries.LOAD_TEST_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(queries.SQL_DELETE_USERS)
        db.execSQL(queries.SQL_DELETE_TODOS)
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