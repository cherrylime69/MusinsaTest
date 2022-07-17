package com.example.musinsatest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsatest.data.dto.Style
import com.example.musinsatest.databinding.ItemStyleBinding

class StyleAdapter(private val clickListener: (url: String) -> Unit) :
    ListAdapter<Style, StyleAdapter.ViewHolder>(StyleDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StyleAdapter.ViewHolder {
        val binding = ItemStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StyleAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemStyleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(style: Style) {
            binding.style = style
            setOnItemClickListener(style.linkURL)
        }

        private fun setOnItemClickListener(url: String) {
            binding.clStyle.setOnClickListener {
                clickListener(url)
            }
        }
    }

    companion object StyleDiffCallback : DiffUtil.ItemCallback<Style>() {
        override fun areItemsTheSame(oldItem: Style, newItem: Style): Boolean {
            return oldItem.linkURL == newItem.linkURL
        }

        override fun areContentsTheSame(oldItem: Style, newItem: Style): Boolean {
            return oldItem == newItem
        }

    }
}