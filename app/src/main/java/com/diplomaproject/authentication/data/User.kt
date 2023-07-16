package com.diplomaproject.authentication.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class User(
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val uId: String? = null,
    val profile_picture: String? = null

) {

}