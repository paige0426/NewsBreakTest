package com.newsbreak.test.utils

import android.content.Context

class Utils {
    companion object {
        fun writeTimeStamp(context: Context, timeStamp: Long) {
            val editor = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE).edit()

            editor.putLong(LAST_REFRESH_KEY, timeStamp)
            editor.apply()
        }

        fun readLastRefershTime(context: Context) = context
            .getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
            .getLong(LAST_REFRESH_KEY, -1)

        private const val PREFERENCE_KEY = "news_break_test_preference"
        private const val LAST_REFRESH_KEY = "last_refresh_time"
    }
}