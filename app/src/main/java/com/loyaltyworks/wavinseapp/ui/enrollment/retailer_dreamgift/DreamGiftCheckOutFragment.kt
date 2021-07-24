package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_dreamgift

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.shippingAddress.ShippingAddressViewModel



class DreamGiftCheckOutFragment : Fragment() {


    private lateinit var viewModel: ShippingAddressViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dream_gift_check_out, container, false)
    }

}