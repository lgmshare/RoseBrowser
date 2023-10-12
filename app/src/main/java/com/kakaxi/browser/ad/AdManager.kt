package com.kakaxi.browser.ad

import com.kakaxi.browser.DataStore
import com.kakaxi.browser.utils.FirebaseRemoteConfigUtil
import com.kakaxi.browser.utils.Utils

object AdManager {

    private var adConfigBean: AdConfigResponse? = null

    private var adMaxDisplayNum = 30
    private var adMaxClickNum = 10

    private var adDisplayNum = 0
    private var adClickNum = 0

    fun init() {
        adConfigBean = FirebaseRemoteConfigUtil.getAdConfig()
        adConfigBean?.let {
            adMaxDisplayNum = it.max_show_num
            adMaxClickNum = it.max_click_num
        }

        setupLimitCount()
    }

    private fun setupLimitCount() {
        if (DataStore.isAdToday()) {
            adDisplayNum = DataStore.showCount
            adClickNum = DataStore.clickCount
        } else {
            adDisplayNum = 0
            adClickNum = 0
            DataStore.showCount = 0
            DataStore.clickCount = 0
        }
    }

    fun preload() {
        setupLimitCount()
        AdPosition.OPEN.load()
        AdPosition.NATIVE.load()
        AdPosition.INTER.load()
    }

    fun getPositionConfig(adPosition: AdPosition): AdPositionBean? {
        if (adConfigBean == null) return null

        return when (adPosition) {
            AdPosition.OPEN -> adConfigBean?.v_open
            AdPosition.NATIVE -> adConfigBean?.v_native
            AdPosition.INTER -> adConfigBean?.v_inter
        }
    }

    fun isLimit(position: AdPosition): Boolean {
        if (adDisplayNum >= adMaxDisplayNum || adClickNum >= adMaxClickNum) {
            Utils.log("$position 广告每日展示或点击次数已到上限 $adDisplayNum:$adMaxDisplayNum 点击次数 $adClickNum:$adMaxClickNum")
            return true
        }

        return false
    }

    fun onAdShow() {
        adDisplayNum++
        DataStore.showCount = adDisplayNum
        Utils.log("广告展示次数 $adDisplayNum:$adMaxDisplayNum 点击次数 $adClickNum:$adMaxClickNum")
    }

    fun onAdClick() {
        adClickNum++
        DataStore.clickCount = adClickNum
        Utils.log("广告展示次数 $adDisplayNum:$adMaxDisplayNum 点击次数 $adClickNum:$adMaxClickNum")
    }
}