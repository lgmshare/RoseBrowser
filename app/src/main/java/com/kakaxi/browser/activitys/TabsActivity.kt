package com.kakaxi.browser.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivityTabsBinding

class TabsActivity : BaseActivity() {

    private lateinit var binding: ActivityTabsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }


}