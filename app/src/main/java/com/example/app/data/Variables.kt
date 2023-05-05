package com.example.app.data

data class Variables (
    val ACTIVE_USER: User,
    val DB_URL: String = "jdbc:postgresql://localhost:5432/todos",
    val DB_USER: String = "postgres",
    val DB_PASSWORD: String = "123"
)