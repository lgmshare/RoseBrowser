package com.kakaxi.browser.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivityPrivacyBinding
import com.kakaxi.browser.databinding.ActivitySplashBinding

class PrivacyActivity : BaseActivity() {

    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }


}