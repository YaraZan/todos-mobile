package com.example.app.database

import android.provider.BaseColumns

object TableObject {
    object FeedEntry : BaseColumns {
        var TABLE_NAME = "entry"
        var COLUMN_NAME_TITLE = "title"
        var COLUMN_NAME_SUBTITLE = "subtitle"
    }
}