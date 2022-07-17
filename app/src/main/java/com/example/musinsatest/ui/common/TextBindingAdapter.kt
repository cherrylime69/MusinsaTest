package com.example.musinsatest.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.musinsatest.R
import java.text.DecimalFormat

@BindingAdapter("updatePrice")
fun updateGoodsPrice(view: TextView, price: Int) {
    val decimalFormat = DecimalFormat("#,###")
    val goodsPrice = view.context.getString(R.string.goods_price, decimalFormat.format(price))
    view.text = goodsPrice
}

@BindingAdapter("updateSaleRate")
fun updateSaleRate(view: TextView, saleRate: Int) {
    view.text = view.context.getString(R.string.goods_sale_rate, saleRate)
}

@BindingAdapter("currentPage", "totalPage")
fun updatePageIndex(view: TextView, currentIndex: Int, totalIndex: Int) {
    val index = view.context.getString(R.string.banner_page_index, currentIndex + 1, totalIndex)
    view.text = index
}
