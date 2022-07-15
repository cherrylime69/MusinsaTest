package com.example.musinsatest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsatest.data.api.MusinsaNetwork
import com.example.musinsatest.data.remote.MusinsaRemoteDataSource
import com.example.musinsatest.data.repository.HomeRepository
import com.example.musinsatest.data.repository.HomeRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    init {
        getMusinsaData()
    }

    private fun getMusinsaData() {
        val homeRepository = HomeRepositoryImpl(MusinsaRemoteDataSource(MusinsaNetwork.create()))

        viewModelScope.launch {
            homeRepository.getMusinsaData().body()
        }
    }
}
