package com.kakaxi.browser.dialogs

import com.kakaxi.browser.app.BaseDialogFragment
import com.kakaxi.browser.databinding.DialogSettingBinding

class SettingDialogFragment : BaseDialogFragment<DialogSettingBinding>() {

    override fun onBindViewBinding(): DialogSettingBinding {
        return DialogSettingBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }
}