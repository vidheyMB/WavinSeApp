package com.loyaltyworks.wavinseapp.ui.enrollment.ASMMaping

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.wavinseapp.R

class RetailerMappingFragment : Fragment() {

    companion object {
        fun newInstance() = RetailerMappingFragment()
    }

    private lateinit var viewModel: RetailerMappingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.retailer_mapping_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RetailerMappingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}