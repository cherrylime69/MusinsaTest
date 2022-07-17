package com.example.musinsatest.domain

import com.example.musinsatest.data.dto.Contents
import com.example.musinsatest.data.dto.Footer
import com.example.musinsatest.data.dto.Header
import com.example.musinsatest.data.dto.MusinsaData
import com.example.musinsatest.ui.common.ViewType


sealed class MusinsaDataType(val dataType: Int) {
    data class HeaderType(val type: Int = ViewType.DATA_TYPE_HEADER, val data: Header) : MusinsaDataType(type)
    data class ContentType(val type: Int = ViewType.DATA_TYPE_CONTENT, val data: Contents) : MusinsaDataType(type)
    data class FooterType(val type: Int = ViewType.DATA_TYPE_FOOTER, val data: Footer) : MusinsaDataType(type)
}

fun MusinsaData.divideDataType(): List<MusinsaDataType> {
    return listOf(
        MusinsaDataType.HeaderType(data = this.header),
        MusinsaDataType.ContentType(data = this.contents),
        MusinsaDataType.FooterType(data = this.footer))
}

