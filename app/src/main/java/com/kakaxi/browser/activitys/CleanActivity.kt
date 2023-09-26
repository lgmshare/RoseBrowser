package com.kakaxi.browser.activitys

import android.os.Bundle
import android.webkit.CookieManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kakaxi.browser.R
import com.kakaxi.browser.WebTabManager
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivityCleanBinding
import com.kakaxi.browser.databinding.ActivitySplashBinding
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

        job = lifecycleScope.launch {
            kotlin.runCatching {
                withTimeoutOrNull(10000) {
                    launch {
                        CookieManager.getInstance().removeAllCookies {
                        }
                        WebTabManager.cleanAllWeb()
                    }

                    launch {
                        delay(3000)
                        toast(R.string.clean_successfully)
                        FirebaseEventUtil.event("rose_clean_toast", Bundle().apply {
                            putLong("bro", 3)
                        })
                    }
                    launch {
                        delay(5000)
                    }
                }
            }.onSuccess {
                FirebaseEventUtil.event("rose_clean_end")
                finish()
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