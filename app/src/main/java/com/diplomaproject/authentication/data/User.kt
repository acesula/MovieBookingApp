package com.diplomaproject.authentication.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class User(
    val name: String?,
    val surname: String?,
    val email: String?,
    val uId: String?,
    val profile_picture: String?

) {

    object DataBindingAdapter {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageByRes(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .into(imageView)

        }
    }
}