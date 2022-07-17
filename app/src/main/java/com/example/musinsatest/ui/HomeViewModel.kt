package com.example.musinsatest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsatest.data.dto.Banner
import com.example.musinsatest.data.dto.Good
import com.example.musinsatest.data.dto.MusinsaDataResponse
import com.example.musinsatest.data.dto.Style
import com.example.musinsatest.data.repository.HomeRepository
import com.example.musinsatest.domain.MusinsaDataType
import com.example.musinsatest.domain.divideDataType
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_BANNER
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_GRID
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_SCROLL
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_STYLE
import com.example.musinsatest.ui.common.ViewType.DATA_TYPE_CONTENT
import com.example.musinsatest.ui.common.ViewType.DATA_TYPE_FOOTER
import com.example.musinsatest.ui.common.ViewType.DATA_TYPE_HEADER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val bannerContentsList = mutableListOf<Banner>()
    private val _bannerViewLiveData = MutableLiveData<List<MusinsaDataType>>()
    val bannerViewLiveData: LiveData<List<MusinsaDataType>> = _bannerViewLiveData

    private val gridContentsList = mutableListOf<Good>()
    private val _gridViewLiveData = MutableLiveData<List<MusinsaDataType>>()
    val gridViewLiveData: LiveData<List<MusinsaDataType>> = _gridViewLiveData

    private val scrollContentsList = mutableListOf<Good>()
    private val _scrollViewLiveData = MutableLiveData<List<MusinsaDataType>>()
    val scrollViewLiveData: LiveData<List<MusinsaDataType>> = _scrollViewLiveData

    private val styleContentsList = mutableListOf<Style>()
    private val _styleViewLiveData = MutableLiveData<List<MusinsaDataType>>()
    val styleViewLiveData: LiveData<List<MusinsaDataType>> = _styleViewLiveData

    init {
        getMusinsaData()
    }

    private fun getMusinsaData() {
        viewModelScope.launch {
            val musinsaDataResponseBody = homeRepository.getMusinsaData().body()
            musinsaDataResponseBody?.let {
                divideMusinsaData(it)
            }
        }
    }

    private fun divideMusinsaData(response: MusinsaDataResponse) {
        response.musinsaDataList.forEach { musinsaData ->
            when (musinsaData.contents.type) {
                CONTENT_TYPE_BANNER -> {
                    bannerContentsList.addAll(musinsaData.contents.banners)
                    musinsaData.divideDataType()
                        .setMusinsaDataLiveData(_bannerViewLiveData)
                }
                CONTENT_TYPE_GRID -> {
                    gridContentsList.addAll(musinsaData.contents.goods)
                    musinsaData.divideDataType()
                        .setMusinsaDataLiveData(_gridViewLiveData)
                }
                CONTENT_TYPE_SCROLL -> {
                    scrollContentsList.addAll(musinsaData.contents.goods)
                    musinsaData.divideDataType()
                        .setMusinsaDataLiveData(_scrollViewLiveData)
                }
                CONTENT_TYPE_STYLE -> {
                    styleContentsList.addAll(musinsaData.contents.styles)
                    musinsaData.divideDataType()
                        .setMusinsaDataLiveData(_styleViewLiveData)
                }
            }
        }
    }

    private fun List<MusinsaDataType>.setMusinsaDataLiveData(
        musinsaDataLiveData: MutableLiveData<List<MusinsaDataType>>
    ) {
        val header = this[DATA_TYPE_HEADER]
        val footer = this[DATA_TYPE_FOOTER]
        val contents = this[DATA_TYPE_CONTENT]
        musinsaDataLiveData.value = listOf(header, contents, footer)
    }
}
