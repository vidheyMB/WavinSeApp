package com.loyaltyworks.wavinseapp.ui.enrollment.member_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.MemberStatusRequest
import com.loyaltyworks.wavinseapp.model.MemberStatusResponse
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class MemberStatusViewModel : BaseViewModel() {

    /* Member Status Update  */
    private val _getMemberStatusLiveData = MutableLiveData<MemberStatusResponse>()
    val getMemberStatusLiveData: LiveData<MemberStatusResponse> = _getMemberStatusLiveData

    fun getMemberStatusUpdate(memberStatusRequest: MemberStatusRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _getMemberStatusLiveData.postValue(apiRepository.getMemberStatusData(memberStatusRequest))
        }
    }



}