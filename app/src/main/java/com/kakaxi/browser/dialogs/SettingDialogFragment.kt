package com.kakaxi.browser.dialogs

import android.view.Gravity
import com.kakaxi.browser.app.BaseDialogFragment
import com.kakaxi.browser.databinding.DialogSettingBinding
import com.kakaxi.browser.extensions.setOnBusyClickListener
import com.kakaxi.browser.models.WebLink

class SettingDialogFragment(private val itemClickListener: ((Int) -> Unit)? = null) : BaseDialogFragment<DialogSettingBinding>() {

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
                itemClickListener?.invoke(1)
            }
            btnShare.setOnBusyClickListener {
                dismiss()
                itemClickListener?.invoke(2)
            }
            btnCopy.setOnBusyClickListener {
                dismiss()
                itemClickListener?.invoke(3)
            }
            btnRateUs.setOnBusyClickListener {
                dismiss()
                itemClickListener?.invoke(4)
            }
            btnPrivacyPolicy.setOnBusyClickListener {
                dismiss()
                itemClickListener?.invoke(5)
            }
        }
    }
}