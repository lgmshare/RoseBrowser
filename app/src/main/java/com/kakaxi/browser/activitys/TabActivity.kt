package com.kakaxi.browser.activitys

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kakaxi.browser.BuildConfig
import com.kakaxi.browser.R
import com.kakaxi.browser.WebTabManager
import com.kakaxi.browser.adapters.LinkAdapter
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivityTabBinding
import com.kakaxi.browser.dialogs.CleanDialogFragment
import com.kakaxi.browser.dialogs.SettingDialogFragment
import com.kakaxi.browser.extensions.copyToClipboard
import com.kakaxi.browser.extensions.hideSoftInput
import com.kakaxi.browser.extensions.jumpShare
import com.kakaxi.browser.extensions.setOnBusyClickListener
import com.kakaxi.browser.extensions.toast
import com.kakaxi.browser.models.WebTab
import com.kakaxi.browser.utils.FirebaseEventUtil
import com.kakaxi.browser.utils.Utils
import com.kakaxi.browser.views.MyWebView

class TabActivity : BaseActivity() {

    private lateinit var binding: ActivityTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            root.postDelayed({
                WebTabManager.updateCurrentWebTabBitmap(binding.root.drawToBitmap())
            }, 300)

            searchView.addTextChangedListener {
                val inputText = binding.searchView.text.toString().trim()
                if (inputText.isEmpty()) {
                    btnSearch.isVisible = true
                    btnDelete.isVisible = false
                } else {
                    btnSearch.isVisible = false
                    btnDelete.isVisible = true
                }
            }

