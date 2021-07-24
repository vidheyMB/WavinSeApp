package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.shippingAddress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class ShippingAddressViewModel : BaseViewModel() {
    /*My Profile Request */
    private val _myProfileResponse = MutableLiveData<MyProfileResponse>()
    val myProfileResponse: LiveData<MyProfileResponse> = _myProfileResponse

    fun setUserDetailRequest(myProfileRequest: MyProfileRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _myProfileResponse.postValue(apiRepository.getProfileRequestData(myProfileRequest))
        }
    }

    /* State Request */
    private val _getStateResponse = MutableLiveData<GetStateResponse>()
    val getStateResponse: LiveData<GetStateResponse> = _getStateResponse

    fun setStateRequest(getStateRequest: GetStateRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _getStateResponse.postValue(apiRepository.getStateData(getStateRequest))
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

    /* District Request */
    private val _getDistrictResponse = MutableLiveData<DistrictResponse>()
    val getDistrictResponse: LiveData<DistrictResponse> = _getDistrictResponse

    fun setDistrictRequest(districtRequest: DistrictRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _getDistrictResponse.postValue(apiRepository.getDistrictData(districtRequest))
        }
    }

    private val _otp = MutableLiveData<OTPResponse>()
    val otp: LiveData<OTPResponse> = _otp

    fun getOTP(otpRequest: OTPRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _otp.postValue(apiRepository.getOTP(otpRequest))
        }
    }


    // ===================================================



    /*Redeem Catalogue Product Request*/

    private val _redeemGiftCatalogueResponse = MutableLiveData<RedeemGiftCatalogueResponse>()
    val redeemGiftCatalogueResponse: LiveData<RedeemGiftCatalogueResponse> = _redeemGiftCatalogueResponse

    fun setRedeemGiftCatalogueRequest(redeemGiftCatalogueRequest: RedeemGiftCatalogueRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _redeemGiftCatalogueResponse.postValue(apiRepository.getRedeemGiftCatalogueRequest(redeemGiftCatalogueRequest))
        }
    }

    /*SendCatalogueRedemptionAlertMobile App request*/

    private val _AlertMobileAppResponse = MutableLiveData<SendCatalogueRedemptionAlertMobileAppResponse>()
    val alertMobileAppResponse: LiveData<SendCatalogueRedemptionAlertMobileAppResponse> = _AlertMobileAppResponse

    fun setSendCatalogueRedemptionAlertMobileAppRequest(sendCatalogueRedemptionAlertMobileAppRequest: SendCatalogueRedemptionAlertMobileAppRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _AlertMobileAppResponse.postValue(apiRepository.getSendCatalogueRedemptionAlertMobileAppRequest(sendCatalogueRedemptionAlertMobileAppRequest))
        }
    }


    /*SendCatalogueRedemptionAlertMobile App request*/

    private val _SendSMSForSuccessfulRedemptionMobileAppResponse = MutableLiveData<SendSMSForSuccessfulRedemptionMobileAppResponse>()
    val sendSMSForSuccessfulRedemptionMobileAppResponse: LiveData<SendSMSForSuccessfulRedemptionMobileAppResponse> = _SendSMSForSuccessfulRedemptionMobileAppResponse

    fun setSendSMSForSuccessfulRedemptionRequest(sendSMSForSuccessfulRedemptionMobileAppRequest: SendSMSForSuccessfulRedemptionMobileAppRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _SendSMSForSuccessfulRedemptionMobileAppResponse.postValue(apiRepository.getSendSMSSuccessRedemptionRequest(sendSMSForSuccessfulRedemptionMobileAppRequest))
        }
    }

}