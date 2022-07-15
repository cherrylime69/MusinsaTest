package com.example.musinsatest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsatest.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    init {
        getMusinsaData()
    }

    private fun getMusinsaData() {
        viewModelScope.launch {
            homeRepository.getMusinsaData().body()
        }
    }
}
