package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList

import androidx.lifecycle.*
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseViewModel
import com.loyaltyworks.wavinseapp.utils.fetchData.db.WordRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: WordRepository) : BaseViewModel() {


    /*Product Category Listing */

    private val _productCategoryResponse = MutableLiveData<ProductCategoryResponse>()
    val productCategoryResponse: LiveData<ProductCategoryResponse> = _productCategoryResponse

    fun setProductCategoryRequest(productCategoryRequest: ProductCategoryRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _productCategoryResponse.postValue(apiRepository.getProductCategory(productCategoryRequest))
        }
    }

    /*Product Catalogue and Voucher Listing */
    private val _redeemGiftLiveData = MutableLiveData<RedeemGiftResponse>()
    val redeemGiftLiveData: LiveData<RedeemGiftResponse> = _redeemGiftLiveData

    fun setRedeemGiftRequest(redeemGiftRequest: RedeemGiftRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _redeemGiftLiveData.postValue(apiRepository.getRedeemGiftData(redeemGiftRequest))
        }
    }


    /*Product Catalogue and Voucher Listing */
    private val _redeemGiftVoucherResponse = MutableLiveData<RedeemGiftVoucherResponse>()
    val redeemGiftVoucherResponse: LiveData<RedeemGiftVoucherResponse> = _redeemGiftVoucherResponse


    fun setRedeemGiftVoucherRequest(redeemGiftVoucherRequest: RedeemGiftVoucherRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _redeemGiftVoucherResponse.postValue(apiRepository.getRedeemGiftVoucherRequest(redeemGiftVoucherRequest))
        }
    }


    /*Redeem Catalogue Product Request*/

    private val _redeemGiftCatalogueResponse = MutableLiveData<RedeemGiftCatalogueResponse>()
    val redeemGiftCatalogueResponse: LiveData<RedeemGiftCatalogueResponse> = _redeemGiftCatalogueResponse

    fun redeemCatalogueRequest(redeemGiftCatalogueRequest: RedeemGiftCatalogueRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _redeemGiftCatalogueResponse.postValue(apiRepository.getRedeemGiftCatalogueRequest(redeemGiftCatalogueRequest))
        }
    }

    /*Product Added to WishList*/

    private val _wishListAddedResponse = MutableLiveData<WishListAddedResponse>()
    val wishListAddedResponse: LiveData<WishListAddedResponse> = _wishListAddedResponse


    fun setWishListAddeddRequest(wishListAddedRequest: WishListAddedRequest) {
///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _wishListAddedResponse.postValue(apiRepository.getWishListAddedRequest(wishListAddedRequest))
        }
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    val allProducts: LiveData<List<ObjCatalogueList>> = repository.allProducts.asLiveData()

    val netPoints: LiveData<Int> = repository.netPoints.asLiveData()


    fun insertProduct(objCatalogueList: ObjCatalogueList) = viewModelScope.launch {
        repository.insertProduct(objCatalogueList)
    }

    fun checkAndInsert(ProductCode: String, objCatalogueList: ObjCatalogueList) = viewModelScope.launch {
        repository.checkAndInsert(ProductCode, objCatalogueList)
    }

    fun getProductById(productCode: String) = viewModelScope.launch {
        repository.getProductById(productCode)
    }

    fun updateProductQuantity(productCode: String, quantity: Int, netPoints: Int) = viewModelScope.launch {
        repository.updateProductQuantity(productCode, quantity, netPoints)
    }

    fun deleteProduct(productCode: String) = viewModelScope.launch {
        repository.deleteProduct(productCode)
    }

    fun deleteAllProduct() = viewModelScope.launch {
        repository.deleteAllProduct()
    }




}