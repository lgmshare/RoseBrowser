package com.kakaxi.browser.ad

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.kakaxi.browser.utils.Utils
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

enum class AdPositionPage(val pos: AdPosition) {
    LOADING(AdPosition.LOADING), HOME(AdPosition.NATIVE), TAP(AdPosition.NATIVE), CLEAN(AdPosition.CLEAN);

    private var showTime: Long = 0

    fun show(activity: AppCompatActivity, adContainer: ViewGroup? = null, callback: (() -> Unit)? = null) {
        if (AdManager.isLimit(pos)) {
            callback?.invoke()
            return
        }

        if (System.currentTimeMillis() - showTime < 12000) {
            Utils.log("$this 刷新时间不足")
            return
        }

        if (activity.lifecycle.currentState != Lifecycle.State.RESUMED) {
            Utils.log("${this.name} show page state is not resumed")
            callback?.invoke()
            return
        }

        val datas = pos.getAdData()
        if (datas.isEmpty()) {
            Utils.log("${this.name} show data is empty")
            callback?.invoke()
            return
        }

        val data = datas.removeFirst()
        pos.load()

        AdManager.onAdShow()
        when (val ad = data.data) {
            is NativeAd -> {
                showTime = System.currentTimeMillis()
                AdUtils.showNativeAd(ad, activity, adContainer)
            }

            is InterstitialAd -> {
                AdUtils.showInterstitialAd(ad, activity, callback)
            }

            is AppOpenAd -> {
                AdUtils.showOpenAd(ad, activity, callback)
            }

            else -> {
                callback?.invoke()
            }
        }
    }
}

enum class AdPosition {

    LOADING, NATIVE, CLEAN;

    private var job: Job? = null
    private val cacheData = mutableListOf<AdData>()

    fun getAdData(): MutableList<AdData> {
        cacheData.removeIf { System.currentTimeMillis() - it.cacheTime > 3000 * 1000 }
        return cacheData
    }

    private fun isDataLoaded(): Boolean {
        cacheData.removeIf { System.currentTimeMillis() - it.cacheTime > 3000 * 1000 }
        if (cacheData.isNotEmpty()) {
            return true
        }
        return false
    }

    fun getAdType(): String {
        return when (this) {
            LOADING -> "open"
            CLEAN -> "inter"
            else -> "native"
        }
    }

    fun load(): Job? {
        if (job != null && job?.isActive == true) {
            Utils.log("${this.name} 加载中")
            return job
        }

        if (isDataLoaded()) {
            Utils.log("${this.name} 缓存不为空")
            return null
        }

        if (AdManager.isLimit(this)) {
            return null
        }

        val bean = AdManager.getPositionConfig(this)
        if (bean == null || bean.ids.isEmpty()) {
            Utils.log("${this.name} 配置为空")
            return null
        }

        job = MainScope().launch {
            Utils.log("${this@AdPosition.name} ##########开始加载广告##########")
            run oneTime@{
                bean.ids.forEach {
                    val data = when (bean.type) {
                        "native" -> {
                            AdUtils.loadNative(it)
                        }

                        "open" -> {
                            AdUtils.loadOpen(it)
                        }

                        "inter" -> {
                            AdUtils.loadInter(it)
                        }

                        else -> {
                            AdData(it, null, false, "no platform")
                        }
                    }

                    if (data.success) {
                        Utils.log("${this@AdPosition.name} 广告加载成功")
                        cacheData.add(data)
                        return@oneTime
                    } else {
                        Utils.log("${this@AdPosition.name} 广告加载失败：" + data)
                    }
                }
            }
            Utils.log("${this@AdPosition.name} ##########加载广告结束##########")
        }

        return job
    }
}