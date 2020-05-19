package com.example.ejemplorecyclerview.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.squareup.picasso.Picasso


fun ImageView.loadUrl(url: String) {
    val link = if (url.isEmpty()) "https://i0.wp.com/oij.org/wp-content/uploads/2016/05/placeholder.png?ssl=1" else "http://image.tmdb.org/t/p/w500/$url"
    Log.e("asdas",link)
    Picasso.get()
        .load(link)
        .into(this)
}

fun ImageView.loadOriginalUrl(url: String) {
    val link = if (url.isEmpty()) "https://i0.wp.com/oij.org/wp-content/uploads/2016/05/placeholder.png?ssl=1" else "http://image.tmdb.org/t/p/original/$url"
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

fun SearchView.hideKeyboard(){
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.getWindowToken(), 0)
}