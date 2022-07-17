package com.example.musinsatest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.musinsatest.databinding.ItemContentBinding
import com.example.musinsatest.databinding.ItemFooterBinding
import com.example.musinsatest.databinding.ItemHeaderBinding
import com.example.musinsatest.domain.MusinsaDataType
import com.example.musinsatest.ui.common.ViewType
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_BANNER
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_GRID
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_SCROLL
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_STYLE

class HomeAdapter(private val clickListener: (url: String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val musinsaDataList = mutableListOf<MusinsaDataType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.DATA_TYPE_HEADER -> {
                val binding =
                    ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }
            ViewType.DATA_TYPE_CONTENT -> {
                val binding =
                    ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ContentViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FooterViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind(musinsaDataList[position])
            }
            is ContentViewHolder -> {
                holder.bind(musinsaDataList[position])
            }
            is FooterViewHolder -> {
                holder.bind(musinsaDataList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return musinsaDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (musinsaDataList[position]) {
            is MusinsaDataType.HeaderType -> ViewType.DATA_TYPE_HEADER
            is MusinsaDataType.ContentType -> ViewType.DATA_TYPE_CONTENT
            is MusinsaDataType.FooterType -> ViewType.DATA_TYPE_FOOTER
        }
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: MusinsaDataType) {
            if (header is MusinsaDataType.HeaderType) {
                binding.header = header.data
                setOnTotalTextViewClickListener(header.data.linkURL)
            }

        }

        private fun setOnTotalTextViewClickListener(url: String) {
            binding.tvHeaderTotalLink.setOnClickListener {
                clickListener(url)
            }
        }
    }

    class FooterViewHolder(private val binding: ItemFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(footer: MusinsaDataType) {
            if (footer is MusinsaDataType.FooterType) {
                binding.footer = footer.data
            }
        }
    }

    inner class ContentViewHolder(private val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val bannerAdapter = BannerAdapter(clickListener).apply {
            binding.vpBanner.adapter = this
        }
        private val gridAdapter = ContentAdapter(clickListener)
        private val scrollAdapter = ContentAdapter(clickListener)
        private val styleAdapter = ContentAdapter(clickListener)

        fun bind(content: MusinsaDataType) {
            if (content is MusinsaDataType.ContentType) {
                binding.contentType = content.data.type
                when (content.data.type) {
                    CONTENT_TYPE_BANNER -> setBannerContents(content)
                    CONTENT_TYPE_GRID -> setGridContents(content)
                    CONTENT_TYPE_SCROLL -> setScrollContents(content)
                    CONTENT_TYPE_STYLE -> setStyleContents(content)
                }
            }
        }

        private fun setStyleContents(content: MusinsaDataType.ContentType) {
            val layoutManager = GridLayoutManager(binding.root.context, 2)
            binding.rvContent.apply {
                this.adapter = styleAdapter
                this.layoutManager = layoutManager
            }
            styleAdapter.submitList(content.data)
        }

        private fun setScrollContents(content: MusinsaDataType.ContentType) {
            val layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvContent.apply {
                this.adapter = scrollAdapter
                this.layoutManager = layoutManager
            }
            scrollAdapter.submitList(content.data)
        }

        private fun setGridContents(content: MusinsaDataType.ContentType) {
            val layoutManager = GridLayoutManager(binding.root.context, 3)
            binding.rvContent.apply {
                this.adapter = gridAdapter
                this.layoutManager = layoutManager
            }
            gridAdapter.submitList(content.data)
        }

        private fun setBannerContents(content: MusinsaDataType.ContentType) {
            bannerAdapter.submitList(content.data.banners)
            setOnViewPagerChangePageListener(content)
        }

        private fun setOnViewPagerChangePageListener(content: MusinsaDataType.ContentType) {
            binding.totalPageIndex = content.data.banners.size
            binding.vpBanner.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.currentPageIndex = position
                }
            })
        }
    }

    fun submitList(musinsaDataType: List<MusinsaDataType>) {
        val startIndex = musinsaDataList.size
        musinsaDataList.addAll(musinsaDataType)
//        this.notifyDataSetChanged()
        notifyItemRangeChanged(startIndex, musinsaDataList.size)
    }


}