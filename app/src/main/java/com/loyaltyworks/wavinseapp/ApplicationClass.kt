package com.loyaltyworks.wavinseapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.loyaltyworks.wavinseapp.utils.fetchData.db.WordRepository
import com.loyaltyworks.wavinseapp.utils.fetchData.db.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ApplicationClass : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }

    companion object {
        lateinit  var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}