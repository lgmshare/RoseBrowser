package com.kakaxi.browser.utils

import android.util.Base64
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.kakaxi.browser.ad.AdConfigResponse
import java.nio.charset.StandardCharsets

class FirebaseRemoteConfigUtil {

    companion object {

        fun getAdConfig(): AdConfigResponse? {
            val encodeData = FirebaseRemoteConfig.getInstance().getString("ad_config").ifEmpty { "ewoibWF4X3Nob3dfbnVtIjozMCwKIm1heF9jbGlja19udW0iOjUsCiJ2X2xvYWRpbmciOnsKInR5cGUiOiJvcGVuIiwKImlkcyI6Wwp7CiJpZCI6ImNhLWFwcC1wdWItMzk0MDI1NjA5OTk0MjU0NC8zNDE5ODM1Mjk0IiwKInBsYXRmb3JtIjoiYWRtb2IiLAoic29ydCI6MQp9Cl0KfSwKInZfaG9tZSI6ewoidHlwZSI6Im5hdGl2ZSIsCiJpZHMiOlsKewoiaWQiOiJjYS1hcHAtcHViLTM5NDAyNTYwOTk5NDI1NDQvMjI0NzY5NjExMCIsCiJwbGF0Zm9ybSI6ImFkbW9iIiwKInNvcnQiOjEKfQpdCn0sCiJ2X3RhcCI6ewoidHlwZSI6Im5hdGl2ZSIsCiJpZHMiOlsKewoiaWQiOiJjYS1hcHAtcHViLTM5NDAyNTYwOTk5NDI1NDQvMjI0NzY5NjExMCIsCiJwbGF0Zm9ybSI6ImFkbW9iIiwKInNvcnQiOjEKfQpdCn0sCiJ2X2NsZWFuIjp7CiJ0eXBlIjoiaW50ZXIiLAoiaWRzIjpbCnsKImlkIjoiY2EtYXBwLXB1Yi0zOTQwMjU2MDk5OTQyNTQ0LzEwMzMxNzM3MTIiLAoicGxhdGZvcm0iOiJhZG1vYiIsCiJzb3J0IjoxCn0KXQp9Cn0KCgo=" }
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