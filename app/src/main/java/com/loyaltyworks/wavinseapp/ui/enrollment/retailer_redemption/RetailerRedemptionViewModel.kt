package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_redemption

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.model.MyRedemptionRequest
import com.loyaltyworks.wavinseapp.model.MyRedemptionResponse
import kotlinx.coroutines.launch

class RetailerRedemptionViewModel : BaseViewModel() {
    /*Get Redemption Listing */
    private val _myRedemptionLiveData = MutableLiveData<MyRedemptionResponse>()
    val myRedemptionLiveData: LiveData<MyRedemptionResponse> = _myRedemptionLiveData

    fun setMyRedemptionListingRequest(myRedemptionRequest: MyRedemptionRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _myRedemptionLiveData.postValue(apiRepository.getRedemptionDetailRequest(myRedemptionRequest))
        }
    }
}