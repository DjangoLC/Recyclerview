package com.example.ejemplorecyclerview.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String) {
    val link = if (url.isEmpty()) "https://i0.wp.com/oij.org/wp-content/uploads/2016/05/placeholder.png?ssl=1" else "http://image.tmdb.org/t/p/w500/$url"
    Picasso.get()
        .load(link)
        .into(this)
}

fun Context.toast(message: String,length : Int = Toast.LENGTH_LONG) {
    Toast.makeText(this,message,length).show()
}

fun Context.toast(message: Int,length : Int = Toast.LENGTH_LONG) {
    Toast.makeText(this,message,length).show()
}