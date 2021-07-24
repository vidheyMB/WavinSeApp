package com.loyaltyworks.wavinseapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.LoginRequest
import com.loyaltyworks.wavinseapp.model.LoginResponse
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {


/*    private val _existencyCodeVerification = MutableLiveData<CheckCustomerExistancyAndVerificationResponse>()
    val existencyCodeVerification: LiveData<CheckCustomerExistancyAndVerificationResponse> = _existencyCodeVerification


    fun getUserNameExistence(checkCustomerExistancyAndVerificationRequest: CheckCustomerExistancyAndVerificationRequest) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            //post the value inside live data
            _existencyCodeVerification.postValue(apiRepository.getCustomerExistency(checkCustomerExistancyAndVerificationRequest))
        }
    }*/

    /*Login Request Response call back*/
    private val _loginLiveData = MutableLiveData<LoginResponse>()
    val loginLiveData: LiveData<LoginResponse> = _loginLiveData


    fun getLoginData(loginRequest: LoginRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _loginLiveData.postValue(apiRepository.getLoginData(loginRequest))
        }
    }

}