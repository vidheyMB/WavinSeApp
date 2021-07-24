package com.loyaltyworks.wavinseapp.ui.usermapping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.UserMappedParentRequest
import com.loyaltyworks.wavinseapp.model.UserMappedParentResponse
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class UserMappingViewModel : BaseViewModel() {

    /*Get Retailer Order Request  */
    private val _getMappedParentChildLiveData = MutableLiveData<UserMappedParentResponse>()
    val getMappedParentChildLiveData: LiveData<UserMappedParentResponse> =
        _getMappedParentChildLiveData

    fun getMappedParentChildList(attributeRequest: UserMappedParentRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getMappedParentChildLiveData.postValue(
                apiRepository.getUserMappedRequestData(
                    attributeRequest
                )
            )
        }
    }
}