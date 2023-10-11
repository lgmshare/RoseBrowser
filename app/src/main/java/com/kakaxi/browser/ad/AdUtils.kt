package com.kakaxi.browser.ad

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.kakaxi.browser.app.App
import com.kakaxi.browser.databinding.ItemAdBinding
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AdUtils {
    companion object {

        fun showNativeAd(nativeAd: NativeAd, activity: Activity, adContainer: ViewGroup? = null) {
            val adBinding = ItemAdBinding.inflate(activity.layoutInflater)
            val nativeAdView = adBinding.root

            nativeAdView.headlineView = adBinding.adHeadline
            nativeAdView.bodyView = adBinding.adBody
            nativeAdView.callToActionView = adBinding.adCallToAction
            nativeAdView.iconView = adBinding.adAppIcon

            adBinding.adHeadline.text = nativeAd.headline

            if (nativeAd.body == null) {
                adBinding.adBody.visibility = View.INVISIBLE
            } else {
                adBinding.adBody.visibility = View.VISIBLE
                adBinding.adBody.text = nativeAd.body
            }

            if (nativeAd.callToAction == null) {
                adBinding.adCallToAction.visibility = View.INVISIBLE
            } else {
                adBinding.adCallToAction.visibility = View.VISIBLE
                adBinding.adCallToAction.text = nativeAd.callToAction
            }

            if (nativeAd.icon == null) {
                adBinding.adAppIcon.visibility = View.GONE
            } else {
                adBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
                adBinding.adAppIcon.visibility = View.VISIBLE
            }

            nativeAdView.setNativeAd(nativeAd)

            val mediaContent = nativeAd.mediaContent
            val vc = mediaContent?.videoController

            if (vc != null && mediaContent.hasVideoContent()) {
                vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        super.onVideoEnd()
                    }
                }
            }

            if (adContainer?.visibility == View.GONE) {
                adContainer.visibility = View.VISIBLE
            }
            adContainer?.tag = nativeAd
            adContainer?.removeAllViews()
            adContainer?.addView(adBinding.root)
        }

        fun showInterstitialAd(ad: InterstitialAd, activity: Activity, callback: (() -> Unit)? = null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    AdManager.onAdClick()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    callback?.invoke()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    callback?.invoke()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                }
            }
            ad.show(activity)
        }

        fun showOpenAd(ad: AppOpenAd, activity: Activity, callback: (() -> Unit)? = null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    AdManager.onAdClick()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    callback?.invoke()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    callback?.invoke()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                }
            }
            ad.show(activity)
        }

        suspend fun loadNative(adId: AdPositionId): AdData {
            return suspendCancellableCoroutine { callback ->
                AdLoader.Builder(App.INSTANCE, adId.id).forNativeAd { nativeAd ->
                    callback.resume(AdData(adId, nativeAd, true, null))
                }.withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        callback.resume(AdData(adId, null, false, adError.message))
                    }

                    override fun onAdClicked() {
                        super.onAdClicked()
                        AdManager.onAdClick()
                    }
                }).build().loadAd(AdRequest.Builder().build())
            }
        }

        suspend fun loadInter(adId: AdPositionId): AdData {
            return suspendCancellableCoroutine { callback ->
                InterstitialAd.load(App.INSTANCE, adId.id, AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        super.onAdLoaded(interstitialAd)
                        callback.resume(AdData(adId, interstitialAd, true, null))
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        callback.resume(AdData(adId, null, false, adError.message))
                    }
                })
            }
        }

        suspend fun loadOpen(adId: AdPositionId): AdData {
            return suspendCancellableCoroutine { callback ->
                val request = AdRequest.Builder().build()
                AppOpenAd.load(
                    App.INSTANCE, adId.id, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    object : AppOpenAd.AppOpenAdLoadCallback() {
                        override fun onAdLoaded(ad: AppOpenAd) {
                            callback.resume(AdData(adId, ad, true, null))
                        }

                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            callback.resume(AdData(adId, null, false, adError.message))
                        }
                    })
            }
        }
    }
}