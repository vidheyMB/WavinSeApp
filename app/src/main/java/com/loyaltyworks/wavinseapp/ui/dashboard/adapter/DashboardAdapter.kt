package com.loyaltyworks.wavinseapp.ui.dashboard.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.CustomerDetailsResponseJson
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity.Companion.TAG

class DashboardAdapter(var customerList: List<CustomerDetailsResponseJson>?, var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DashboardAdapter.MemberViewHolder>() {


    interface OnItemClickListener {
        fun onCustomerListClickResponse(customerList: CustomerDetailsResponseJson)

        fun onCustomerInteractionClickResponse(pos: Int, customerList: CustomerDetailsResponseJson)
    }


    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val customerName = itemView.findViewById<TextView?>(R.id.mem_list_username)
        val customerPoint = itemView.findViewById<TextView?>(R.id.mem_list_point)
        val customerGrade = itemView.findViewById<TextView?>(R.id.mem_list_tier)
        val createdBy = itemView.findViewById<TextView?>(R.id.mem_list_createdby)

        val SEHeader = itemView.findViewById<TextView?>(R.id.SEHeader)
        val ASMHeader = itemView.findViewById<TextView?>(R.id.ASMHeader)
        val SE = itemView.findViewById<TextView?>(R.id.mem_list_se)
        val ASM = itemView.findViewById<TextView?>(R.id.mem_list_asm)
        val memId = itemView.findViewById<TextView?>(R.id.mem_list_membershipId)
        val enrollDate = itemView.findViewById<TextView?>(R.id.mem_list_date)

        val activeStatus = itemView.findViewById<TextView?>(R.id.mem_list_status)
        val verifyStatus = itemView.findViewById<TextView?>(R.id.mem_list_approve)
        val location = itemView.findViewById<TextView?>(R.id.mem_list_branch)
        val activeStatusImg =
            itemView.findViewById<android.widget.ImageView?>(R.id.mem_list_activeImg)
        val verifyStatusImg =
            itemView.findViewById<android.widget.ImageView?>(R.id.mem_list_verifyImg)
        val mActiveHost = itemView.findViewById<LinearLayout?>(R.id.active_status_host)
        val profileImg =
            itemView.findViewById<android.widget.ImageView?>(R.id.general_profile_photo)
        val callIcon = itemView.findViewById<LinearLayout?>(R.id.call_icon)
        val interaction = itemView.findViewById<LinearLayout?>(R.id.interaction)

    }

    //Assign for lazy loader ...
    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val progressBar = view.findViewById<ProgressBar?>(R.id.rest_loader)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.member_list_row,
            parent,
            false
        )
        return MemberViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {


        if (holder is MemberViewHolder) {
            val CustomerDetailsResponseJson: CustomerDetailsResponseJson = customerList!!.get(
                position
            )
            val memberViewHolder: MemberViewHolder = holder as MemberViewHolder
            memberViewHolder.customerName.text = CustomerDetailsResponseJson.FullName
            if (!CustomerDetailsResponseJson.CustomerGrade.isNullOrEmpty())
                memberViewHolder.customerGrade.text = CustomerDetailsResponseJson.CustomerGrade
            else memberViewHolder.customerGrade.text = "-"
            if (!CustomerDetailsResponseJson.AsmName.isNullOrEmpty()) memberViewHolder.ASM.text =
                CustomerDetailsResponseJson.AsmName else memberViewHolder.ASM.text = "-"
            if (!CustomerDetailsResponseJson.SeName.isNullOrEmpty()) memberViewHolder.SE.text =
                CustomerDetailsResponseJson.SeName else memberViewHolder.SE.text = "-"
            if (!CustomerDetailsResponseJson.UserOneHeader.isNullOrEmpty())
                memberViewHolder.ASMHeader.text = CustomerDetailsResponseJson.UserOneHeader
            else memberViewHolder.ASMHeader.text = "-"
            if (!CustomerDetailsResponseJson.UserTwoHeader.isNullOrEmpty())
                memberViewHolder.SEHeader.text =
                    CustomerDetailsResponseJson.UserTwoHeader else memberViewHolder.SEHeader.text =
                "-"
            if (CustomerDetailsResponseJson.IsActive!!) {
                memberViewHolder.activeStatusImg.setImageDrawable(
                    holder.itemView.context.resources.getDrawable(R.drawable.ic_active)
                )
                memberViewHolder.activeStatus.text = "Active"
            } else {
                memberViewHolder.activeStatusImg.setImageDrawable(
                    holder.itemView.context.resources.getDrawable(R.drawable.ic_inactive)
                )
                memberViewHolder.activeStatus.text = "Inactive"
            }
            if (CustomerDetailsResponseJson.VerifiedTypeId == 1) {
                memberViewHolder.verifyStatusImg.setImageDrawable(
                    holder.itemView.context.resources.getDrawable(R.drawable.ic_active)
                )
                memberViewHolder.verifyStatus.text = CustomerDetailsResponseJson.VerifiedName
            } else {
                memberViewHolder.verifyStatusImg.setImageDrawable(
                    holder.itemView.context.resources.getDrawable(R.drawable.ic_inactive)
                )
                memberViewHolder.verifyStatus.text = CustomerDetailsResponseJson.VerifiedName
            }

            if(CustomerDetailsResponseJson.VerifiedName.equals("Verified", true)){
                memberViewHolder.verifyStatusImg.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.ic_active))
            }else{
                memberViewHolder.verifyStatusImg.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.ic_inactive))
            }

            if (!CustomerDetailsResponseJson.ProfilePicture.isNullOrEmpty()
            ) {
                Log.d(
                    "PICTUREIMAGE",
                    BuildConfig.PROMO_IMAGE_BASE + CustomerDetailsResponseJson.ProfilePicture
                )

//                //Picasso.with(context).load(AppController.getInstance().sharedRetrive(context, Constants.PROMO_IMAGE_BASE) + CustomerDetailsResponseJson.getProfilePicture()).transform(new CircleOnLineView()).into(memberViewHolder.profileImg);
//                Picasso.with(context)
//                    .load(Constants.PROMO_IMAGE_BASE + CustomerDetailsResponseJson.getProfilePicture())
//                    .transform(CircleOnLineView()).into(memberViewHolder.profileImg)

                Glide.with(holder.itemView.context)
                    .load(BuildConfig.PROFILE_IMAGE_BASE + CustomerDetailsResponseJson.ProfilePicture!!.replace("~/UploadFiles/CustomerImage/",""))
                    .transform(CircleCrop())
                    .into(
                        memberViewHolder.profileImg
                    )
                Log.d(TAG, "onBindViewHolder: ${BuildConfig.PROFILE_IMAGE_BASE + CustomerDetailsResponseJson.ProfilePicture}")
                memberViewHolder.profileImg.setPadding(0, 0, 0, 0)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    memberViewHolder.profileImg.background = holder.itemView.context.resources
                        .getDrawable(R.drawable.round_profile_bg)
                } else {
                }
                memberViewHolder.profileImg.setImageDrawable(
                    holder.itemView.context.resources.getDrawable(
                        R.drawable.ic_person
                    )
                )
                memberViewHolder.profileImg.setPadding(8, 8, 8, 8)
            }
            memberViewHolder.enrollDate.text = CustomerDetailsResponseJson.CreatedDate
            memberViewHolder.memId.text = CustomerDetailsResponseJson.LoyaltyId
            memberViewHolder.createdBy.text = CustomerDetailsResponseJson.CreatedByName
            memberViewHolder.location.text = CustomerDetailsResponseJson.LocationName
            memberViewHolder.customerPoint.text =
                CustomerDetailsResponseJson.PointsBalance.toString()
            holder.itemView.setOnClickListener {
                onItemClickListener.onCustomerListClickResponse(
                    CustomerDetailsResponseJson
                )
            }
            memberViewHolder.interaction.visibility = View.GONE
            memberViewHolder.callIcon.visibility = View.VISIBLE
            (holder as MemberViewHolder).callIcon.setOnClickListener(
                View.OnClickListener {
                    holder.itemView.context.startActivity(
                        Intent(
                            Intent.ACTION_DIAL,
                            Uri.fromParts("tel", CustomerDetailsResponseJson.Mobile, null)
                        )
                    )
                })
            (holder as MemberViewHolder).interaction.setOnClickListener(
                View.OnClickListener { v: View? ->
                    onItemClickListener.onCustomerInteractionClickResponse(
                        position,
                        CustomerDetailsResponseJson
                    )
                })


        } /*else if (holder is LoadingViewHolder) {
            val loadingViewHolder = holder as LoadingViewHolder
            if (position == 0) {
                Toast.makeText(holder.itemView.context, "loading...", Toast.LENGTH_SHORT).show()
                loadingViewHolder.progressBar.visibility = View.VISIBLE
                loadingViewHolder.progressBar.isIndeterminate = true
            } else {
                loadingViewHolder.progressBar.visibility = View.GONE
            }
        }*/

    }

    override fun getItemCount(): Int {
        return customerList!!.size
    }


}