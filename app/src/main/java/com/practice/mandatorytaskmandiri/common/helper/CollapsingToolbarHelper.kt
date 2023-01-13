package com.practice.mandatorytaskmandiri.common.helper

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.practice.mandatorytaskmandiri.BuildConfig

class CollapsingToolbarHelper {
    fun setTittleToolbar(
        collapsingToolbarLayout: CollapsingToolbarLayout?,
        tittle: String
    ) {
        collapsingToolbarLayout?.title = tittle
    }

    fun setImageToolbar(
        imageView: ImageView?,
        imageSrc: String?,
        drawable: Int
    ) {
        when {
            imageSrc.isNullOrEmpty() -> {
                imageView?.context?.let {
                    Glide.with(it).load(imageView.context.getDrawable(drawable)).into(imageView)
                }
            }
            else -> {
                imageView?.context?.let {
                    Glide.with(it).load(BuildConfig.URL_TMDB_IMAGE + imageSrc).into(imageView)
                }
            }
        }
    }
}
