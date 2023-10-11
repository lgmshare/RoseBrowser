package com.kakaxi.browser.app

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.ads.AdActivity
import com.kakaxi.browser.activitys.SplashActivity

object AppActivityLifecycleObserver : ActivityLifecycleCallbacks {

    private var activityCount = 0
    private var count = 0

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        activityCount++
    }

    override fun onActivityStarted(p0: Activity) {
        count++
        if (count == 1) {
            if (p0 !is SplashActivity) {
                p0.startActivity(Intent(p0, SplashActivity::class.java))
            }
        }
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
        count++
        if (p0 is AdActivity) {
            p0.finish()
        }
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
        activityCount--
    }

    fun hasMoreActivity(): Boolean {
        return activityCount > 1
    }

}