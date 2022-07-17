package com.example.musinsatest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsatest.R
import com.example.musinsatest.data.dto.Good
import com.example.musinsatest.databinding.ItemGoodsBinding
import com.example.musinsatest.ui.common.GoodDiffCallback

class ScrollAdapter(private val clickListener: (url: String) -> Unit) :
    ListAdapter<Good, ScrollAdapter.ViewHolder>(GoodDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollAdapter.ViewHolder {
        val binding = ItemGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrollAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scroll: Good) {
            binding.goods = scroll
            setScrollGoodsStyle()
            setOnItemClickListener(scroll.linkURL)
        }

        private fun setScrollGoodsStyle() {
            binding.tvGoodsBrand.setTextAppearance(R.style.HeadLine5_Grey03)
            binding.tvGoodsPrice.setTextAppearance(R.style.HeadLine6)
            binding.tvGoodsSaleRate.setTextAppearance(R.style.HeadLine6_Red)
            binding.tvGoodsCoupon.setTextAppearance(R.style.Subtitle2_White)
        }

        private fun setOnItemClickListener(url: String) {
            binding.clGoods.setOnClickListener {
                clickListener(url)
            }
        }
    }

}