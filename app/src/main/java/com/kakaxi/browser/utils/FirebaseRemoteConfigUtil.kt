package com.kakaxi.browser.utils

import android.util.Base64
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.kakaxi.browser.ad.AdConfigResponse
import java.nio.charset.StandardCharsets

class FirebaseRemoteConfigUtil {

    companion object {

        fun getAdConfig(): AdConfigResponse? {
            val encodeData = FirebaseRemoteConfig.getInstance().getString("ad_config").ifEmpty { "ewoibWF4X3Nob3dfbnVtIjozMCwKIm1heF9jbGlja19udW0iOjUsCiJ2X29wZW4iOnsKInR5cGUiOiJvcGVuIiwKImlkcyI6Wwp7CiJpZCI6ImNhLWFwcC1wdWItMzk0MDI1NjA5OTk0MjU0NC8zNDE5ODM1Mjk0IiwKInBsYXRmb3JtIjoiYWRtb2IiLAoic29ydCI6MQp9Cl0KfSwKInZfbmF0aXZlIjp7CiJ0eXBlIjoibmF0aXZlIiwKImlkcyI6Wwp7CiJpZCI6ImNhLWFwcC1wdWItMzk0MDI1NjA5OTk0MjU0NC8yMjQ3Njk2MTEwIiwKInBsYXRmb3JtIjoiYWRtb2IiLAoic29ydCI6MQp9Cl0KfSwKInZfaW50ZXIiOnsKInR5cGUiOiJpbnRlciIsCiJpZHMiOlsKewoiaWQiOiJjYS1hcHAtcHViLTM5NDAyNTYwOTk5NDI1NDQvMTAzMzE3MzcxMiIsCiJwbGF0Zm9ybSI6ImFkbW9iIiwKInNvcnQiOjEKfQpdCn0KfQoKCg==" }
            val decodeData = String(Base64.decode(encodeData, Base64.DEFAULT), StandardCharsets.UTF_8)
            try {
                Utils.log(decodeData)
                return Gson().fromJson(decodeData, AdConfigResponse::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}