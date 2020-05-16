package com.example.ejemplorecyclerview.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response

class NetworkServiceImpl(context: Context) : NetworkService {

    private val applicationContext = context.applicationContext

    override fun isConnected(): Boolean {
        return isInternetAvailable()
    }

    @SuppressLint("NewApi")
    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }
}