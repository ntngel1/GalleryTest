package com.shepelevkirill.gateway.network.gateway

import android.content.Context
import android.net.ConnectivityManager
import com.shepelevkirill.core.gateway.NetworkGateway

class NetworkGateway(private val context: Context) : NetworkGateway {
    override fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected)
    }
}