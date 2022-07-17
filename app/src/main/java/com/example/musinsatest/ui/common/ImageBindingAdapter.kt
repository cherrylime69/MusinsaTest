package com.example.musinsatest.ui.common

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.musinsatest.GlideApp

@BindingAdapter("loadImageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view)
            .load(imageUrl)
            .into(view)
    }
}

@BindingAdapter("loadIconImageUrl")
fun loadIconImage(view: TextView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val icon = BitmapDrawable(view.resources, resource)
                    view.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}
