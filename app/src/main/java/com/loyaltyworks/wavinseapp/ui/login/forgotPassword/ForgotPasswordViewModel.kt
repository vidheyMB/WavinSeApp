package com.loyaltyworks.wavinseapp.ui.login.forgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.ForgotPasswordRequest
import com.loyaltyworks.wavinseapp.model.ForgotPasswordResponse
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : BaseViewModel() {
    private val _forgotPasswordLiveData = MutableLiveData<ForgotPasswordResponse>()
    val forgotPasswordLiveData: LiveData<ForgotPasswordResponse> = _forgotPasswordLiveData

    fun getForgotPwd(forgotPasswordRequest: ForgotPasswordRequest) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
//            val forgotPassword = apiRepository.getForgotPasswordData(forgotPasswordRequest)
            //post the value inside live data
            _forgotPasswordLiveData.postValue(apiRepository.getForgotPasswordData(forgotPasswordRequest))
        }
    }


}