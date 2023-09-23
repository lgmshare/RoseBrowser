package com.kakaxi.browser.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivityCleanBinding
import com.kakaxi.browser.databinding.ActivitySplashBinding

class CleanActivity : BaseActivity() {

    private lateinit var binding: ActivityCleanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCleanBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }


}