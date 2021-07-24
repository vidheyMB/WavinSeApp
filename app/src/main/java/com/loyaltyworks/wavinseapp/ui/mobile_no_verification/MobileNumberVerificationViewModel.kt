package com.loyaltyworks.wavinseapp.ui.mobile_no_verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.CheckCustomerExistancyAndVerificationRequest
import com.loyaltyworks.wavinseapp.model.EmailCheckResponse
import com.loyaltyworks.wavinseapp.model.SaveAndGetOTPDetailsRequest
import com.loyaltyworks.wavinseapp.model.SaveAndGetOTPDetailsResponse
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class MobileNumberVerificationViewModel : BaseViewModel() {

    /***  Check Customer Mobile Number Exists or not ***/

    private val _emailMobileExists = MutableLiveData<EmailCheckResponse>()
    val mobileNumberExists: LiveData<EmailCheckResponse> = _emailMobileExists

    fun getMobileEmailExistenceCheck(emailCheckRequest: CheckCustomerExistancyAndVerificationRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _emailMobileExists.postValue(apiRepository.getEmailExist(emailCheckRequest))
        }
    }

    /*OTP Request Callback*/

    private val _saveAndGetOTPDetailsResponse = MutableLiveData<SaveAndGetOTPDetailsResponse>()
    val saveAndGetOTPDetailsResponse: LiveData<SaveAndGetOTPDetailsResponse> = _saveAndGetOTPDetailsResponse

    fun setOTPRequest(saveAndGetOTPDetailsRequest: SaveAndGetOTPDetailsRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _saveAndGetOTPDetailsResponse.postValue(apiRepository.getOTPDetail(saveAndGetOTPDetailsRequest))
        }
    }

}