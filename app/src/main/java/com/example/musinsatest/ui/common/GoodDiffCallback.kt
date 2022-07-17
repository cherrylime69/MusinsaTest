package com.example.musinsatest.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.example.musinsatest.data.dto.Good

object GoodDiffCallback : DiffUtil.ItemCallback<Good>() {
    override fun areItemsTheSame(oldItem: Good, newItem: Good): Boolean {
        return oldItem.linkURL == newItem.linkURL
    }

    override fun areContentsTheSame(oldItem: Good, newItem: Good): Boolean {
        return oldItem == newItem
    }
}