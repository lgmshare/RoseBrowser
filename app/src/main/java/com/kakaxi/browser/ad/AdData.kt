package com.kakaxi.browser.ad

data class AdData(
    val adid: AdPositionId,
    val data: Any? = null,
    val success: Boolean = false,
    val msg: String? = null,
) {
    var loadIp: String? = null
    val cacheTime = System.currentTimeMillis()
    override fun toString(): String {
        return "AdData(adid=$adid, success=$success, msg=$msg, cacheTime=$cacheTime)"
    }


}
