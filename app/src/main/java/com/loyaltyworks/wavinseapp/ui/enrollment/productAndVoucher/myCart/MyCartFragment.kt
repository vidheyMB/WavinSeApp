package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.myCart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.CartAdapter
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import kotlinx.android.synthetic.main.my_cart_fragment.*

class MyCartFragment : Fragment(), CartAdapter.OnRemoveCallBack {

    var netPoints:Int = 0
    var redeemablePoints:Int = 0

    private lateinit var cartAdapter: CartAdapter

    private val viewModel : ProductViewModel by viewModels{
        ViewModelFactory((activity?.application as ApplicationClass).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Redeemable point balance
        redeemablePoints = PreferenceHelper.getCustomerDashboard(requireContext())!!.ObjCustomerDashboardList!![0].RedeemablePointsBalance!!

        return inflater.inflate(R.layout.my_cart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.netPoints.observe(viewLifecycleOwner, Observer {
            if (it != null)
                netPoints = it
            if (it != null && it >= 500) {
                checkout_btn.isEnabled = true
                checkout_btn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.button_bg))
                minimum_value.visibility = View.GONE
            } else {
                checkout_btn.isEnabled = false
                minimum_value.visibility = View.VISIBLE
                checkout_btn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.button_bg_grey))
            }
        })


        viewModel.allProducts.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                cartAdapter.submitList(it)
                checkout_btn.visibility = View.VISIBLE
                mEmptyView.visibility = View.GONE
            } else {
                cartAdapter.submitList(it)
                checkout_btn.visibility = View.GONE
                minimum_value.visibility = View.GONE
                mEmptyView.visibility = View.VISIBLE
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        redeemable_points.text = "Points  "+redeemablePoints?.toString()

        // set recyclerView
        cartAdapter = CartAdapter(this, viewModel, this)
        cartRecyclerview.adapter = cartAdapter


        checkout_btn.setOnClickListener {
            if (BlockMultipleClick.click()){
                return@setOnClickListener
            }

            if(cartAdapter.itemCount > 0 ){
                it.findNavController().navigate(R.id.action_myCartFragment_to_shippingAddressFragment)
            }
        }

    }

    override fun onRemoveCode(position: Int, objCatalogueList: ObjCatalogueList) {
        viewModel.deleteProduct(objCatalogueList.ProductCode!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


}