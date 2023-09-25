package com.kakaxi.browser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kakaxi.browser.R
import com.kakaxi.browser.databinding.ItemLinkBinding
import com.kakaxi.browser.extensions.setOnBusyClickListener
import com.kakaxi.browser.models.WebLink

class LinkAdapter(private val context: Context, private val itemClickListener: ((WebLink, Int) -> Unit)? = null) :
    RecyclerView.Adapter<LinkAdapter.ItemViewHolder>() {

    var dataList: ArrayList<WebLink> = arrayListOf()


    inner class ItemViewHolder(val binding: ItemLinkBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemLinkBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]
        holder.binding.linkLogo.setImageResource(item.icon)
        holder.binding.linkName.text = item.name
        holder.itemView.setOnBusyClickListener {
            itemClickListener?.invoke(item, position)
        }
    }
}