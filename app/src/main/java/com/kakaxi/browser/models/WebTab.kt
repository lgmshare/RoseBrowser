package com.kakaxi.browser.models

import android.graphics.Bitmap
import android.webkit.WebView
import com.kakaxi.browser.views.MyWebView

data class WebTab(var bitmap: Bitmap?, val webView: MyWebView) {

    val createTime = System.currentTimeMillis()

    var inputText: String? = null
}