package com.kakaxi.browser

import android.webkit.WebView
import com.kakaxi.browser.app.App
import com.kakaxi.browser.models.WebTab

object WebTabManager {

    private val list = arrayListOf<WebTab>()

    fun addNewWeb() {
        list.add(WebTab(null, WebView(App.INSTANCE)))
    }

    fun remove(webTab: WebTab) {
        list.remove(webTab)
    }


    




}