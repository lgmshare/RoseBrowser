package com.kakaxi.browser.activitys

import android.os.Bundle
import android.webkit.CookieManager
import androidx.lifecycle.lifecycleScope
import com.kakaxi.browser.R
import com.kakaxi.browser.WebTabManager
import com.kakaxi.browser.ad.AdManager
import com.kakaxi.browser.ad.AdPosition
import com.kakaxi.browser.ad.AdPositionPage
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivityCleanBinding
import com.kakaxi.browser.extensions.toast
import com.kakaxi.browser.utils.FirebaseEventUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class CleanActivity : BaseActivity() {

    private lateinit var binding: ActivityCleanBinding

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCleanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AdManager.preload()

        job = lifecycleScope.launch {
            kotlin.runCatching {
                withTimeoutOrNull(14000) {
                    launch {
                        CookieManager.getInstance().removeAllCookies {
                        }
                        WebTabManager.cleanAllWeb()
                    }

                    launch {
                        AdPosition.INTER.load()?.join()
                    }

                    launch {
                        delay(2800)
                    }
                }
            }.onSuccess {
                toast(R.string.clean_successfully)
                FirebaseEventUtil.event("rose_clean_toast", Bundle().apply {
                    putLong("bro", 3)
                })
                AdPositionPage.CLEAN.show(this@CleanActivity) {
                    FirebaseEventUtil.event("rose_clean_end")
                    finish()
                }
            }.onFailure {
                finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    override fun onBackPressed() {
    }

}