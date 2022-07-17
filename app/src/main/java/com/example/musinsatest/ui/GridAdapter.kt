package com.example.musinsatest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsatest.R
import com.example.musinsatest.data.dto.Good
import com.example.musinsatest.databinding.ItemGoodsBinding
import com.example.musinsatest.ui.common.GoodDiffCallback

class GridAdapter(private val clickListener: (url: String) -> Unit) :
    ListAdapter<Good, GridAdapter.ViewHolder>(
        GoodDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter.ViewHolder {
        val binding = ItemGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(grid: Good) {
            binding.goods = grid
            setGridGoodsStyle()
            setOnItemClickListener(grid.linkURL)
        }

        private fun setGridGoodsStyle() {
            binding.tvGoodsBrand.setTextAppearance(R.style.Subtitle2_Grey03)
            binding.tvGoodsPrice.setTextAppearance(R.style.Caption_Bold)
            binding.tvGoodsSaleRate.setTextAppearance(R.style.Caption_Red)
            binding.tvGoodsCoupon.setTextAppearance(R.style.Overline_White)
        }

        private fun setOnItemClickListener(url: String) {
            binding.clGoods.setOnClickListener {
                clickListener(url)
            }
        }
    }
}