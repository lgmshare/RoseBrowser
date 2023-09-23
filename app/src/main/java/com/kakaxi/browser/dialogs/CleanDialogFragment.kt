package com.kakaxi.browser.dialogs

import android.view.Gravity
import com.kakaxi.browser.app.BaseDialogFragment
import com.kakaxi.browser.databinding.DialogCleanBinding
import com.kakaxi.browser.extensions.setOnBusyClickListener

class CleanDialogFragment : BaseDialogFragment<DialogCleanBinding>() {

    override fun onBindViewBinding(): DialogCleanBinding {
        return DialogCleanBinding.inflate(layoutInflater)
    }

    override fun onBuildGravity(): Int {
        return Gravity.CENTER
    }

    override fun initView() {
        binding.btnConfirm.setOnBusyClickListener {
            dismiss()
        }
    }
}