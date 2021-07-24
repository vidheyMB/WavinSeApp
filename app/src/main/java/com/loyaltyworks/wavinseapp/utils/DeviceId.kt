package com.loyaltyworks.wavinseapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object DeviceId {
    @SuppressLint("HardwareIds")
    fun getDeviceID(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        );
    }
}