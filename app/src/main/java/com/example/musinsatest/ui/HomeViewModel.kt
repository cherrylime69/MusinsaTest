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

//        if (footer is MusinsaDataType.FooterType && contents is MusinsaDataType.ContentType) {
        /*if (footer is MusinsaDataType.FooterType && contents is MusinsaDataType.ContentType) {
            if (footer.data.type == FOOTER_TYPE_MORE) {
                val newContents = initContentsForMoreFooter(contents.data)
                musinsaDataLiveData.value = listOf(header, newContents, footer)
            } else {*/
        musinsaDataLiveData.value = listOf(header, contents, footer)
//            }
    }
}

/*
    private fun initContentsForMoreFooter(newContents: MutableList<>, contents: Contents): MusinsaDataType.ContentType {
        return when (contents.type) {
            CONTENT_TYPE_GRID -> MusinsaDataType.ContentType(
                DATA_TYPE_CONTENT, Contents(
                    type = contents.type,
                    goods = addGoodsItems()
                )
            )
            CONTENT_TYPE_STYLE -> MusinsaDataType.ContentType(
                DATA_TYPE_CONTENT, Contents(
                    type = contents.type,
                    styles = addStyleItems()
                )
            )
        }
    }
*/


/*private fun initContentsForMoreFooter(contents: MusinsaContentType): MusinsaDataType.ContentType {
    return when (contents) {
        is GridContentType -> MusinsaDataType.ContentType(
            DATA_TYPE_CONTENT, GridContentType(
                type = contents.contentType,
                data = addGoodsItems()
            )
        )
        is StyleContentType -> MusinsaDataType.ContentType(
            DATA_TYPE_CONTENT, StyleContentType(
                type = contents.contentType,
                data = addStyleItems()
            )
        )
        else -> MusinsaDataType.ContentType(DATA_TYPE_CONTENT, ErrorContentType())
    }
}*/

/*private fun addGoodsItems(): MutableList<Good> {
    val newGoodsItems = mutableListOf<Good>()

    repeat(MORE_ADD_COUNT) {
        if (gridContentsList.isNotEmpty()) {
            newGoodsItems.add(gridContentsList.removeFirst())
        }
    }

    return newGoodsItems
}

private fun addStyleItems(): MutableList<Style> {
    val newStyleItems = mutableListOf<Style>()

    repeat(MORE_ADD_COUNT) {
        if (styleContentsList.isNotEmpty()) {
            newStyleItems.add(styleContentsList.removeFirst())
        }
    }

    return newStyleItems
}*/

//}
