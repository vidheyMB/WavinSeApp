package com.loyaltyworks.wavinseapp.utils

import android.os.SystemClock

object BlockMultipleClick {
    private var mLastClickTime: Long = 0
    fun click(): Boolean {
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }
}