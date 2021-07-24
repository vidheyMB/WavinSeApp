package com.loyaltyworks.wavinseapp.ui.enrollment.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class WishListViewModel : BaseViewModel() {

    // WishList Listing Request
    private val _getWishListLiveData = MutableLiveData<WishListResponse>()
    val getWishListLiveData: LiveData<WishListResponse> = _getWishListLiveData

    fun getWishListLiveData(wishListRequest: WishListRequest) {
        scope.launch {

            //post the value inside live data
            _getWishListLiveData.postValue(apiRepository.getWishListData(wishListRequest))
        }
    }

  // Remove WishList Listing Request
    private val _getRemoveWishListLiveData = MutableLiveData<RemoveWishListResponse>()
    val getRemoveWishListLiveData: LiveData<RemoveWishListResponse> = _getRemoveWishListLiveData

    fun getRemoveWishListLiveData(wishListRequest: RemoveWishListRequest) {
        scope.launch {

            //post the value inside live data
            _getRemoveWishListLiveData.postValue(apiRepository.getRemoveWishListData(wishListRequest))
        }
    }


    // Remove WishList Listing Request
    private val _getAddOrNotWishListLiveData = MutableLiveData<AddOrNotWishListResponse>()
    val getAddOrNotWishListLiveData: LiveData<AddOrNotWishListResponse> = _getAddOrNotWishListLiveData

    fun getAddOrNotWishListLiveData(addOrNotWishListRequest: AddOrNotWishListRequest) {
        scope.launch {

            //post the value inside live data
            _getAddOrNotWishListLiveData.postValue(apiRepository.getAddOrNotWishListData(addOrNotWishListRequest))
        }
    }



}