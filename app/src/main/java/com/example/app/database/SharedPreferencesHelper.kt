package com.example.app.database

import android.content.SharedPreferences

class SharedPreferencesHelper(sharedpref: SharedPreferences) {

    val editor = sharedpref.edit()
    val shp = sharedpref

    fun setCurrentUser(id: Int) {
        editor.putInt("current_user", id)
        editor.apply()
    }

    fun getCurrentUser() : Int {
        return shp.getInt("current_user", -1)
    }

}