package com.kakaxi.browser.extensions

import android.view.View

fun View.setOnBusyClickListener(callback: (() -> Unit)) {
    var lastClickTime = 0L
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < 500) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        callback.invoke()
    }
}