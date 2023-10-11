package com.kakaxi.browser.app

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.kakaxi.browser.ad.AdManager

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        registerActivityLifecycleCallbacks(AppActivityLifecycleObserver)

        MobileAds.initialize(this)

        Firebase.initialize(this)
        //初始化RemoteConfig
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        })
        remoteConfig.fetchAndActivate()

        AdManager.init()
    }

}