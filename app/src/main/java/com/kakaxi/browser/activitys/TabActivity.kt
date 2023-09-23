package com.kakaxi.browser.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivityTabBinding
import com.kakaxi.browser.dialogs.CleanDialogFragment
import com.kakaxi.browser.dialogs.SettingDialogFragment
import com.kakaxi.browser.extensions.setOnBusyClickListener

class TabActivity : BaseActivity() {

    private lateinit var binding: ActivityTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {

            btnBackward.setOnBusyClickListener {
            }

            btnForward.setOnBusyClickListener {
            }

            btnClean.setOnBusyClickListener {
                val cleanDialog = CleanDialogFragment()
                cleanDialog.show(supportFragmentManager, "cleanDialog")
            }

            btnCount.setOnBusyClickListener {
            }

            btnSetting.setOnBusyClickListener {
                val settingDialog = SettingDialogFragment()
                settingDialog.show(supportFragmentManager, "settingDialog")
            }
        }
    }





}