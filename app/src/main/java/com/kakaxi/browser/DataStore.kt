package com.kakaxi.browser

import android.app.Application
import android.content.SharedPreferences
import com.kakaxi.browser.app.App

object DataStore {
    private val sharePref: SharedPreferences = App.INSTANCE.getSharedPreferences("browser", Application.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean {
        val sf = sharePref
        val isFirstLaunch = sf.getBoolean("first_launch", true)
        if (isFirstLaunch) {
            sf.edit().putBoolean("first_launch", false).apply()
            return true
        }
        return false
    }


}