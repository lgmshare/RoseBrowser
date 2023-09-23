package com.kakaxi.browser.dialogs

import android.view.Gravity
import com.kakaxi.browser.app.BaseDialogFragment
import com.kakaxi.browser.databinding.DialogSettingBinding
import com.kakaxi.browser.extensions.setOnBusyClickListener

class SettingDialogFragment : BaseDialogFragment<DialogSettingBinding>() {

    override fun onBindViewBinding(): DialogSettingBinding {
        return DialogSettingBinding.inflate(layoutInflater)
    }

    override fun onBuildGravity(): Int {
        return Gravity.BOTTOM
    }


    override fun initView() {

        binding.run {
            btnNew.setOnBusyClickListener {
                dismiss()
            }
            btnShare.setOnBusyClickListener {
                dismiss()
            }
            btnCopy.setOnBusyClickListener {
                dismiss()
            }
            btnRateUs.setOnBusyClickListener {
                dismiss()
            }
            btnPrivacyPolicy.setOnBusyClickListener {
                dismiss()
            }
        }
    }
}