            searchView.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    binding.searchView.text = SpannableStringBuilder("")
                    stopLoad()
                }
            }

            searchView.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(textView: TextView, actionId: Int, p2: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        binding.searchView.hideSoftInput()
                        binding.searchView.clearFocus()
                        val inputText = binding.searchView.text.toString().trim()
                        if (inputText.isEmpty()) {
                            toast(R.string.please_enter_content)
                        } else {
                            FirebaseEventUtil.searchEvent(inputText)
                            startLoad(inputText)
                        }
                        return true
                    }
                    return false
                }
            })

            btnDelete.setOnBusyClickListener {
                binding.searchView.text = SpannableStringBuilder("")
            }

            btnBackward.setOnBusyClickListener {
                onBack()
            }

            btnForward.setOnBusyClickListener {
                clickForward()
            }

            btnClean.setOnBusyClickListener {
                FirebaseEventUtil.event("rose_clean")
                val cleanDialog = CleanDialogFragment() {
                    startActivity(Intent(this@TabActivity, CleanActivity::class.java))
                }
                cleanDialog.show(supportFragmentManager, "cleanDialog")
            }

            btnCount.setOnBusyClickListener {
                startActivity(Intent(this@TabActivity, TabsActivity::class.java))
            }

            btnSetting.setOnBusyClickListener {
                val settingDialog = SettingDialogFragment() {
                    when (it) {
                        1 -> {
                            FirebaseEventUtil.event("rose_newTab", Bundle().apply {
                                putString("bro", "setting")
                            })
                            WebTabManager.addNewWeb()
                        }

                        2 -> {
                            FirebaseEventUtil.event("rose_share")
                            val path = if (WebTabManager.currentWebTab.webView.isIdea || WebTabManager.currentWebTab.webView.isStopped) {
                                "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                            } else {
                                WebTabManager.currentWebTab.webView.url ?: ""
                            }
                            jumpShare(path)
                        }

                        3 -> {
                            FirebaseEventUtil.event("rose_copy")
                            val path = if (WebTabManager.currentWebTab.webView.isIdea || WebTabManager.currentWebTab.webView.isStopped) {
                                ""
                            } else {
                                WebTabManager.currentWebTab.webView.url ?: ""
                            }
                            copyToClipboard(path)
                        }

                        4 -> {
                            startActivity(Intent(this@TabActivity, PrivacyActivity::class.java))
                        }

                        5 -> {
                            startActivity(Intent(this@TabActivity, PrivacyActivity::class.java))
                        }
                    }
                }
                settingDialog.show(supportFragmentManager, "settingDialog")
            }
        }

        val adapter = LinkAdapter(this) { item, _ ->
            binding.searchView.hideSoftInput()
            binding.searchView.clearFocus()
            startLoad(item.url)
            FirebaseEventUtil.newLinkEvent(item.name)
        }
        adapter.dataList.clear()
        adapter.dataList.addAll(Utils.getWebLinks())
        binding.navRecyclerView.layoutManager = GridLayoutManager(this, 4)
        binding.navRecyclerView.adapter = adapter

        WebTabManager.webListener = object : WebTabManager.WebListener {
            override fun onProgressChanged(progress: Int, webView: MyWebView) {
                if (WebTabManager.isCurrentWebView(webView) && !webView.isStopped) {
                    binding.progressBar.isVisible = progress < 100
                    binding.progressBar.progress = progress
                    if (progress >= 100) {
                        updateWebViewVisible(true)
                        if (binding.webContainer.childCount >= 1) {
                            val view = binding.webContainer.getChildAt(0)
                            if (view != webView) {
                                binding.webContainer.removeAllViews()
                                (webView.parent as? ViewGroup)?.removeAllViews()
                                binding.webContainer.addView(webView)
                            }
                        } else {
                            binding.webContainer.removeAllViews()
                            (webView.parent as? ViewGroup)?.removeAllViews()
                            binding.webContainer.addView(webView)
                        }
                        updateBottomTools()

                        webView.postDelayed({
                            if (webView.isLaidOut) {
                                WebTabManager.updateCurrentWebTabBitmap(webView.drawToBitmap())
                            }
                        }, 500)
                    }
                }
            }

            override fun onWebChanged(webTab: WebTab) {
                if (webTab.webView.isLoadingFinish) {
                    updateWebViewVisible(true)
                    binding.progressBar.isVisible = false
                    binding.webContainer.removeAllViews()
                    binding.webContainer.addView(webTab.webView)
                } else {
                    binding.searchView.text = SpannableStringBuilder(webTab.inputText ?: "")
                    binding.progressBar.isVisible = webTab.webView.isLoading
                    updateWebViewVisible(false)
                }

                updateBottomTools()
            }

            override fun clean() {
                binding.progressBar.isVisible = false
                binding.progressBar.progress = 0
                binding.searchView.text = SpannableStringBuilder("")
                updateWebViewVisible(false)
                updateBottomTools()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseEventUtil.event("rose_show")
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        updatePageBitmap()
    }

    private fun updateWebViewVisible(isVisible: Boolean) {
        binding.webContainer.isVisible = isVisible
        if (!isVisible) {
            binding.progressBar.isVisible = false
            binding.webContainer.removeAllViews()
        }
    }

    private fun updateBottomTools() {
        if (binding.webContainer.isVisible) {
            binding.btnBackward.isEnabled = true
            binding.btnBackward.setImageResource(R.mipmap.nav_backward)
            if (WebTabManager.currentWebTab.webView.canGoForward()) {
                binding.btnForward.isEnabled = true
                binding.btnForward.setImageResource(R.mipmap.nav_forward)
            } else {
                binding.btnForward.isEnabled = false
                binding.btnForward.setImageResource(R.mipmap.nav_forward_disable)
            }
        } else {
            WebTabManager.currentWebTab.webView.clearWebHistory()
            binding.btnBackward.isEnabled = false
            binding.btnForward.isEnabled = false
            binding.webContainer.removeAllViews()
            binding.btnBackward.setImageResource(R.mipmap.nav_backward_disable)
            binding.btnForward.setImageResource(R.mipmap.nav_forward_disable)
        }

        binding.tvCount.text = "${WebTabManager.getTabCount()}"
    }

    private fun updatePageBitmap() {
        val view = binding.webContainer.getChildAt(0)
        if (view != null) {
            WebTabManager.updateCurrentWebTabBitmap(view.drawToBitmap())
        } else {
            WebTabManager.updateCurrentWebTabBitmap(binding.root.drawToBitmap())
        }
    }

    private fun startLoad(url: String) {
        FirebaseEventUtil.event("rose_newSearch")
        WebTabManager.startLoad(url)
    }

    private fun stopLoad() {
        WebTabManager.stopLoad()
        updateWebViewVisible(false)
    }

    private fun onBack() {
        if (WebTabManager.currentWebTab.webView.canGoBack()) {
            WebTabManager.currentWebTab.webView.goBack()
        } else {
            WebTabManager.currentWebTab.webView.stopLoad()
            updateWebViewVisible(false)
            updateBottomTools()
        }
    }

    private fun clickForward() {
        if (WebTabManager.currentWebTab.webView.canGoForward()) {
            WebTabManager.currentWebTab.webView.goForward()
        } else {
            updateBottomTools()
        }
    }
}