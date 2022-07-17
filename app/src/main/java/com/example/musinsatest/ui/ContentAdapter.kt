package com.example.musinsatest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsatest.data.dto.Contents
import com.example.musinsatest.data.dto.Good
import com.example.musinsatest.data.dto.Style
import com.example.musinsatest.databinding.ItemGoodsBinding
import com.example.musinsatest.databinding.ItemStyleBinding
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_GRID
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_SCROLL
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_STYLE

class ContentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var type = ""
    private val goodsList = mutableListOf<Good>()
    private val styleList = mutableListOf<Style>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            CONTENT_TYPE_GRID -> {
                val binding =
                    ItemGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolder(binding)
            }
            CONTENT_TYPE_SCROLL -> {
                val binding =
                    ItemGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ScrollViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                StyleViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GridViewHolder -> holder.bind(goodsList[position])
            is ScrollViewHolder -> holder.bind(goodsList[position])
            is StyleViewHolder -> holder.bind(styleList[position])
        }
    }

    override fun getItemCount(): Int {
        return when (type) {
            CONTENT_TYPE_GRID -> goodsList.size
            CONTENT_TYPE_SCROLL -> goodsList.size
            else -> styleList.size
        }
    }

    class GridViewHolder(private val binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(grid: Good) {
            binding.goods = grid
        }

    }

    class ScrollViewHolder(private val binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(scroll: Good) {
            binding.goods = scroll
        }

    }

    class StyleViewHolder(private val binding: ItemStyleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(style: Style) {
            binding.style = style
        }
    }

    fun submitList(contents: Contents) {
        type = contents.type
        when (type) {
            CONTENT_TYPE_GRID -> goodsList.addAll(contents.goods)
            CONTENT_TYPE_SCROLL -> goodsList.addAll(contents.goods)
            CONTENT_TYPE_STYLE -> styleList.addAll(contents.styles)
        }
    }

}