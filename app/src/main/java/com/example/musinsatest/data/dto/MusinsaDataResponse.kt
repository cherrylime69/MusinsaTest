package com.example.musinsatest.data.dto


import com.google.gson.annotations.SerializedName

data class MusinsaDataResponse(
    @SerializedName("data")
    val musinsaDataList: List<MusinsaData> = listOf()
)

data class MusinsaData(
    @SerializedName("contents")
    val contents: Contents = Contents(),
    @SerializedName("footer")
    val footer: Footer = Footer(),
    @SerializedName("header")
    val header: Header = Header()
)

data class Header(
    @SerializedName("iconURL")
    val iconURL: String = "",
    @SerializedName("linkURL")
    val linkURL: String = "",
    @SerializedName("title")
    val title: String = ""
)

data class Footer(
    @SerializedName("iconURL")
    val iconURL: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: String = ""
)

data class Contents(
    @SerializedName("banners")
    val banners: List<Banner> = listOf(),
    @SerializedName("goods")
    val goods: List<Good> = listOf(),
    @SerializedName("styles")
    val styles: List<Style> = listOf(),
    @SerializedName("type")
    val type: String = ""
)

data class Banner(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("keyword")
    val keyword: String = "",
    @SerializedName("linkURL")
    val linkURL: String = "",
    @SerializedName("thumbnailURL")
    val thumbnailURL: String = "",
    @SerializedName("title")
    val title: String = ""
)

data class Good(
    @SerializedName("brandName")
    val brandName: String = "",
    @SerializedName("hasCoupon")
    val hasCoupon: Boolean = false,
    @SerializedName("linkURL")
    val linkURL: String = "",
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("saleRate")
    val saleRate: Int = 0,
    @SerializedName("thumbnailURL")
    val thumbnailURL: String = ""
)

data class Style(
    @SerializedName("linkURL")
    val linkURL: String = "",
    @SerializedName("thumbnailURL")
    val thumbnailURL: String = ""
)