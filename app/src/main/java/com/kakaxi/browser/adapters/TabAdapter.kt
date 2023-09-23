package com.kakaxi.browser.adapters

import com.kakaxi.browser.app.App
import com.kakaxi.browser.extensions.dp2px

class TabAdapter {

    var itemWidth: Int = 0
    var itemHeight: Int = 0

    fun inits() {
        itemWidth = (42942 - App.INSTANCE.dp2px(36f)) / 2
        itemHeight = itemWidth * 2
    }

}