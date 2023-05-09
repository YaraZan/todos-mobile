package com.example.app.helper

class AuthValidation(val login: String, val password: String) {
    fun validate() : Boolean {
        var res = false
        if (!(login == "" || password == "")) {
            res = true
        }
        return res
    }
}