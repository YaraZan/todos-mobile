package com.example.app.data


data class User(val id: Int, val login: String, val email: String, val password: String, var todos: ArrayList<Todo>)
