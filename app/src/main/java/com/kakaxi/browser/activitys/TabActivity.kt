package com.kakaxi.browser.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakaxi.browser.databinding.ActivityTabBinding
import com.kakaxi.browser.dialogs.SettingDialogFragment

class TabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.run {
            btnSetting.setOnClickListener {
                val settingDialog = SettingDialogFragment()
                settingDialog.show(supportFragmentManager, "settingDialog")
            }
        }


    }


}