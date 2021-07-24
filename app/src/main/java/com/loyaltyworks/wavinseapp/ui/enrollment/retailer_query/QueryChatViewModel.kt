package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.model.PostChatStatusRequest
import com.loyaltyworks.wavinseapp.model.PostChatStatusResponse
import com.loyaltyworks.wavinseapp.model.QueryChatElementRequest
import com.loyaltyworks.wavinseapp.model.QueryChatElementResponse
import kotlinx.coroutines.launch

class QueryChatViewModel : BaseViewModel() {
    /*Chat reply*/
    private val _queryChatLiveData = MutableLiveData<QueryChatElementResponse>()
    val queryChatLiveData : LiveData<QueryChatElementResponse> = _queryChatLiveData

    fun getQueryChat(chatQuery: QueryChatElementRequest){
        scope.launch { _queryChatLiveData.postValue(apiRepository.getChatQuery(chatQuery)) }
    }

    /*Post reply help topic*/

    private val _postChatStatusResponseLiveData = MutableLiveData<PostChatStatusResponse>()
    val postChatStatusResponseLiveData : LiveData<PostChatStatusResponse> = _postChatStatusResponseLiveData

    fun getPostReply(postChatStatusRequest: PostChatStatusRequest, context: Context) {

        scope.launch {
            _postChatStatusResponseLiveData.postValue(
                apiRepository.getPostReply(
                    postChatStatusRequest
                )
            )
        }
    }

}