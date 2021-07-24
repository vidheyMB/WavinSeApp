package com.loyaltyworks.wavinseapp.ui.baseClass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.myCart.MyCartViewModel
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import com.loyaltyworks.wavinseapp.utils.fetchData.db.WordRepository

class ViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyCartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyCartViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}