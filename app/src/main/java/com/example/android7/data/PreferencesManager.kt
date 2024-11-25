package com.example.android7.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun saveTheme(isDarkMode: Boolean) {
        preferences.edit().putBoolean("dark_mode", isDarkMode).apply()
    }

    fun isDarkMode(): Boolean {
        return preferences.getBoolean("dark_mode", false)
    }

    fun savePlayerCount(count: Int) {
        preferences.edit().putInt("player_count", count).apply()
    }

    fun getPlayerCount(): Int {
        return preferences.getInt("player_count", 0)
    }
}
