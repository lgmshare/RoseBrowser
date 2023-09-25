package com.kakaxi.browser.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kakaxi.browser.WebTabManager
import com.kakaxi.browser.adapters.TabAdapter
import com.kakaxi.browser.app.BaseActivity
import com.kakaxi.browser.databinding.ActivityTabsBinding
import com.kakaxi.browser.extensions.setOnBusyClickListener
import com.kakaxi.browser.models.WebTab
import com.kakaxi.browser.utils.FirebaseEventUtil

class TabsActivity : BaseActivity() {

    private lateinit var binding: ActivityTabsBinding

    private val adapter by lazy {
        TabAdapter(this, { item, _ ->
            WebTabManager.changeWeb(item)
            finish()
        }, { item, _ ->
            remoteWeb(item)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseEventUtil.event("rose_showTab")
        binding.run {
            btnAdd.setOnBusyClickListener {
                FirebaseEventUtil.event("rose_newTab", Bundle().apply {
                    putString("bro", "tab")
                })
                WebTabManager.addNewWeb()
                finish()
            }

            btnBack.setOnBusyClickListener {
                finish()
            }

        }
        binding.tabsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.tabsRecyclerView.adapter = adapter

        updateData()
    }

    private fun remoteWeb(link: WebTab) {
        WebTabManager.remove(link)
        updateData()
    }

    private fun updateData() {
        val list = WebTabManager.webPageLists.sortedByDescending { it.createTime }
        adapter.dataList.clear()
        adapter.dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }
}