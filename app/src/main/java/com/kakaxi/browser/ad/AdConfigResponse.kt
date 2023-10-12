package com.kakaxi.browser.ad

data class AdConfigResponse(
    val max_click_num: Int = 0,
    val max_show_num: Int = 0,
    val v_open: AdPositionBean? = null,
    val v_native: AdPositionBean? = null,
    val v_inter: AdPositionBean? = null,
)

data class AdPositionBean(
    val ids: List<AdPositionId> = arrayListOf(),
    val type: String,
)

data class AdPositionId(
    val id: String,
    val platform: String,
    val sort: Int,
)
