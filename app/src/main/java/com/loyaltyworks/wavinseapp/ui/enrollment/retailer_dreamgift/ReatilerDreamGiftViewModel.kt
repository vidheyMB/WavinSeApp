package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_dreamgift

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.model.RemoveDreamGiftRequest
import com.loyaltyworks.wavinseapp.model.RemoveDreamGiftResponse
import kotlinx.coroutines.launch

class ReatilerDreamGiftViewModel : BaseViewModel() {

    /* Dream Gift Listing Request  */
    private val _getDreamGiftLiveData = MutableLiveData<DreamGiftResponse>()
    val getDreamGiftLiveData: LiveData<DreamGiftResponse> =
        _getDreamGiftLiveData

    fun getDreamGiftList(dreamGiftRequest: DreamGiftRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getDreamGiftLiveData.postValue(
                apiRepository.getDreamGiftData(
                    dreamGiftRequest
                )
            )
        }
    }



    /* Dream Gift Remove Request  */
    private val _getDreamGiftRemoveLiveData = MutableLiveData<DreamGiftRemoveResponse>()
    val getDreamGiftRemoveLiveData: LiveData<DreamGiftRemoveResponse> =
        _getDreamGiftRemoveLiveData

    fun getDreamGiftRemoveList(dreamGiftRemoveRequest: DreamGiftRemoveRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getDreamGiftRemoveLiveData.postValue(
                apiRepository.getDreamGiftRemoveData(
                    dreamGiftRemoveRequest
                )
            )
        }
    }


    /* Dream Gift Add Request  */
    private val _getDreamGiftAddLiveData = MutableLiveData<AddDreamGiftResponse>()
    val getDreamGiftAddLiveData: LiveData<AddDreamGiftResponse> =
        _getDreamGiftAddLiveData

    fun getDreamGiftAddList(addDreamGiftRequest: AddDreamGiftRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getDreamGiftAddLiveData.postValue(
                apiRepository.getDreamGiftAddData(
                    addDreamGiftRequest
                )
            )
        }
    }


    // Remove MyDream Gift
    private val _getRemoveDreamGiftLiveData = MutableLiveData<RemoveDreamGiftResponse>()
    val getRemoveDreamGiftLiveData: LiveData<RemoveDreamGiftResponse> = _getRemoveDreamGiftLiveData

    fun getRemoveDreamGiftNew(removeDreamGift: RemoveDreamGiftRequest) {
        scope.launch {

            //post the value inside live data
            _getRemoveDreamGiftLiveData.postValue(apiRepository.getRemoveDreamGift(removeDreamGift))
        }
    }

}