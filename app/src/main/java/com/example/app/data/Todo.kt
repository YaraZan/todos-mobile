package com.example.app.data

import java.sql.Timestamp

data class Todo(val id: Int, val uid: Int, val name: String, val descr: String, val deadline: String, val isDone: Boolean, val isDeleted: Boolean)

