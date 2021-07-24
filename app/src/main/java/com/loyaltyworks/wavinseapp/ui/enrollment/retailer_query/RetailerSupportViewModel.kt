package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.model.GetHelpTopicResponse
import com.loyaltyworks.wavinseapp.model.HelpTopicRetrieveRequest
import com.loyaltyworks.wavinseapp.model.QueryListingRequest
import com.loyaltyworks.wavinseapp.model.QueryListingResponse
import kotlinx.coroutines.launch

class RetailerSupportViewModel : BaseViewModel() {


    /*Help topic listing*/
    private val _topicListData = MutableLiveData<GetHelpTopicResponse>()
    val topicListLiveData: LiveData<GetHelpTopicResponse> = _topicListData

    fun getHelpTopicListLiveData(topicList: HelpTopicRetrieveRequest) {
        scope.launch {
            _topicListData.postValue(apiRepository.getHelpTopic(topicList))
        }
    }


    /*Query Listing */
    private val _queryListLiveData = MutableLiveData<QueryListingResponse>()
    val queryListLiveData : LiveData<QueryListingResponse> = _queryListLiveData

    fun getQueryListLiveData(queryList: QueryListingRequest){
        scope.launch { _queryListLiveData.postValue(apiRepository.getQueryListData(queryList)) }
    }
}