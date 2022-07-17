package com.example.musinsatest.ui.common

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.musinsatest.data.dto.Footer
import com.example.musinsatest.data.dto.Header
import com.example.musinsatest.ui.common.ViewType.CONTENT_TYPE_BANNER

@BindingAdapter("isHeaderVisible")
fun updateHeaderVisibility(view: View, header: Header) {
    view.isVisible = header.title.isNotBlank() || header.title.isNotEmpty()
}

@BindingAdapter("isHeaderTotalVisible")
fun updateHeaderTotalTextViewVisibility(view: View, header: Header) {
    view.isVisible = header.linkURL.isNotEmpty()
}

@BindingAdapter("isFooterVisible")
fun updateFooterVisibility(view: View, footer: Footer) {
    view.isVisible = footer.title.isNotBlank() || footer.title.isNotEmpty()
}

@BindingAdapter("isFooterMoreVisible")
fun updateFooterMoreVisibility(view: View, footer: Footer) {
    view.isVisible = footer.type == ViewType.FOOTER_TYPE_MORE
}

@BindingAdapter("isFooterRefreshVisible")
fun updateFooterRefreshVisibility(view: View, footer: Footer) {
    view.isVisible = footer.type == ViewType.FOOTER_TYPE_REFRESH
}

@BindingAdapter("isCouponVisible")
fun updateCouponVisibility(view: View, hasCoupon: Boolean) {
    view.isVisible = hasCoupon
}

@BindingAdapter("isViewPagerVisible")
fun updateViewPagerVisibility(view: View, type: String) {
    view.isVisible = type == CONTENT_TYPE_BANNER
}

@BindingAdapter("isRecyclerViewVisible")
fun updateRecyclerViewVisibility(view: View, type: String) {
    view.isVisible = type != CONTENT_TYPE_BANNER
}