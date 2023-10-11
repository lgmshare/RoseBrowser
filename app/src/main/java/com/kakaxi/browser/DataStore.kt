package com.kakaxi.browser

import android.app.Application
import android.content.SharedPreferences
import android.text.TextUtils
import com.kakaxi.browser.app.App
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DataStore {
    private val sharePref: SharedPreferences = App.INSTANCE.getSharedPreferences("rise_browser", Application.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean {
        val sf = sharePref
        val isFirstLaunch = sf.getBoolean("first_launch", true)
        if (isFirstLaunch) {
            sf.edit().putBoolean("first_launch", false).apply()
            return true
        }
        return false
    }

    ///////////////////ad param//////////////
    //是否当日广告
    fun isAdToday(): Boolean {
        val day = sharePref.getString("ad_datetime", "")
        val today = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date(System.currentTimeMillis()))
        return if (TextUtils.equals(day, today)) {
            true
        } else {
            sharePref.edit().putString("ad_datetime", today).apply()
            false
        }
    }

    //展示次数
    var showCount: Int
        get() = sharePref.getInt("ad_show_count", 0)
        set(value) = sharePref.edit().putInt("ad_show_count", value).apply()

    //点击次数
    var clickCount: Int
        get() = sharePref.getInt("ad_click_count", 0)
        set(value) = sharePref.edit().putInt("ad_click_count", value).apply()


}