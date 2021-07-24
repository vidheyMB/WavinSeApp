package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.model.GetHelpTopicResponse
import com.loyaltyworks.wavinseapp.model.HelpTopicRetrieveRequest
import com.loyaltyworks.wavinseapp.model.SaveNewTicketQueryRequest
import com.loyaltyworks.wavinseapp.model.SaveNewTicketQueryResponse
import kotlinx.coroutines.launch

class NewTicketViewModel  : BaseViewModel() {
    /*Help topic listing*/
    private val _topicListData = MutableLiveData<GetHelpTopicResponse>()
    val topicListLiveData : LiveData<GetHelpTopicResponse> = _topicListData

    fun getHelpTopicListLiveData(topicList: HelpTopicRetrieveRequest) {
        scope.launch {
            val helpTopic_data = apiRepository.getHelpTopic(topicList)
            _topicListData.postValue(helpTopic_data!!) }
    }

    /*Save new ticket request-response*/
    private val _saveNewTicketQueryLiveData = MutableLiveData<SaveNewTicketQueryResponse>()
    val saveNewTicketQueryLiveData : LiveData<SaveNewTicketQueryResponse> = _saveNewTicketQueryLiveData

    fun saveNewTicketQuery(saveNewTicketQueryRequest: SaveNewTicketQueryRequest) {

        scope.launch {
            _saveNewTicketQueryLiveData.postValue(
                apiRepository.getSaveNewTicketQuery(
                    saveNewTicketQueryRequest
                )
            ) }
    }
}