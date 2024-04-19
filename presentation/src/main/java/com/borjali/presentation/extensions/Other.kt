@file:Suppress( "DEPRECATION")

package com.borjali.presentation.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.media.ExifInterface.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.util.*

val Context.isOnline: Boolean
    @SuppressLint("MissingPermission")
    get() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

