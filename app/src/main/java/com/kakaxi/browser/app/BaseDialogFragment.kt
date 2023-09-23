package com.kakaxi.browser.app

import android.app.ActionBar
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.kakaxi.browser.R

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    lateinit var binding: VB

    protected abstract fun onBindViewBinding(): VB

    protected abstract fun onBuildGravity(): Int

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = onBindViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(true)

        val window = dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transparent)))
        }
        val displayMetrics = DisplayMetrics()
        val windowManager: WindowManager? = activity?.windowManager
        val defaultDisplay: Display? = windowManager?.defaultDisplay
        if (activity != null && windowManager != null && defaultDisplay != null) {
            defaultDisplay.getMetrics(displayMetrics)
        }
        val attributes = window?.attributes
        if (attributes != null) {
            attributes.gravity = onBuildGravity()
            attributes.width = ActionBar.LayoutParams.MATCH_PARENT
            attributes.height = ActionBar.LayoutParams.WRAP_CONTENT
        }
        if (window != null) {
            window.attributes = attributes
        }
    }


    fun show(fragmentManager: FragmentManager, tag: String, isCancelable: Boolean = true) {
        super.show(fragmentManager, tag)
        setCancelable(isCancelable)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}