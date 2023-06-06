package com.diplomaproject.movies.adapter

import androidx.appcompat.widget.AppCompatRatingBar
import androidx.databinding.BindingAdapter

@BindingAdapter("ratingFromString")
fun setRatingFromString(ratingBar: AppCompatRatingBar, ratingString: String?) {
    if (!ratingString.isNullOrEmpty()) {
        val rating = ratingString.toDoubleOrNull()
        if (rating != null) {
            ratingBar.rating = rating.toFloat()
        }
    }
}
