package com.example.musinsatest.domain

import com.example.musinsatest.data.dto.Contents
import com.example.musinsatest.data.dto.Footer
import com.example.musinsatest.data.dto.Header
import com.example.musinsatest.data.dto.MusinsaData
import com.example.musinsatest.ui.common.ViewType


sealed class MusinsaDataType {
    data class HeaderType(val data: Header) : MusinsaDataType()
    data class ContentType(val data: Contents) : MusinsaDataType()
    data class FooterType(val data: Footer) : MusinsaDataType()
}

fun MusinsaData.divideDataType(): List<MusinsaDataType> {
    return listOf(
        MusinsaDataType.HeaderType(this.header),
        MusinsaDataType.ContentType(this.contents),
        MusinsaDataType.FooterType(this.footer))
}

