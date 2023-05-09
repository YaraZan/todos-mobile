package com.example.app.helper

class RegValidation(val email: String, val login: String, val password: String, val confPassword: String) {

    fun validate() : Boolean {
        var res = false
        if (!(email == "" || login == "" || password == "" || confPassword == "")) {
            if ("@" in email) {
                if (password == confPassword) {
                    res = true
                }
            }
        }
        return res
    }
}