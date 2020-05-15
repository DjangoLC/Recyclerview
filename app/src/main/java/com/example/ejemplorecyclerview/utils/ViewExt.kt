package com.example.ejemplorecyclerview.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String) {
    Picasso.get()
        .load(url)
        .into(this)
}

fun Context.toast(message: String,length : Int = Toast.LENGTH_LONG) {
    Toast.makeText(this,message,length).show()
}

fun Context.toast(message: Int,length : Int = Toast.LENGTH_LONG) {
    Toast.makeText(this,message,length).show()
}