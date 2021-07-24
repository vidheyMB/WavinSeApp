package com.loyaltyworks.wavinseapp.ui.retailerorderrequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustOrderDeliveryDetail
import com.loyaltyworks.wavinseapp.model.OrderDetailsRequest
import com.loyaltyworks.wavinseapp.ui.retailerorderrequest.adapter.RetailerOrderRequestDetailAdapter
import com.loyaltyworks.wavinseapp.utils.AppController
import kotlinx.android.synthetic.main.fragment_retailer_order_request_details.*
import kotlinx.android.synthetic.main.row_retailer_order_request.*


class RetailerOrderRequestDetailsFragment : Fragment() {

    var retailerOrderRequestDetailsAdapter: RetailerOrderRequestDetailAdapter? = null

    var lstCustOrderDeliveryDetails: LstCustOrderDeliveryDetail? = null

    private lateinit var viewModel: RetailerOrderRequestViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(RetailerOrderRequestViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retailer_order_request_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null)
            lstCustOrderDeliveryDetails =
                requireArguments().getSerializable("LSTCUSTOMER_DELIVERY_DETIALS") as LstCustOrderDeliveryDetail

        forword_arrow.visibility = View.INVISIBLE
        retailer_Name.text = "Distributor Name : " + lstCustOrderDeliveryDetails!!.DistributorName
        claim_id.text = lstCustOrderDeliveryDetails!!.OrderNo
        plumber_name.text = lstCustOrderDeliveryDetails!!.CustomerName
        date.text = AppController.dateAPIFormat(lstCustOrderDeliveryDetails!!.OrderDate!!.split(" ")[0])
        quantity.text = " QTY \n" + lstCustOrderDeliveryDetails!!.Quantity
        mobile_number.text = lstCustOrderDeliveryDetails!!.CustomerMobile


        OrderDetilsList()
    }

    private fun OrderDetilsList() {

        viewModel.getOrderDetailsList(
            OrderDetailsRequest(
                ActionType = "7",
                ActorId = lstCustOrderDeliveryDetails!!.CustomerUserId.toString(),
                OrderId = lstCustOrderDeliveryDetails!!.OrderID.toString()
            )
        )


        viewModel.getOrderDetailsLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.lstCustOrderDeliveryDetails.isNullOrEmpty()) {
                order_details_error.visibility = View.GONE
                order_claim_status_recycler.visibility = View.VISIBLE

                order_claim_status_recycler.adapter = RetailerOrderRequestDetailAdapter(
                    it.lstCustOrderDeliveryDetails
                )

            } else {

                order_details_error.visibility = View.VISIBLE
                order_claim_status_recycler.visibility = View.GONE
            }

        })

    }


}