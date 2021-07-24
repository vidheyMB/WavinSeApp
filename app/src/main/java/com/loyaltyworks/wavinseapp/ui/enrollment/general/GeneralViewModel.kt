package com.loyaltyworks.wavinseapp.ui.enrollment.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class GeneralViewModel : BaseViewModel() {

    /*GetCustomer Category Request Callback*/
    private val _getCustomerCategoryLiveData = MutableLiveData<AttributeResponse>()
    val getCustomerCategoryLiveData: LiveData<AttributeResponse> = _getCustomerCategoryLiveData

    fun setCustomerCategoryRequest(attributeRequest: AttributeRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getCustomerCategoryLiveData.postValue(
                apiRepository.getCustomerCategoryData(
                    attributeRequest
                )
            )
        }
    }

    /*Distributor Request Callback*/
    private val _getDistributorLiveData = MutableLiveData<AttributeResponse>()
    val getDistributorLiveData: LiveData<AttributeResponse> = _getDistributorLiveData
    fun setDistributorCategoryRequest(attributeRequest: AttributeRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getDistributorLiveData.postValue(
                apiRepository.getDistributorData(
                    attributeRequest
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


    /*Get Tier Listing  */
    private val _getLanguageTypeListingLiveData = MutableLiveData<TierResponse>()
    val getLanguageTypeListingLiveData: LiveData<TierResponse> = _getLanguageTypeListingLiveData

    fun getLanguageTypeListing(tierRequest: TierRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getLanguageTypeListingLiveData.postValue(apiRepository.getLanguageTypeListingData(tierRequest))
        }
    }


    private val _getCustomerTypeListingLiveData = MutableLiveData<TierResponse>()
    val getCustomerTypeListingLiveData: LiveData<TierResponse> = _getCustomerTypeListingLiveData

    fun getCustomerTypeListing(tierRequest: TierRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getCustomerTypeListingLiveData.postValue(apiRepository.getCustomerTypeListingData(tierRequest))
        }
    }

    private val _getGeneralSubmitLiveData = MutableLiveData<GeneralResponse>()
    val getGeneralSubmitLiveData: LiveData<GeneralResponse> = _getGeneralSubmitLiveData

    fun setGeneralUpdateRequest(generalRequest: GeneralRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getGeneralSubmitLiveData.postValue(apiRepository.getGeneralSubmitData(generalRequest))
        }
    }


}