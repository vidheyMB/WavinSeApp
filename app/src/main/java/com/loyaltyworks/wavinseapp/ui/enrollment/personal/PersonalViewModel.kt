package com.loyaltyworks.wavinseapp.ui.enrollment.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class PersonalViewModel : BaseViewModel() {

    /*Get Country Listing  */
    private val _getCountryListingLiveData = MutableLiveData<CountryResponse>()
    val getCountryListingLiveData: LiveData<CountryResponse> = _getCountryListingLiveData

    fun setCountryRequest(countryRequest: CountryRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getCountryListingLiveData.postValue(apiRepository.getCountryListingData(countryRequest))
        }
    }

    /*Get Country Listing  */
    private val _getDistrictListingLiveData = MutableLiveData<DistrictResponse>()
    val getDistrictListingLiveData: LiveData<DistrictResponse> = _getDistrictListingLiveData

    fun setDistrictRequest(districtRequest: DistrictRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getDistrictListingLiveData.postValue(apiRepository.getDistrictListingData(districtRequest))
        }
    }

    /*Get Native State Listing  */
    private val _getNativeStateListingLiveData = MutableLiveData<StateResponse>()
    val getNativeStateListingLiveData: LiveData<StateResponse> = _getNativeStateListingLiveData

    fun setNativeStateRequest(stateRequest: StateRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getNativeStateListingLiveData.postValue(apiRepository.getNativeStateListingData(stateRequest))
        }
    }

    /*Get State Listing  */
    private val _getStateListingLiveData = MutableLiveData<StateResponse>()
    val getStateListingLiveData: LiveData<StateResponse> = _getStateListingLiveData

    fun setStateRequest(stateRequest: StateRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getStateListingLiveData.postValue(apiRepository.getStateListingData(stateRequest))
        }
    }

    /* City Request */
    private val _getCityResponse = MutableLiveData<GetCityDetailsResponse>()
    val getCityResponse: LiveData<GetCityDetailsResponse> = _getCityResponse

    fun setCityRequest(getCityDetailsRequest: GetCityDetailsRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _getCityResponse.postValue(apiRepository.getCityData(getCityDetailsRequest))
        }
    }

    /*Get Personal Save and Update  */
    private val _getPersonalLiveData = MutableLiveData<GeneralResponse>()
    val getPersonalLiveData: LiveData<GeneralResponse> = _getPersonalLiveData

    fun setUpdateProfileRequest(personalSaveUpdateRequest: PersonalSaveUpdateRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getPersonalLiveData.postValue(apiRepository.getPersonalSaveUpdateData(personalSaveUpdateRequest))
        }
    }

}