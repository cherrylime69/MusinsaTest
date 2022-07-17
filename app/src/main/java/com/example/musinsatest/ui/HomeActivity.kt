package com.example.musinsatest.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.musinsatest.R
import com.example.musinsatest.databinding.ActivityHomeBinding
import com.example.musinsatest.ui.common.ViewType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter
    private var isGridFooterClicked = false
    private var isStyleFooterClicked = false
    private var isScrollFooterClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeAdapter = HomeAdapter(
            clickListener = { url -> setOnClickListenerToMoveToGivenUrl(url) },
            footerClickListener = { type -> setOnFooterClickListener(type) }
        )
        binding.rvHome.adapter = homeAdapter


        viewModel.bannerViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                homeAdapter.submitList(it)
            }
        }
        viewModel.gridViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                if (!isGridFooterClicked) {
                    homeAdapter.submitList(it)
                    isGridFooterClicked = true
                } else {
                    homeAdapter.submitListAfterFooterClick(it)
                }
            }
        }

        viewModel.scrollViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                if (!isScrollFooterClicked) {
                    homeAdapter.submitList(it)
                    isScrollFooterClicked = true
                } else {
                    homeAdapter.submitListAfterFooterClick(it)
                }
            }
        }

        viewModel.styleViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                if (!isStyleFooterClicked) {
                    homeAdapter.submitList(it)
                    isStyleFooterClicked = true
                } else {
                    homeAdapter.submitListAfterFooterClick(it)
                }
            }
        }
    }

    private fun setOnClickListenerToMoveToGivenUrl(url: String) {
        if (url.isNotBlank() || url.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun setOnFooterClickListener(type: String) {
        when (type) {
            ViewType.CONTENT_TYPE_STYLE -> viewModel.addMoreStyleItems()
            ViewType.CONTENT_TYPE_GRID -> viewModel.addMoreGoodsItems()
            else -> viewModel.refreshScrollItems()
        }
    }

}