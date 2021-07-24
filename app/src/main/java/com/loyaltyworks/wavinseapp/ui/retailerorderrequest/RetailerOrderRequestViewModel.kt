package com.loyaltyworks.wavinseapp.ui.retailerorderrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class RetailerOrderRequestViewModel : BaseViewModel() {

    /*Get Retailer Order Request  */
    private val _getAttributeDetailsLiveData = MutableLiveData<AttributeResponse>()
    val getAttributeDetailsLiveData: LiveData<AttributeResponse> = _getAttributeDetailsLiveData

    fun getAttributeDetailsList(attributeRequest: AttributeRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getAttributeDetailsLiveData.postValue(
                apiRepository.getAttributeDetailsRequestData(
                    attributeRequest
                )
            )
        }
    }


    /*Get Plumber Claim Status Request  */
    private val _getPlumberClaimStatusLiveData = MutableLiveData<RetailerOrderResponse>()
    val getPlumberClaimLiveData: LiveData<RetailerOrderResponse> = _getPlumberClaimStatusLiveData

    fun getPlumberClaimList(plumberClaimStatusRequest: RetailerOrderRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getPlumberClaimStatusLiveData.postValue(
                apiRepository.getPlumberClaimRequestData(
                    plumberClaimStatusRequest
                )
            )
        }
    }


    /*Get Order Details Request  */
    private val _getOrderDetilsLiveData = MutableLiveData<OrderDetailsResponse>()
    val getOrderDetailsLiveData: LiveData<OrderDetailsResponse> = _getOrderDetilsLiveData

    fun getOrderDetailsList(orderDetailsRequest: OrderDetailsRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getOrderDetilsLiveData.postValue(
                apiRepository.getOrderDetailsData(
                    orderDetailsRequest
                )
            )
        }
    }



}