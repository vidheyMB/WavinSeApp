package com.loyaltyworks.wavinseapp.ui.splashscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.ui.ForceUpdateAndMaintenance.ForceUpdateActivity
import com.loyaltyworks.wavinseapp.ui.ForceUpdateAndMaintenance.MaintenanceActivity
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.utils.RegistrationAsyncTask
import org.json.JSONException
import org.json.JSONObject
import org.xml.sax.SAXException
import java.io.IOException
import javax.xml.parsers.ParserConfigurationException

class SplashScreenViewModel : BaseViewModel() {

    var UPDATE_URL = "http://appupdate.arokiait.com/updates/serviceget?pid=com.loyaltyworks.wavinseapp"

    var SUCCESS = true
    var FAILURE = false

    private val _isUpdateAvailable = MutableLiveData<Boolean>()

    fun get_isUpdateAvailable(): LiveData<Boolean> {
        return _isUpdateAvailable
    }

    var dialog: Dialog? = null

    fun checkIfUpdateAvailabe(context: Context?, currentVersionCode: Int) {
        RegistrationAsyncTask(context, UPDATE_URL, SUCCESS,
            object : RegistrationAsyncTask.RegistrationResponse {
                @SuppressLint("SetTextI18n")
                @Throws(
                    ParserConfigurationException::class,
                    IOException::class,
                    SAXException::class,
                    JSONException::class
                )
                override fun onSuccess(status: Boolean, response: String?) {
                    if (status) {
                        val jsonObject = JSONObject(response)
                        if (java.lang.Boolean.parseBoolean(jsonObject.getString("Status"))) {
                            val source = jsonObject.getJSONObject("Result")
                            if (source.getString("version_number").toInt() > currentVersionCode || source.getString("is_maintenance").toInt() == 1)
                            {
                                // do something

                                if (source.getString("version_number").toInt() > currentVersionCode) {

                                    val intent = Intent(context, ForceUpdateActivity::class.java) // New activity
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    context!!.startActivity(intent)
                                    (context as Activity).finish()

                                }
                                else if (source.getString("is_maintenance").toInt() == 1) {
//                                    _isUpdateAvailable.value = FAILURE
                                    val intent = Intent(context, MaintenanceActivity::class.java) // New activity
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    context!!.startActivity(intent)
                                    (context as Activity).finish()
                                }
                            } else {
                                _isUpdateAvailable.value = FAILURE
                            }
                        }
                    }
                }

                override fun onFailure(status: Boolean?, response: String?) {
                    _isUpdateAvailable.value = FAILURE
                }
            }).execute()
    }
}