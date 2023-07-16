package com.diplomaproject.movies.adapter

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DataBindingAdapter {
    @BindingAdapter("ratingFromString")
    @JvmStatic
    fun setRatingFromString(ratingBar: AppCompatRatingBar, ratingString: String?) {
        if (!ratingString.isNullOrEmpty()) {
            val rating = ratingString.toDoubleOrNull()
            if (rating != null) {
                ratingBar.rating = rating.toFloat()
            }
        }
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageByRes(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)

    }
}
