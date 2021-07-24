package com.loyaltyworks.wavinseapp.ui.enrollment.selected_retailer_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class SelectedRetailerDetailViewModel : BaseViewModel() {

    /*Get Selected Retailer Detail Request api callback*/

    private val _getSelectedRetailerDetailsByCustomerIDLiveData = MutableLiveData<GetSelectedRetailerDetailByIDResponse>()
    val getSelectedRetailerDetailsByCustomerIDLiveData: LiveData<GetSelectedRetailerDetailByIDResponse> = _getSelectedRetailerDetailsByCustomerIDLiveData

    fun getSelectedRetailerDetailbyCustomerId(getSelectedRetailerDetailByCustomerIDRequest: GetSelectedRetailerDetailByCustomerIDRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getSelectedRetailerDetailsByCustomerIDLiveData.postValue(
                apiRepository.getSelectedRetailerDetailsByCustomerIDData(
                    getSelectedRetailerDetailByCustomerIDRequest
                )
            )
        }
    }


    /*Customer Dashboard Detail */
    private val _dashboardLiveData = MutableLiveData<DashboardResponse>()
    val dashboardLiveData: LiveData<DashboardResponse> = _dashboardLiveData

    fun getDashBoardData(dashboardRequest: DashboardRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _dashboardLiveData.postValue(apiRepository.getDashBoardData(dashboardRequest))
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