package com.htnguyen.customeranalysis.ultils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.htnguyen.customeranalysis.R

object FileUtils {
    fun isPrimeNumber(n: Int): Boolean {
        if (n < 2) {
            return false
        }
        val squareRoot = Math.sqrt(n.toDouble()).toInt()
        for (i in 2..squareRoot) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getStringLanguage(language: String): String {
        return when (language) {
            Constants.LG_VIETNAMESE -> "Vietnamese"
            Constants.LG_ENGLISH -> "English"
            Constants.LG_RUSSIAN -> "Russian"
            Constants.LG_KOREAN -> "Korean"
            Constants.LG_LAOS -> "Laos"
            Constants.LG_THAI -> "Thai"
            Constants.LG_MYANMAR -> "Myanmar"
            Constants.LG_CHINESE -> "Chinese"
            Constants.LG_JAPANESE -> "Japanese"
            Constants.LG_FILIPINO -> "Filipino"
            Constants.LG_INDONESIAN -> "Indonesian"
            Constants.LG_SPANISH -> "Spanish"
            Constants.LG_FRENCH -> "French"
            Constants.LG_INDIAN -> "Indian"
            Constants.LG_GERMAN -> "German"
            Constants.LG_ITALIAN -> "Italian"
            else -> "Vietnamese"
        }
    }

    fun getLanguages(language: String): Int {
        return when (language) {
            Constants.LG_VIETNAMESE -> R.drawable.ic_language_vietnam
            Constants.LG_ENGLISH -> R.drawable.ic_language_english_uk
            Constants.LG_RUSSIAN -> R.drawable.ic_language_russian
            Constants.LG_KOREAN -> R.drawable.ic_language_south_korea
            Constants.LG_LAOS -> R.drawable.ic_language_laos
            Constants.LG_THAI -> R.drawable.ic_language_thailand
            Constants.LG_MYANMAR -> R.drawable.ic_language_myanmar
            Constants.LG_CHINESE -> R.drawable.ic_china_svgrepo_com
            Constants.LG_JAPANESE -> R.drawable.ic_language_japan
            Constants.LG_FILIPINO -> R.drawable.ic_language_philippines
            Constants.LG_INDONESIAN -> R.drawable.ic_language_indonesia
            Constants.LG_SPANISH -> R.drawable.ic_language_spain
            Constants.LG_FRENCH -> R.drawable.ic_language_france
            Constants.LG_INDIAN -> R.drawable.ic_language_india
            Constants.LG_GERMAN -> R.drawable.ic_language_germany
            Constants.LG_ITALIAN -> R.drawable.ic_language_italy
            else -> R.drawable.ic_language_vietnam
        }
    }

    fun getColorTheme(color: Int): Int {
        return when (color) {
            R.id.rbColor1 -> R.style.Theme_AppTeal
            R.id.rbColor2-> R.style.Theme_AppOrange
            R.id.rbColor3 -> R.style.Theme_AppSkyBlue
            R.id.rbColor4 -> R.style.Theme_AppGreen
            R.id.rbColor5 -> R.style.Theme_AppYellow
            R.id.rbColor6 -> R.style.Theme_AppRed
            R.id.rbColor7 -> R.style.Theme_AppBlue
            else -> R.style.Theme_AppBrown
        }
    }

    fun getColorDark(color: Int): Int {
        return when (color) {
            R.id.rbColor1 -> R.style.MyDialog
            R.id.rbColor2 -> R.style.MyDialog
            R.id.rbColor3 -> R.style.MyDialog
            R.id.rbColor4 -> R.style.MyDialog
            R.id.rbColor5 -> R.style.MyDialog
            R.id.rbColor6 -> R.style.MyDialog
            R.id.rbColor7 -> R.style.MyDialog
            else -> R.style.MyDialog
        }
    }

    fun getColorString(color: Int): String {
        return when (color) {
            R.id.rbColor1 -> Constants.COLOR_TEAL
            R.id.rbColor2 -> Constants.COLOR_ORANGE
            R.id.rbColor3 -> Constants.COLOR_SKYBLUE
            R.id.rbColor4 -> Constants.COLOR_GREEN
            R.id.rbColor5 -> Constants.COLOR_YELLOW
            R.id.rbColor6 -> Constants.COLOR_RED
            R.id.rbColor7 -> Constants.COLOR_BLUE
            else -> Constants.COLOR_BROWN
        }
    }

}