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
    view.text = "$saleRate%"
}