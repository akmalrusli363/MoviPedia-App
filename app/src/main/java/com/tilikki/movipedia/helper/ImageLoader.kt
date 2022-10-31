package com.tilikki.movipedia.helper

import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tilikki.movipedia.R

object ImageLoader {
    fun loadImage(imageUri: Uri, imageView: ImageView) {
        Picasso.get().load(imageUri)
            .placeholder(R.drawable.ic_baseline_local_movies_24)
            .into(imageView)
    }
}