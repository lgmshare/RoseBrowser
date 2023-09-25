package com.kakaxi.browser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kakaxi.browser.R
import com.kakaxi.browser.app.App
import com.kakaxi.browser.databinding.ItemTabBinding
import com.kakaxi.browser.extensions.dp2px
import com.kakaxi.browser.extensions.getScreenWidth
import com.kakaxi.browser.extensions.setOnBusyClickListener
import com.kakaxi.browser.models.WebTab

class TabAdapter(
    val context: Context,
    val itemClickListener: ((WebTab, Int) -> Unit)? = null,
    val itemDeleteClickListener: ((WebTab, Int) -> Unit)? = null,
) : RecyclerView.Adapter<TabAdapter.ItemViewHolder>() {

    var dataList: ArrayList<WebTab> = arrayListOf()

    var itemWidth: Int = 0
    var itemHeight: Int = 0

    init {
        itemWidth = (context.getScreenWidth() - App.INSTANCE.dp2px(44f)) / 2
        itemHeight = (itemWidth * 1.3).toInt()
    }

    inner class ItemViewHolder(val binding: ItemTabBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemTabBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]
        val bitmap = item.bitmap
        if (bitmap != null) {
            holder.binding.tabImage.setImageBitmap(bitmap)
        } else {
            holder.binding.tabImage.setImageResource(R.drawable.shape_ffffff_r12)
        }
        holder.itemView.setOnBusyClickListener {
            itemClickListener?.invoke(item, position)
        }

        holder.binding.tabDelete.setOnBusyClickListener {
            itemDeleteClickListener?.invoke(item, position)
        }

        holder.binding.tabDelete.isVisible = itemCount > 1
    }
}