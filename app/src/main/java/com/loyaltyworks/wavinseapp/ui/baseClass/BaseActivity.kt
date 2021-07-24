package com.loyaltyworks.wavinseapp.ui.baseClass

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.utils.internet.ConnectivityReceiver
import com.loyaltyworks.wavinseapp.utils.dialogBox.NoInternetDialog
import com.loyaltyworks.wavinseapp.utils.internet.ConnectivityReceiverListener
import java.io.File


/** Created by : Vidhey
 *  Project : Good pack project
 *  Date : 10 Nov 2020 */

abstract class BaseActivity : AppCompatActivity(),


    ConnectivityReceiverListener {

    private var hideNotInternetDialogue = false  // default show no internet dialogue

    open var context: Context? = null


    lateinit var viewGroup: ViewGroup

    // connectivity Receiver class
    private var connectivityReceiver = ConnectivityReceiver


    // call the services on internet present
    abstract fun callInitialServices()

    // call the observers onCreate
    abstract fun callObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${this.localClassName} : Created Base Activity")

    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "${this.localClassName} : OnStart Base Activity")

        callObservers()

        // register connectivity receiver
        registerConnectivityReceiver(true)

        // get view groups
        viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "${this.localClassName} : Pause Base Activity")

    }

    override fun onDestroy() {
        Log.d(TAG, "${this.localClassName} : Destroyed Base Activity")
        registerConnectivityReceiver(false)
        super.onDestroy()
    }

    private fun registerConnectivityReceiver(isRegister: Boolean) {
        if (isRegister) {
            // set network listener callback
            connectivityReceiver.connectivityReceiverListener = this
            // register the network check broadcast receiver
            registerReceiver(
                connectivityReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        } else {

            try {
                // unregister the connectivity receiver
                unregisterReceiver(connectivityReceiver)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }

        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            Log.d(TAG, "${this.localClassName} : onNetworkConnectionChanged: $isConnected")
            // dismiss no internet dialog on internet back
            NoInternetDialog.dismissDialog()
            // call the services on internet back
            callInitialServices()
        } else {
            Log.d(TAG, "${this.localClassName} : onNetworkConnectionChanged: $isConnected")
            if(!hideNotInternetDialogue) {
                // show no internet dialog on no internet
                NoInternetDialog.showDialog(this)
            }
        }

    }

    // optional for display or hide no internet dialogue
    fun hideNoInternetDialog(hide: Boolean){
        hideNotInternetDialogue = hide
    }

    companion object {
        const val TAG = "BaseActivity"
    }

    // SnackBar display
    @RequiresApi(Build.VERSION_CODES.M)
    fun snackBar(MSG: String, color: Int) {
        Snackbar.make(viewGroup, MSG, Snackbar.LENGTH_LONG).setBackgroundTint(
            getColor(color)
        ).show()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun openFile(filename: String, fileType: String) {

        val dwldsPath = File(filename)

        val fileTypes = if(fileType == "pdf"){
            "application/pdf"
        }else if(fileType == "png" || fileType == "jpg"){
            "image/*"
        }else{
            "application/pdf"
        }

        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val apkURI = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    dwldsPath
                )

                intent.setDataAndType(apkURI, fileTypes)

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.setDataAndType(Uri.fromFile(dwldsPath), fileTypes)
            }
            startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            snackBar("No application found to open file", R.color.red)
        }
    }

    /**
     *  EOF Download PDF Files
     */
}