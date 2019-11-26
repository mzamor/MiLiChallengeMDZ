package com.example.milichallenge.presentation.main

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager


object ConnectivityHelper {
    fun isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var isConnected = false
        if (connectivityManager != null) {
            val activeNetwork = connectivityManager.activeNetworkInfo
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

        return isConnected
    }
}