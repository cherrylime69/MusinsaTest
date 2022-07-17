package com.example.musinsatest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsatest.R
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
            setGridGoodsStyle()
        }

        private fun setGridGoodsStyle() {
            binding.tvGoodsBrand.setTextAppearance(R.style.Subtitle2_Grey03)
            binding.tvGoodsPrice.setTextAppearance(R.style.Caption_Bold)
            binding.tvGoodsSaleRate.setTextAppearance(R.style.Caption_Red)
            binding.tvGoodsCoupon.setTextAppearance(R.style.Overline_White)
        }

    }

    class ScrollViewHolder(private val binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(scroll: Good) {
            binding.goods = scroll
            setScrollGoodsStyle()
        }

        private fun setScrollGoodsStyle() {
            binding.tvGoodsBrand.setTextAppearance(R.style.HeadLine5_Grey03)
            binding.tvGoodsPrice.setTextAppearance(R.style.HeadLine6)
            binding.tvGoodsSaleRate.setTextAppearance(R.style.HeadLine6_Red)
            binding.tvGoodsCoupon.setTextAppearance(R.style.Subtitle2_White)
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
            CONTENT_TYPE_GRID, CONTENT_TYPE_SCROLL  -> {
                if (goodsList.isEmpty())
                goodsList.addAll(contents.goods)
            }
            CONTENT_TYPE_STYLE -> {
                if (styleList.isEmpty())
                styleList.addAll(contents.styles)
            }
        }
    }

}