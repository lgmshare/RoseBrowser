package com.kakaxi.browser.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import com.kakaxi.browser.BuildConfig
import com.kakaxi.browser.R


fun Context.toast(msg: String?) {
    if (!msg.isNullOrBlank()) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Context.toast(@StringRes msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 * dp值转换为px
 */
fun Context.dp2px(dp: Float): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

/**
 * px值转换成dp
 */
fun Context.px2dp(px: Float): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

/**
 * 获取屏幕宽度
 */
fun Context.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

/**
 * 获取屏幕高度
 */
fun Context.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

/**
 * 调用系统分享
 */
fun Context.jumpShare(shareText: String?, shareTitle: String? = getString(R.string.txt_share)) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareText ?: "")
        startActivity(Intent.createChooser(intent, shareTitle ?: ""))
    } catch (e: Exception) {
    }
}

fun Context.copyToClipboard(text: String) {
    val clip = ClipData.newPlainText(getString(R.string.app_name), text)
    (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(clip)
    toast(getString(R.string.copy_successfully))
}

fun Context.jumpGooglePlayStore() {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            intent.data = Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}");
            if (intent.resolveActivity(packageManager) == null) {
                startActivity(intent)
            } else {
                toast("You don't have an app market installed, not even a browser!")
            }
        }
    } catch (e: Exception) {
    }
}