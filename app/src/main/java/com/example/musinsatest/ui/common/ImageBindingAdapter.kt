package com.example.musinsatest.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.musinsatest.GlideApp

@BindingAdapter("loadImageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view)
            .load(imageUrl)
            .into(view)
    }
}