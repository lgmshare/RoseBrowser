package com.kakaxi.browser.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

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

fun View.hideSoftInput() {
    val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) {
        imm.hideSoftInputFromWindow(applicationWindowToken, 0)
    }
}