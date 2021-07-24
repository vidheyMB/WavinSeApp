package com.loyaltyworks.wavinseapp.utils

import android.content.Context
import com.loyaltyworks.wavinseapp.model.DashboardCustomerResponse
import com.loyaltyworks.wavinseapp.model.DashboardResponse
import com.loyaltyworks.wavinseapp.model.LoginResponse
import com.loyaltyworks.wavinseapp.model.GetCustomerDetailsMobileAppResult
import com.loyaltyworks.wavinseapp.model.SaveCustomerRequest
import com.squareup.moshi.JsonAdapter

import com.squareup.moshi.Moshi



object PreferenceHelper {

    interface PreferenceCallBack{
        fun customerDetails(saveCustomerRequest: SaveCustomerRequest)
    }

    private const val PreferenceName = "LoyaltyPreferenceHelper"
    private const val LoginDetails = "LoyaltyPreferenceHelper_loginDetails"
    private const val RegistrationDetails = "LoyaltyPreferenceHelper_RegistrationDetails"
    private const val DashboardDetails = "LoyaltyPreferenceHelper_uusDashboardDetailssuu"
    private const val SEDashboardDetails = "LoyaltyPreferenceHelper_uusDashboardDetail3suu"
    private const val DashboardDetails2 = "LoyaltyPreferenceHelper_uusDashboardDetails2suu"
    private const val ShowCaseDialogDetails = "LoyaltyPreferenceHelper_uusShowCaseDialogsuu"

    fun setBooleanValue(context: Context, key: String, value: Boolean){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanValue(context: Context, key: String): Boolean{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    fun setStringValue(context: Context, key: String, value: String){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
            sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringValue(context: Context, key: String): String{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")!!
    }

    fun setLongValue(context: Context, key: String, value: Long){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
            sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLongValue(context: Context, key: String): Long{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, 0L)
    }

   fun setLoginDetails(context: Context, loginResponse: LoginResponse){
       val jsonAdapter: JsonAdapter<LoginResponse> = jsonAdapter()
       val json = jsonAdapter.toJson(loginResponse)

       val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
       sharedPreferences.edit().putString(LoginDetails, json).apply()
   }

    fun getLoginDetails(context: Context) : LoginResponse? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(LoginDetails, "")

        val jsonAdapter: JsonAdapter<LoginResponse> = jsonAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }

    fun setDashboardDetails(context: Context, dashboardResponse: DashboardResponse){
       val jsonAdapter: JsonAdapter<DashboardResponse> = jsonDashboardAdapter()
       val json = jsonAdapter.toJson(dashboardResponse)

       val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
       sharedPreferences.edit().putString(DashboardDetails, json).apply()
   }

    fun getDashboardDetails(context: Context) : DashboardResponse? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(DashboardDetails, "")

        val jsonAdapter: JsonAdapter<DashboardResponse> = jsonDashboardAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }

    fun setSECustomerDetails(context: Context, seCustomerResponse: GetCustomerDetailsMobileAppResult){
       val jsonAdapter: JsonAdapter<GetCustomerDetailsMobileAppResult> = jsonSECustomerAdapter()
       val json = jsonAdapter.toJson(seCustomerResponse)

       val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
       sharedPreferences.edit().putString(SEDashboardDetails, json).apply()
   }

    fun getSECustomerDetails(context: Context) : GetCustomerDetailsMobileAppResult? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(SEDashboardDetails, "")

        val jsonAdapter: JsonAdapter<GetCustomerDetailsMobileAppResult> = jsonSECustomerAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }

    fun setCustomerDashboard(context: Context, dashboardResponse: DashboardCustomerResponse){
        val jsonAdapter: JsonAdapter<DashboardCustomerResponse> = jsonDashboardCustomerAdapter()
        val json = jsonAdapter.toJson(dashboardResponse)

        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(DashboardDetails2, json).apply()
    }

    fun getCustomerDashboard(context: Context) : DashboardCustomerResponse? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(DashboardDetails2, "")

        val jsonAdapter: JsonAdapter<DashboardCustomerResponse> = jsonDashboardCustomerAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }



    // Registration data hold until process get complete

    fun setCustomerDetails(context: Context, saveCustomerRequest: SaveCustomerRequest){
        val jsonAdapter: JsonAdapter<SaveCustomerRequest> = jsonSaveCustomerDataAdapter()
        val json = jsonAdapter.toJson(saveCustomerRequest)

        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(RegistrationDetails, json).apply()
    }

    fun getCustomerDetails(context: Context, preferenceCallBack: PreferenceCallBack?=null) : SaveCustomerRequest? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(RegistrationDetails, "")

        val jsonAdapter: JsonAdapter<SaveCustomerRequest> = jsonSaveCustomerDataAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            preferenceCallBack?.customerDetails(jsonAdapter.fromJson(stringValue)!!)
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }

    fun clearCustomerDetails(context: Context){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(RegistrationDetails).apply()
    }

    // EOF Registration data hold until process get complete


    fun clear(context: Context){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    private fun jsonAdapter(): JsonAdapter<LoginResponse> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(LoginResponse::class.java)
    }

    private fun jsonDashboardAdapter(): JsonAdapter<DashboardResponse> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(DashboardResponse::class.java)
    }

    private fun jsonSECustomerAdapter(): JsonAdapter<GetCustomerDetailsMobileAppResult> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(GetCustomerDetailsMobileAppResult::class.java)
    }

    private fun jsonSaveCustomerDataAdapter(): JsonAdapter<SaveCustomerRequest> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(SaveCustomerRequest::class.java)
    }


    private fun jsonDashboardCustomerAdapter(): JsonAdapter<DashboardCustomerResponse> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(DashboardCustomerResponse::class.java)
    }

}
