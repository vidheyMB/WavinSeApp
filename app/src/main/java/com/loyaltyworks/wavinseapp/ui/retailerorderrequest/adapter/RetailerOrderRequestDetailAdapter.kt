package com.loyaltyworks.wavinseapp.ui.retailerorderrequest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustOrderDeliveryDetailss
import kotlinx.android.synthetic.main.row_plumber_claim_status_details.view.*

class RetailerOrderRequestDetailAdapter(
    var lstCustOrder: List<LstCustOrderDeliveryDetailss>?
) : RecyclerView.Adapter<RetailerOrderRequestDetailAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val d_product_name = itemView.d_product_name as TextView
        val qty_spn = itemView.qty_spn as TextView
        val d_status = itemView.d_status as TextView


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_plumber_claim_status_details,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val _listProductDetails = lstCustOrder?.get(position)

        holder.d_product_name.text = _listProductDetails!!.ProductName
        holder.d_status.text = _listProductDetails.OrderStatus
        holder.qty_spn.text = _listProductDetails.Quantity.toString()


    }

    override fun getItemCount(): Int {
        return lstCustOrder!!.size
    }

}