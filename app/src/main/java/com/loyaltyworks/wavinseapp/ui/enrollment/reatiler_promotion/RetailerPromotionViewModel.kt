package com.loyaltyworks.wavinseapp.ui.enrollment.reatiler_promotion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class RetailerPromotionViewModel : BaseViewModel() {
    /*Promotion Listing */
    private val _getPromotionListLiveData = MutableLiveData<GetPromotionResponse>()
    val getPromotionListLiveData: LiveData<GetPromotionResponse> = _getPromotionListLiveData

    fun getWhatsNew(getWhatsNewRequest: GetWhatsNewRequest) {
        scope.launch {

            //post the value inside live data
            _getPromotionListLiveData.postValue(apiRepository.getPromotionList(getWhatsNewRequest))
        }
    }


    /*Promotion Detail  by id*/
    private val _getPromotionDetailLiveData = MutableLiveData<GetPromotionResponse>()
    val getPromotionDetailLiveData: LiveData<GetPromotionResponse> = _getPromotionDetailLiveData
    fun getPromotionDetailByID(getPromotionDetailsRequest: GetPromotionDetailsRequest) {
        scope.launch {

            //post the value inside live data
            _getPromotionDetailLiveData.postValue(
                apiRepository.getPromotionDetail(
                    getPromotionDetailsRequest
                )
            )
        }
    }

    /*Promotion save  by id*/
    private val _saveCustomerPromotionLiveData = MutableLiveData<SaveCustomerPromotionResponse>()
    val saveCustomerPromotionLiveData: LiveData<SaveCustomerPromotionResponse> =
        _saveCustomerPromotionLiveData

    fun setSaveCustomerPromotionRequest(saveCustomerPromotionRequest: SaveCustomerPromotionRequest) {
        scope.launch {

            //post the value inside live data
            _saveCustomerPromotionLiveData.postValue(
                apiRepository.setCustomerPromotionRequest(
                    saveCustomerPromotionRequest
                )
            )
        }
    }
}