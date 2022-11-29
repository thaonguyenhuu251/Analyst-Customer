package com.htnguyen.customeranalysis.ultils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.htnguyen.customeranalysis.R
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

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

}