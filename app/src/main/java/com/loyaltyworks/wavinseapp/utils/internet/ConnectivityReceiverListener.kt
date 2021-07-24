package com.loyaltyworks.wavinseapp.utils.internet

interface ConnectivityReceiverListener {
    fun onNetworkConnectionChanged(isConnected: Boolean)
}