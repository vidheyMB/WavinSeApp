package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.shippingAddress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class ReddemGiftViewModel : BaseViewModel() {


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