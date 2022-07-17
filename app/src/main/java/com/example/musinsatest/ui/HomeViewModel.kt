package com.example.musinsatest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsatest.data.dto.*
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
import com.example.musinsatest.ui.common.ViewType.FOOTER_TYPE_MORE
import com.example.musinsatest.ui.common.ViewType.GRID_DEFAULT_COLUMN
import com.example.musinsatest.ui.common.ViewType.GRID_DEFAULT_ROW
import com.example.musinsatest.ui.common.ViewType.STYLE_DEFAULT_COLUMN
import com.example.musinsatest.ui.common.ViewType.STYLE_DEFAULT_ROW
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

        if (footer is MusinsaDataType.FooterType && contents is MusinsaDataType.ContentType) {
            if (footer.data.type == FOOTER_TYPE_MORE) {
                when (contents.data.type) {
                    CONTENT_TYPE_GRID -> {
                        val newContents = MusinsaDataType.ContentType(
                            Contents(
                                type = CONTENT_TYPE_GRID,
                                goods = addGoodsItems()
                            )
                        )
                        musinsaDataLiveData.value = listOf(header, newContents, footer)
                    }
                    CONTENT_TYPE_STYLE -> {
                        val newContents = MusinsaDataType.ContentType(
                            Contents(
                                type = CONTENT_TYPE_STYLE,
                                styles = addStyleItems()
                            )
                        )
                        musinsaDataLiveData.value = listOf(header, newContents, footer)
                    }
                }
            } else {
                musinsaDataLiveData.value = listOf(header, contents, footer)
            }
        }
    }

    private fun addGoodsItems(): MutableList<Good> {
        val newGoodsItems = mutableListOf<Good>()

        repeat(GRID_DEFAULT_COLUMN * GRID_DEFAULT_ROW) {
            if (gridContentsList.isNotEmpty()) {
                newGoodsItems.add(gridContentsList.removeFirst())
            }
        }

        return newGoodsItems
    }

    private fun addStyleItems(): MutableList<Style> {
        val newStyleItems = mutableListOf<Style>()

        repeat(STYLE_DEFAULT_COLUMN * STYLE_DEFAULT_ROW) {
            if (styleContentsList.isNotEmpty()) {
                newStyleItems.add(styleContentsList.removeFirst())
            }
        }

        return newStyleItems
    }

    fun addMoreGoodsItems() {
        if (gridContentsList.isEmpty()) return
        val header = _gridViewLiveData.value?.get(DATA_TYPE_HEADER) ?: MusinsaDataType.HeaderType(Header())
        val footer = _gridViewLiveData.value?.get(DATA_TYPE_FOOTER) ?: MusinsaDataType.FooterType(Footer(), "")
        val contents = _gridViewLiveData.value?.get(DATA_TYPE_CONTENT) ?: MusinsaDataType.ContentType(Contents())
        val newGoodsItems = mutableListOf<Good>()

        if (contents is MusinsaDataType.ContentType) {
            val previousGoodsItems = contents.data.goods
            newGoodsItems.addAll(previousGoodsItems)
        }

        repeat(GRID_DEFAULT_COLUMN) {
            println(it)
            if (gridContentsList.isNotEmpty()) {
                newGoodsItems.add(gridContentsList.removeFirst())
            }
        }
        val newContents = MusinsaDataType.ContentType(
            Contents(
                type = CONTENT_TYPE_GRID,
                goods = newGoodsItems
            )
        )
        _gridViewLiveData.value = listOf(header, newContents, footer)
    }

    fun addMoreStyleItems() {
        if (styleContentsList.isEmpty()) return
        val header = _styleViewLiveData.value?.get(DATA_TYPE_HEADER) ?: MusinsaDataType.HeaderType(Header())
        val footer = _styleViewLiveData.value?.get(DATA_TYPE_FOOTER) ?: MusinsaDataType.FooterType(Footer(), "")
        val contents = _styleViewLiveData.value?.get(DATA_TYPE_CONTENT) ?: MusinsaDataType.ContentType(Contents())
        val newStyleItems = mutableListOf<Style>()

        if (contents is MusinsaDataType.ContentType) {
            val previousStyleItems = contents.data.styles
            newStyleItems.addAll(previousStyleItems)
        }

        repeat(STYLE_DEFAULT_COLUMN) {
            if (styleContentsList.isNotEmpty()) {
                newStyleItems.add(styleContentsList.removeFirst())
            }
        }
        val newContents = MusinsaDataType.ContentType(
            Contents(
                type = CONTENT_TYPE_STYLE,
                styles = newStyleItems
            )
        )
        _styleViewLiveData.value = listOf(header, newContents, footer)
    }

    fun refreshScrollItems() {
        val header = _scrollViewLiveData.value?.get(DATA_TYPE_HEADER) ?: MusinsaDataType.HeaderType(Header())
        val footer = _scrollViewLiveData.value?.get(DATA_TYPE_FOOTER) ?: MusinsaDataType.FooterType(Footer(), "")
        val contents = _scrollViewLiveData.value?.get(DATA_TYPE_CONTENT) ?: MusinsaDataType.ContentType(Contents())
        val shuffledContents = mutableListOf<Good>()

        if (contents is MusinsaDataType.ContentType) {
            val previousScrollItems = contents.data.goods
            shuffledContents.addAll(previousScrollItems.shuffled())
        }

        val newContents = MusinsaDataType.ContentType(
            Contents(
                type = CONTENT_TYPE_SCROLL,
                goods = shuffledContents
            )
        )
        _scrollViewLiveData.value = listOf(header, newContents, footer)
    }

}


