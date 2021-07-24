package com.loyaltyworks.wavinseapp.ui.retailerorderrequest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustOrderDeliveryDetail
import com.loyaltyworks.wavinseapp.utils.AppController
import kotlinx.android.synthetic.main.row_retailer_order_request.view.*

class RetailerOrderRequestAdapter(
    var lstCustOrderDeliveryDetails: List<LstCustOrderDeliveryDetail>?,
    var callBackCategory: ClickOnItemClick
) : RecyclerView.Adapter<RetailerOrderRequestAdapter.ViewHolder>() {

    interface ClickOnItemClick {
        fun onClickOnItemClick(lstProductDetails: LstCustOrderDeliveryDetail)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val row_cardview = itemView.row_cardview as CardView
        val retailer_Name = itemView.retailer_Name as TextView
        val claim_id = itemView.claim_id as TextView
        val plumber_name = itemView.plumber_name as TextView
        val date = itemView.date as TextView
        val mobile_number = itemView.mobile_number as TextView
        val quantity = itemView.quantity as TextView

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_retailer_order_request,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holer: ViewHolder, position: Int) {

        val claimStatus = lstCustOrderDeliveryDetails?.get(position)

        holer.retailer_Name.text = "Distributor Name : " + claimStatus!!.DistributorName
        holer.claim_id.text = claimStatus.OrderNo
        holer.plumber_name.text = claimStatus.CustomerName
        holer.date.text = AppController.dateAPIFormat(claimStatus.OrderDate!!.split(" ")[0])
        holer.quantity.text = " QTY \n" + claimStatus.Quantity
        holer.mobile_number.text = claimStatus.CustomerMobile

        holer.row_cardview.setOnClickListener {
            callBackCategory.onClickOnItemClick(claimStatus)
        }


    }

    override fun getItemCount(): Int {
        return lstCustOrderDeliveryDetails!!.size
    }

}