package com.kakaxi.browser.utils

import android.util.Log
import com.kakaxi.browser.BuildConfig
import com.kakaxi.browser.R
import com.kakaxi.browser.models.WebLink

class Utils {
    companion object {

        fun log(msg: String?) {
            if (BuildConfig.DEBUG) {
                if (!msg.isNullOrEmpty()) {
                    Log.d("Rose_TAG", msg)
                }
            }
        }

        fun getWebLinks(): List<WebLink> {
            return mutableListOf<WebLink>().apply {
                add(WebLink("facebook", "Facebook", "https://www.facebook.com", R.mipmap.icon_link_fb))
                add(WebLink("google", "google", "https://www.google.com", R.mipmap.icon_link_google))
                add(WebLink("youtube", "youtube", "https://www.youtube.com", R.mipmap.icon_link_yt))
                add(WebLink("twitter", "twitter", "https://www.twitter.com", R.mipmap.icon_link_tw))
                add(WebLink("instagram", "instagram", "https://www.instagram.com", R.mipmap.icon_link_ins))
                add(WebLink("amazon", "amazon", "https://www.amazon.com", R.mipmap.icon_link_am))
                add(WebLink("tiktok", "tiktok", "https://www.tiktok.com", R.mipmap.icon_link_tt))
                add(WebLink("yahoo", "yahoo", "www.yahoo.com", R.mipmap.icon_link_yh))
            }
        }
    }
}