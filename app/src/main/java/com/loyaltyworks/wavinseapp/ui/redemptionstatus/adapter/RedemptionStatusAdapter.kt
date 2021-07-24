package com.loyaltyworks.wavinseapp.ui.redemptionstatus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.ObjCatalogueRedemReq
import com.loyaltyworks.wavinseapp.utils.AppController

class RedemptionStatusAdapter(
    var catRedemList: List<ObjCatalogueRedemReq>?
) : RecyclerView.Adapter<RedemptionStatusAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val redemDate = itemView.findViewById<TextView?>(R.id.transaction_date_tv)
        val redemrefNo = itemView.findViewById<TextView?>(R.id.redem_ref_no_tv)
        val redemStatus = itemView.findViewById<TextView?>(R.id.txt_status)
        val prodName = itemView.findViewById<TextView?>(R.id.product_tv)
        val prodCategory = itemView.findViewById<TextView?>(R.id.catalogue_type_tv)
        val points = itemView.findViewById<TextView?>(R.id.points_tv)

        //        val redemDetails = itemView.findViewById<TextView?>(R.id.more_detail)
        val name = itemView.findViewById<TextView?>(R.id.customer_name)
        val memId = itemView.findViewById<TextView?>(R.id.loyalty_id)
        val asm = itemView.findViewById<TextView?>(R.id.asm)
        val se = itemView.findViewById<TextView?>(R.id.se)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_redemptions_status,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val rewards = catRedemList?.get(position)

        holder.redemrefNo.text = "Redemption.Ref.No : " + rewards!!.RedemptionRefno

        if (rewards.Status!!.equals("OutforDelivery")) {
            holder.redemStatus.text = "Status : " + "Out for Delivery"
        } else if (rewards.Status.equals("AddressVerified")) {
            holder.redemStatus.text = "Status : " + "Address Verified"
        } else {

            when (rewards.Status) {
                0 -> {
                    holder.redemStatus.text = "Status : Pending"
                }
                2 -> {
                    holder.redemStatus.text = "Status : Processed"

                }
                3 -> {
                    holder.redemStatus.text = "Status : Cancelled"

                }
                4 -> {
                    holder.redemStatus.text = "Status : Delivered"

                }
                7 -> {
                    holder.redemStatus.text = "Status : Returned"

                }
                8 -> {
                    holder.redemStatus.text = "Status : Redispatched"

                }
                9 -> {
                    holder.redemStatus.text = "Status : OnHold"

                }
                10 -> {
                    holder.redemStatus.text = "Status : Dispatched"

                }
                11 -> {
                    holder.redemStatus.text = "Status : Out for Delivery"

                }
                12 -> {
                    holder.redemStatus.text = "Status : Address Verified"

                }
            }


        }


        holder.prodName.text = "Product : " + rewards.ProductName
        holder.redemrefNo.text = "Ref.No : " + rewards!!.RedemptionRefno
        holder.points.text = "Points : " + rewards.RedemptionPoints
        holder.redemDate.text =
            "Trxn Date : " + AppController.dateUIFormat(rewards.JRedemptionDate!!.split(" ")[0])

        holder.asm.visibility = View.VISIBLE
        holder.se.visibility = View.VISIBLE
        holder.name.visibility = View.VISIBLE
        holder.memId.visibility = View.VISIBLE

        if (rewards.ASM != null && rewards.ASM.isNotEmpty()) {
            holder.asm.text = "DPO : " + rewards.ASM
        } else {
            holder.asm.text = "DPO : -"
        }

        if (rewards.SE != null && !rewards.SE.isNullOrEmpty()) {
            holder.se.text = "DPE : " + rewards.SE
        } else {
            holder.se.text = "DPE : -"
        }

        holder.name.text = "Name : " + rewards.FullName
        holder.memId.text = "ID : " + rewards.LoyaltyId.toString()

        if (rewards.RedemptionType === 1) {
            holder.prodCategory.text = "Catalogue Type : Product"
        } else if (rewards.RedemptionType === 3) {
            holder.prodCategory.text = "Catalogue Type : Dream Gift"
        } else if (rewards.RedemptionType === 4) {
            holder.prodCategory.text = "Catalogue Type : Voucher"
        } else {
            holder.prodCategory.text = "Catalogue Type : -"
        }

    }

    override fun getItemCount(): Int {
        return catRedemList!!.size
    }

}