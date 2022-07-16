package com.example.musinsatest.domain

import com.example.musinsatest.data.dto.Banner
import com.example.musinsatest.data.dto.Contents
import com.example.musinsatest.data.dto.Good
import com.example.musinsatest.data.dto.Style
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_BANNER
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_ERROR
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_GRID
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_SCROLL
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_STYLE


sealed class MusinsaContentType(val contentType: String) {
    data class BannerContentType(val type: String = CONTENT_TYPE_BANNER, val data: MutableList<Banner>): MusinsaContentType(type)
    data class GridContentType(val type: String = CONTENT_TYPE_GRID, val data: MutableList<Good>): MusinsaContentType(type)
    data class ScrollContentType(val type: String = CONTENT_TYPE_SCROLL, val data: MutableList<Good>): MusinsaContentType(type)
    data class StyleContentType(val type: String = CONTENT_TYPE_STYLE, val data: MutableList<Style>): MusinsaContentType(type)
    data class ErrorContentType(val type: String = CONTENT_TYPE_ERROR, val error: Throwable = Throwable("콘텐츠가 없거나 잘못된 접근입니다")): MusinsaContentType(type)
}

fun Contents.divideContentType(): MusinsaContentType {
    return when (this.type) {
        CONTENT_TYPE_BANNER -> MusinsaContentType.BannerContentType(data = this.banners.toMutableList())
        CONTENT_TYPE_GRID-> MusinsaContentType.GridContentType(data = this.goods.toMutableList())
        CONTENT_TYPE_SCROLL -> MusinsaContentType.ScrollContentType(data = this.goods.toMutableList())
        CONTENT_TYPE_STYLE -> MusinsaContentType.StyleContentType(data = this.styles.toMutableList())
        else -> MusinsaContentType.ErrorContentType()
    }
}
