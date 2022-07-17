package com.example.musinsatest.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsatest.R
import com.example.musinsatest.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeAdapter = HomeAdapter()
        binding.rvHome.adapter = homeAdapter


        viewModel.bannerViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                homeAdapter.submitList(it)
            }
        }
        viewModel.gridViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                homeAdapter.submitList(it)
            }
        }

        viewModel.scrollViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                homeAdapter.submitList(it)
            }
        }

        viewModel.styleViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                homeAdapter.submitList(it)
            }
        }
    }

}