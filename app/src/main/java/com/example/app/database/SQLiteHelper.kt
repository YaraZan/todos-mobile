package com.example.app.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.app.data.Todo
import com.example.app.data.User
import com.example.app.data.Variables
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SQLiteHelper(database: SQLiteOpenHelper) {

    val writableDb = database.writableDatabase
    val readableDb = database.readableDatabase

    fun registerUser(login: String, email: String, password: String) : Pair<Boolean, Int> {
        val values = ContentValues().apply {
            put("user_login", login)
            put("user_email", email)
            put("user_password", password)
        }
        val newRowId = writableDb?.insert("users", null, values)

        return if (newRowId != null) {
            Pair(true, newRowId.toInt())
        } else {
            Pair(false, 0)
        }

    }

    fun authorizeUser(login: String, password: String) : Pair<Boolean, Int> {
        val projection = arrayOf("user_id")

        val selection = "user_login = ? AND user_password = ?"
        val selectionArgs = arrayOf(login, password)

        val cursor = readableDb.query(
            "users",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        return if (cursor.moveToNext()) {
            val uid = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
            Pair(true, uid)
        } else {
            Pair(false, 0)
        }
    }

    private fun getUsersTodos(id: Int) : ArrayList<Todo> {
        val todos = ArrayList<Todo>()

        val projection = arrayOf("*")

        val selection = "user_id = ?"
        val selectionArgs = arrayOf("$id")

        val cursor = readableDb.query(
            "todos",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val todo = Todo(
                    getInt(getColumnIndexOrThrow("todo_id")),
                    getInt(getColumnIndexOrThrow("user_id")),
                    getString(getColumnIndexOrThrow("todo_name")),
                    getString(getColumnIndexOrThrow("todo_descr")),
                    getString(getColumnIndexOrThrow("todo_deadline")),
                    getInt(getColumnIndexOrThrow("isDone")) > 0,
                    getInt(getColumnIndexOrThrow("isDeleted")) > 0)
                todos.add(todo)
            }
        }

        todos.reverse()
        return todos
    }

    fun getUndoneTodos(id: Int) : ArrayList<Todo> {
        val todos = getUsersTodos(id)
        val undoneTodos = ArrayList<Todo>()

        todos.forEach { todo ->
            if (!(todo.isDone)) {
                undoneTodos.add(todo)
            }
        }

        undoneTodos.reverse()
        return undoneTodos
    }

    fun getDoneTodos(id: Int) : ArrayList<Todo> {
        val todos = getUsersTodos(id)
        val doneTodos = ArrayList<Todo>()

        todos.forEach { todo ->
            if (todo.isDone) {
                doneTodos.add(todo)
            }
        }

        doneTodos.reverse()
        return doneTodos
    }

    fun createTodo(id: Int, name: String, descr: String, deadline: String) : Boolean {
        val values = ContentValues().apply {
            put("user_id", id)
            put("todo_name", name)
            put("todo_descr", descr)
            put("todo_deadline", deadline)
        }
        val newRowId = writableDb?.insert("todos", null, values)

        return newRowId != null
    }

    fun deleteTodo(id: Int) {
        val selection = "todo_id LIKE ?"
        val selectionArgs = arrayOf("$id")
        readableDb.delete("todos", selection, selectionArgs)
    }

    fun doneTodo(id: Int) {
        val title = true
        val values = ContentValues().apply {
            put("isDone", title)
        }
        val selection = "todo_id LIKE ?"
        val selectionArgs = arrayOf("$id")
        writableDb.update(
            "todos",
            values,
            selection,
            selectionArgs)
    }
}