package com.diplomaproject.movies.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class OrderItem(
    val movie_name: String,
    val banner_image_url: String,
    val room: String,
    val date: String,
    val seating_no: String,
    val price: String,
    val quantity: String,
    val total_price: String,
    val time: String,
    val id: String
){

}
