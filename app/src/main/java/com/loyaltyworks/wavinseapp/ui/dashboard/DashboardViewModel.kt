package com.loyaltyworks.wavinseapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class DashboardViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    /*Get Customer Details  */
    private val _getCustomerDetailsLiveData = MutableLiveData<CustomerDetailsResponse>()
    val getCustomerDetailsLiveData: LiveData<CustomerDetailsResponse> = _getCustomerDetailsLiveData

    fun getCustomerDetails(customerDetailsRequest: CustomerDetailsRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getCustomerDetailsLiveData.postValue(
                apiRepository.getCustomerDetailsData(
                    customerDetailsRequest
                )
            )
        }
    }


    /*Get Tier Listing  */
    private val _getTierListingLiveData = MutableLiveData<TierResponse>()
    val getTierListingLiveData: LiveData<TierResponse> = _getTierListingLiveData

    fun getTierListing(tierRequest: TierRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getTierListingLiveData.postValue(apiRepository.getTierListingData(tierRequest))
        }
    }


    /*Customer Dashboard Merchnat Detail */
    private val _dashboardLiveData2 = MutableLiveData<DashboardCustomerResponse>()
    val dashboardLiveData2: LiveData<DashboardCustomerResponse> = _dashboardLiveData2

    fun getDashBoardData2(dashboardCustomerRequest: DashboardCustomerRequest) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            //post the value inside live data
            _dashboardLiveData2.postValue(apiRepository.getDashBoardData2(dashboardCustomerRequest))
        }
    }


}