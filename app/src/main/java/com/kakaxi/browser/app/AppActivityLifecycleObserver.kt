package com.kakaxi.browser.app

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

object AppActivityLifecycleObserver : ActivityLifecycleCallbacks {

    private var activityCount = 0

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        activityCount++
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
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