package com.loyaltyworks.wavinseapp.ui.redemptionstatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.RedemptionsStatusRequest
import com.loyaltyworks.wavinseapp.model.RedemptionsStatusResponse
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class RedemptionsStatusViewModel : BaseViewModel() {

  /*  *//*Get Retailer Order Request  *//*
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
    }*/



    /* Redemptions Status Request  */
    private val _getRedemptionStatusLiveData = MutableLiveData<RedemptionsStatusResponse>()
    val getRedemptionStatusLiveData: LiveData<RedemptionsStatusResponse> =
        _getRedemptionStatusLiveData

    fun getRedemptionStatusList(redemptionsStatusRequest: RedemptionsStatusRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getRedemptionStatusLiveData.postValue(
                apiRepository.getRedemptionsStatusData(
                    redemptionsStatusRequest
                )
            )
        }
    }


}