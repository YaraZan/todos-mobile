package com.example.app.database

data class RegisterRequest(
    val login: String,
    val email: String,
    val password: String
)
