package com.loyaltyworks.wavinseapp.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.model.HistoryNotificationDeleteResponse
import com.loyaltyworks.wavinseapp.model.HistoryNotificationDetailsRequest
import com.loyaltyworks.wavinseapp.model.HistoryNotificationRequest
import com.loyaltyworks.wavinseapp.model.HistoryNotificationResponse
import kotlinx.coroutines.launch

class HistoryNotificationViewModel : BaseViewModel() {

    /*TransactionHistory Listing */
    private val _historyNotificationtLiveData = MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtLiveData

    fun getNotificationHistoryResponse(historyNotificationRequest: HistoryNotificationRequest) {
        scope.launch {
            //post the value inside live data
            _historyNotificationtLiveData.postValue(apiRepository.getHistoryNotificationList(historyNotificationRequest))
        }
    }

    /*TransactionHistory Listing */
    private val _historyNotificationtDetailByIDLiveData =
        MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtDetailByIDLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtDetailByIDLiveData

    fun getHistoryNotificationDetailById(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest) {
        scope.launch {
            //post the value inside live data
            _historyNotificationtLiveData.postValue( apiRepository.getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest))
        }
    }

    /*TransactionHistory Listing */
    private val _historyNotificationtDeleteByIDLiveData = MutableLiveData<HistoryNotificationDeleteResponse>()
    val historyNotificationtDeleteByIDLiveData : LiveData<HistoryNotificationDeleteResponse> = _historyNotificationtDeleteByIDLiveData

    fun getDeleteHistoryNotificationResponse(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest) {
        scope.launch {
            //post the value inside live data
            _historyNotificationtDeleteByIDLiveData.postValue(apiRepository.getHistoryNotificationDeleteByIdList(historyNotificationDetailsRequest))
        }
    }

}