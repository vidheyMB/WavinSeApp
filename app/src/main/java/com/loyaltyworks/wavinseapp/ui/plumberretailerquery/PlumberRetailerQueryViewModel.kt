package com.loyaltyworks.wavinseapp.ui.plumberretailerquery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.model.GetHelpTopicResponse
import com.loyaltyworks.wavinseapp.model.HelpTopicRetrieveRequest
import kotlinx.coroutines.launch

class PlumberRetailerQueryViewModel : BaseViewModel(){

    /*_seQueryListing Request Response call back*/
    private val _seQueryListingResponse = MutableLiveData<SEQueryListingResponse>()
    val seQueryListingResponse: LiveData<SEQueryListingResponse> = _seQueryListingResponse

    fun getSEQueryListingData(seQueryListingRequest: SEQueryListingRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _seQueryListingResponse.postValue(apiRepository.getSEQueryData(seQueryListingRequest))
        }

    }

    /*Help topic listing*/
    private val _topicListData = MutableLiveData<GetHelpTopicResponse>()
    val topicListLiveData : LiveData<GetHelpTopicResponse> = _topicListData

    fun getHelpTopicListLiveData(topicList: HelpTopicRetrieveRequest) {
        scope.launch {
            val helpTopic_data = apiRepository.getHelpTopic(topicList)
            _topicListData.postValue(helpTopic_data!!) }
    }

    /*getSEQueryChatRequest-Response*/
    private val _seQueryChatRequest = MutableLiveData<SEQueryChatResponse>()
    val seQueryChatRequest : LiveData<SEQueryChatResponse> = _seQueryChatRequest

    fun setSEQueryDetailByIDRequest(seQueryChatRequest: SEQueryChatRequest) {
        scope.launch {
            _seQueryChatRequest.postValue(apiRepository.getQueryChatRequest(seQueryChatRequest))
        }
    }

    /*seQueryChatReplyRequest-Response*/
    private val _seQueryChatReplyResponse = MutableLiveData<SEQueryChatReplyResponse>()
    val seQueryChatReplyResponse : LiveData<SEQueryChatReplyResponse> = _seQueryChatReplyResponse

    fun setPostReplyRequest(seQueryChatReplyRequest: SEQueryChatReplyRequest) {
        scope.launch {
            _seQueryChatReplyResponse.postValue(apiRepository.getQueryChatReplyRequest(seQueryChatReplyRequest))
        }
    }


}