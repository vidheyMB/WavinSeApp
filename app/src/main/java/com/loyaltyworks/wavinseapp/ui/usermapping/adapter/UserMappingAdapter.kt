package com.loyaltyworks.wavinseapp.ui.usermapping.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustParentChildMapping

class UserMappingAdapter(
    val lstCustParentChildMapping: List<LstCustParentChildMapping>?,
    val selelctedActionType: Int?,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UserMappingAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onRewardItemClickListenerCallback(
            position: Int,
            allGroups: List<LstCustParentChildMapping?>?,
            actionType: String
        )
    }

    var UserDetailsView: View? = null
    var EnrollmentView: View? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // user detials screen

        val userName = itemView.findViewById<TextView>(R.id.userName)
        val enrollCount = itemView.findViewById<TextView>(R.id.enrollCount)
        val userIdClick = itemView.findViewById<LinearLayout>(R.id.userIdClick)
        val enrollIdClick = itemView.findViewById<LinearLayout>(R.id.enrollIdClick)

        // enrollment details screen
        val mem_list_username = itemView.findViewById<TextView>(R.id.mem_list_username)
        val mem_list_mobile = itemView.findViewById<TextView>(R.id.mem_list_mobile)
        val mem_list_trxnCount = itemView.findViewById<TextView>(R.id.mem_list_trxnCount)
        val mem_list_points = itemView.findViewById<TextView>(R.id.mem_list_points)
        val mem_list_pointsRedeemed = itemView.findViewById<TextView>(R.id.mem_list_pointsRedeemed)
        val mem_list_rdemptionCount = itemView.findViewById<TextView>(R.id.mem_list_rdemptionCount)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (selelctedActionType == 1) {
            UserDetailsView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_usermapping_first, parent, false)
            ViewHolder(UserDetailsView!!)
        } else {
            EnrollmentView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_enrollment, parent, false)
            ViewHolder(EnrollmentView!!)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val rewards = lstCustParentChildMapping!![position]

        if (selelctedActionType == 1) {
            holder.userName.text = rewards.FirstName
            holder.enrollCount.text = rewards.TotalEnrollCount.toString()
            holder.userIdClick.setOnClickListener { v: View? ->
                onItemClickListener.onRewardItemClickListenerCallback(
                    position,
                    lstCustParentChildMapping,
                    "1"
                )
            }
            holder.enrollIdClick.setOnClickListener { v: View? ->
                onItemClickListener.onRewardItemClickListenerCallback(
                    position,
                    lstCustParentChildMapping,
                    "2"
                )
            }

        } else {

            holder.mem_list_username.text = rewards.FirstName
            holder.mem_list_mobile.text = rewards.Mobile
            holder.mem_list_trxnCount.text = rewards.TotalTransactionCount.toString()
            holder.mem_list_points.text = rewards.TotalPointsEarned.toString()
            holder.mem_list_rdemptionCount.text = rewards.TotalRedeemedCount.toString()
            holder.mem_list_pointsRedeemed.text = rewards.TotalPointsRedeemed.toString()

        }


    }

    override fun getItemCount(): Int {
        return lstCustParentChildMapping!!.size
    }

}