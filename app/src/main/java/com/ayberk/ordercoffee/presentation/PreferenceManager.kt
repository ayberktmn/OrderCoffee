package com.ayberk.ordercoffee.util

import android.content.Context

object PreferenceManager {

    private const val PREF_NAME = "user_prefs"

    private fun getSharedPreferences(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        getSharedPreferences(context).edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean("is_logged_in", false)
    }

    fun clear(context: Context) {
        getSharedPreferences(context).edit().clear().apply()
    }
}
