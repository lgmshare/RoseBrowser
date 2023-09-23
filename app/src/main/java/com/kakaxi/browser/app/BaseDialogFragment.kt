package com.kakaxi.browser.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    protected lateinit var binding: VB

    protected abstract fun onBindViewBinding(): VB

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

    fun show(fragmentManager: FragmentManager, tag: String, isCancelable: Boolean = false) {
        super.show(fragmentManager, tag)
        setCancelable(isCancelable)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}