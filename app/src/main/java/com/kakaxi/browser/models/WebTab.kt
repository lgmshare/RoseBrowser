package com.kakaxi.browser.models

import android.graphics.Bitmap
import android.webkit.WebView

data class WebTab(val bitmap: Bitmap?, val webView: WebView) {

    val createTime = System.currentTimeMillis()

}