package com.example.musinsatest.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.musinsatest.R
import com.example.musinsatest.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel.bannerViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                println("첫번째 $it")
            }
        }
        viewModel.gridViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                println("두번째 $it")
            }
        }

        viewModel.scrollViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                println("세번째 $it")
            }
        }

        viewModel.styleViewLiveData.observe(this) {
            if (it.isNotEmpty()) {
                println("네번째 $it")
            }
        }
    }

}