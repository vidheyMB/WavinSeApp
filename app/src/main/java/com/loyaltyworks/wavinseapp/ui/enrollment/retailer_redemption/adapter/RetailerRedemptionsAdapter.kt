package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_redemption.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.model.MyRedemptionResponse
import kotlinx.android.synthetic.main.row_my_redemptions.view.*

class RetailerRedemptionsAdapter(
    var myRedemptionResponse: MyRedemptionResponse
) : RecyclerView.Adapter<RetailerRedemptionsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val transaction_date_tv = itemView.date
        val redemption_ref_no = itemView.redemption_ref_no
        val product_name = itemView.product_name
        val catalogue_type = itemView.catalogue_type
        val status = itemView.status
        val points_tv = itemView.points_tv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_my_redemptions,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val rewardTransDetails = myRedemptionResponse.LstGiftCardIssueDetailsJson!![position]

        holder.transaction_date_tv.text = AppController.dateToNewUIFormats(rewardTransDetails.JTransactiondate!!.split(" ")[0])
        holder.redemption_ref_no.text = rewardTransDetails.TransactionNo
        holder.catalogue_type.text = rewardTransDetails.TransactionType
        holder.status.text = rewardTransDetails.Status
        holder.product_name.text = rewardTransDetails.ProductName
        holder.points_tv.text = rewardTransDetails.PointsAwarded.toString()

    }

    override fun getItemCount(): Int {
        return myRedemptionResponse.LstGiftCardIssueDetailsJson!!.size
    }

}