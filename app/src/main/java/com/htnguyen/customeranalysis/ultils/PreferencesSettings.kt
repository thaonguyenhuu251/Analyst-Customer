package com.htnguyen.customeranalysis.ultils

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

object PreferencesSettings {
    private const val PREF_FILE = "settings_pref"

    fun saveToFinger(context: Context, boolean: Boolean) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(Constants.FINGERON, boolean)
        editor.apply()
    }

    fun getFinger(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = false
        return sharedPref.getBoolean(Constants.FINGERON, defaultValue)
    }

    fun saveToPref(context: Context, str: String?) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(Constants.PASSCODE, str)
        editor.apply()
    }

    fun getCode(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = ""
        return sharedPref.getString(Constants.PASSCODE, defaultValue)
    }

    fun setThemes(context: Context, str: Boolean) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(Constants.THEMES, str)
        editor.apply()
    }

    fun getThemes(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = false
        return sharedPref.getBoolean(Constants.THEMES, defaultValue)
    }

    fun setLanguage(context: Context, str: String) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(Constants.LANGUAGE, str)
        editor.apply()
    }

    fun getLanguage(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = Constants.LG_VIETNAMESE
        return sharedPref.getString(Constants.LANGUAGE, defaultValue)
    }

    fun applyTheme(theme: Int) {
        when (theme) {
            AppCompatDelegate.MODE_NIGHT_NO, AppCompatDelegate.MODE_NIGHT_YES -> {
                AppCompatDelegate.setDefaultNightMode(theme)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

}