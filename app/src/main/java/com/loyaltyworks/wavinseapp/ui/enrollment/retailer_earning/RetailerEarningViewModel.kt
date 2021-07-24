package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_earning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.model.MyEarningRequest
import com.loyaltyworks.wavinseapp.model.MyEarningResponse
import kotlinx.coroutines.launch

class RetailerEarningViewModel : BaseViewModel() {


    /*Get Earnings Listing */
    private val _myEarningLiveData = MutableLiveData<MyEarningResponse>()
    val myEarningLiveData: LiveData<MyEarningResponse> = _myEarningLiveData

    fun setEarningListingRequest(myEarningRequest: MyEarningRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _myEarningLiveData.postValue(apiRepository.getMyEarningDetailRequest(myEarningRequest))
        }
    }

}