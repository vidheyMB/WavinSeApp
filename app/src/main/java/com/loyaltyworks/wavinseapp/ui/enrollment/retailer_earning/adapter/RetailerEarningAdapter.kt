package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_earning.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.model.MyEarningResponse
import kotlinx.android.synthetic.main.row_my_earning.view.*

class RetailerEarningAdapter(
    var myearningResponse: MyEarningResponse
) : RecyclerView.Adapter<RetailerEarningAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transaction_date = itemView.transaction_date
        //        val product_name = itemView.product_name
//        val qr_code = itemView.qr_code
//        val product = itemView.product
//        val qrcode = itemView.qrcode
        val points_tv = itemView.points_tv
        val remarks = itemView.remarks
        val remark = itemView.remark
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_my_earning,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rewardTransDetails = myearningResponse.lstRewardTransJsonDetails!![position]
        holder.points_tv.text = rewardTransDetails.RewardPoints
        holder.transaction_date.text = AppController.dateUIFormat(rewardTransDetails.JTranDate!!.split(" ")[0])
        holder.remarks.text = rewardTransDetails.Remarks
    }

    override fun getItemCount(): Int {
        return myearningResponse.lstRewardTransJsonDetails?.size!!
    }

}