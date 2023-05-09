package com.example.app.helper

class AddFormValidation(val name: String, val descr: String, val deadline: String) {
    fun validation() : Boolean {
        var res = false
        if (!(name == "" || descr == "" || deadline == "")) {
            res = true
        }
        return res
    }
}