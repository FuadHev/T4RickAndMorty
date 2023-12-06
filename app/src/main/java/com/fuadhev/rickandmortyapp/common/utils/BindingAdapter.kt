package com.fuadhev.rickandmortyapp.common.utils

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fuadhev.rickandmortyapp.R
import com.google.android.material.imageview.ShapeableImageView

object BindingAdapter {

    @BindingAdapter("load_url")
    @JvmStatic
    fun loadUrl(view: ShapeableImageView, url: String?) {
        url?.let {
            Glide.with(view)
                .load(url)
                .placeholder(R.drawable.preloading_animation)
                .into(view)
        }
    }

}