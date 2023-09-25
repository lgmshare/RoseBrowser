package com.kakaxi.browser.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.kakaxi.browser.DataStore
import com.kakaxi.browser.app.AppActivityLifecycleObserver
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivitySplashBinding
import com.kakaxi.browser.utils.FirebaseEventUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (DataStore.isFirstLaunch()) {
            FirebaseEventUtil.event("rose_first")
        }
    }

    override fun onStart() {
        super.onStart()

        if (AppActivityLifecycleObserver.hasMoreActivity()) {
            FirebaseEventUtil.event("rose_hot")
        } else {
            FirebaseEventUtil.event("rose_cold")
        }

        Firebase.analytics.setUserProperty("Rose_pe", Locale.getDefault().country)

        binding.progressCircular.progress = 0
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                binding.progressCircular.let { progressBar ->
                    val progress = progressBar.progress + 1
                    if (progress >= 100) {
                        timer?.cancel()
                    } else {
                        progressBar.progress = progress
                    }
                }
            }
        }, 0, 30) // 定时器每隔 500 毫秒执行一次任务

        lifecycleScope.launch {
            delay(3000)
            if (!AppActivityLifecycleObserver.hasMoreActivity()) {
                startActivity(Intent(this@SplashActivity, TabActivity::class.java))
            }
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    override fun onBackPressed() {

    }

}