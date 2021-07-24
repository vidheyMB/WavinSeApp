package com.loyaltyworks.wavinseapp.ui.enrollment.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.SaveUpdateBusinessRequest
import com.loyaltyworks.wavinseapp.model.SaveUpdateBusinessResponse
import com.loyaltyworks.wavinseapp.model.TierRequest
import com.loyaltyworks.wavinseapp.model.TierResponse
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class BusinessViewModel : BaseViewModel() {

    /*Get Firm Type Listing  */
    private val _getFirmTypeListingLiveData = MutableLiveData<TierResponse>()
    val getFirmTypeListingLiveData: LiveData<TierResponse> = _getFirmTypeListingLiveData

    fun getFirmTypeListing(firmTypeRequest: TierRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getFirmTypeListingLiveData.postValue(apiRepository.getFirmTypeListingData(firmTypeRequest))
        }
    }


     /*Get Job Type Listing  */
    private val _getJobTypeListingLiveData = MutableLiveData<TierResponse>()
    val getJobTypeListingLiveData: LiveData<TierResponse> = _getJobTypeListingLiveData

    fun getJobTypeListing(firmTypeRequest: TierRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getJobTypeListingLiveData.postValue(apiRepository.getJobTypeListingData(firmTypeRequest))
        }
    }

    /*GetSaveUpdateBusinessData  */
    private val _getSaveUpdateBusinessLiveData = MutableLiveData<SaveUpdateBusinessResponse>()
    val getSaveUpdateBusinessLiveData: LiveData<SaveUpdateBusinessResponse> = _getSaveUpdateBusinessLiveData

    fun setSaveUpdateBusinessRequest(saveUpdateBusinessRequest: SaveUpdateBusinessRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getSaveUpdateBusinessLiveData.postValue(apiRepository.getSaveUpdateBusinessRequestData(saveUpdateBusinessRequest))
        }
    }

}