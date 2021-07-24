package com.loyaltyworks.wavinseapp.ui.enrollment.identification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class IdentificationViewModel : BaseViewModel() {

    /*Get AddressProofIdentification Listing  */
    private val _getAddreseProofIdentificationListingLiveData = MutableLiveData<TierResponse>()
    val getAddreseProofIdentificationListingLiveData: LiveData<TierResponse> = _getAddreseProofIdentificationListingLiveData

    fun getAddressProofListing(tierRequest: TierRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getAddreseProofIdentificationListingLiveData.postValue(apiRepository.getTierListingData(tierRequest))
        }
    }


 /*Get IdentityProofIdentification Listing  */
    private val _getIdentityProofIdentificationListingLiveData = MutableLiveData<TierResponse>()
    val getIdentityProofIdentificationListingLiveData: LiveData<TierResponse> = _getIdentityProofIdentificationListingLiveData

    fun getIdentityProofListing(tierRequest: TierRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getIdentityProofIdentificationListingLiveData.postValue(apiRepository.getTierListingData(tierRequest))
        }
    }


    /*setIdentificationNumberExistencyCheck*/
    private val _getIdentificationNumberExistencyCheckLiveData = MutableLiveData<IdentificationNumberExistencyCheckResponse>()
    val getIdentificationNumberExistencyCheckLiveData: LiveData<IdentificationNumberExistencyCheckResponse> = _getIdentificationNumberExistencyCheckLiveData

    fun setIdentificationNumberCheckExistencyRequest(identificationNumberExistencyCheckRequest: IdentificationNumberExistencyCheckRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getIdentificationNumberExistencyCheckLiveData.postValue(apiRepository.getIdentificationNumberExistencyCheckData(identificationNumberExistencyCheckRequest))
        }
    }

    /*setIdentificationNumberExistencyCheck*/
    private val _getSaveUpdateIdentificationLiveData = MutableLiveData<SaveUpdateIdentificationDetailResponse>()
    val getSaveUpdateIdentificationLiveData: LiveData<SaveUpdateIdentificationDetailResponse> = _getSaveUpdateIdentificationLiveData

    fun setSaveUpdateRequest(identificationSaveUpdateRequest: IdentificationSaveUpdateRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getSaveUpdateIdentificationLiveData.postValue(apiRepository.getSaveUpdateIdentificationDetail(identificationSaveUpdateRequest))
        }
    }


}