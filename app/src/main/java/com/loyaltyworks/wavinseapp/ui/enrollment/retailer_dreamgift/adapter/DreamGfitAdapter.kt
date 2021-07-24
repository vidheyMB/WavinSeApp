package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_dreamgift.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.numberprogressbar.NumberProgressBar
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstDreamGift
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.dialogBox.DeactivateDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.DialogueCallBack
import kotlinx.android.synthetic.main.row_my_dream_gift.view.*
import kotlinx.android.synthetic.main.row_offer_and_promotions.view.*
import java.util.*
import kotlin.math.roundToInt

class DreamGfitAdapter(
    var lstDreamGift: List<LstDreamGift>?,
    var callBackDreamGift: ClickOnItemList
) : RecyclerView.Adapter<DreamGfitAdapter.ViewHolder>() {

    interface ClickOnItemList {
        fun onClickDreamGiftDetail(position: Int, dreamGift: List<LstDreamGift?>?)
        fun onClickDreamGiftRemove(
            position: Int,
            dreamGiftArrayList: List<LstDreamGift?>?,
            dreamGiftID: String?,
            dreamGiftStatusID: String?
        )

        fun onClickDreamGiftRedeem(
            position: Int,
            dreamGift: List<LstDreamGift?>?,
            dreamGiftID: String?
        )

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val giftName_tv = itemView.giftName_tv as TextView
        val remarks = itemView.remarks as TextView
        val gift_type_tv = itemView.gift_type_tv as TextView
        val points_req_tv = itemView.points_req_tv as TextView
        val desired_date = itemView.desired_date as TextView
        val created_date = itemView.created_date as TextView
        val redem_btn = itemView.redem_btn as Button
        val detail_btn = itemView.detail_btn as AppCompatButton
        val cancel_img = itemView.cancel_img as ImageView
        val dreamgift_status = itemView.dreamgift_status as TextView
        val number_progress_bar = itemView.number_progress_bar as NumberProgressBar


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_my_dream_gift,
            parent,
            false
        )
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dreamGift = lstDreamGift?.get(position)


        holder.giftName_tv.text = dreamGift!!.DreamGiftName
        holder.gift_type_tv.text = "Gift Type : " + dreamGift.GiftType
        holder.points_req_tv.text = "Points Required : " + dreamGift.PointsRequired as Int
        holder.dreamgift_status.text = "Status : " + dreamGift.Status

        if (dreamGift.Status.equals("Rejected")) {
            holder.remarks.visibility = View.VISIBLE
            holder.remarks.text = "Remarks : " + dreamGift.Remark
        } else {
            holder.remarks.visibility = View.GONE
        }

        holder.desired_date.text =
            "Desired Date : " + AppController.dateUIFormat(dreamGift.JDesiredDate!!.split(" ")[0])
        holder.created_date.text =
            "Requested Date : " + AppController.dateUIFormat(dreamGift.JCreatedDate!!.split(" ")[0])

        val points = dreamGift.PointsRequired
        val currentPoints = dreamGift.PointsBalance

        if (currentPoints == 0) {
            holder.detail_btn.visibility = View.GONE
            holder.redem_btn.visibility = View.GONE
        } else {
            holder.detail_btn.visibility = View.VISIBLE
            holder.redem_btn.visibility = View.VISIBLE
        }

        if (currentPoints!! >= points && dreamGift.Status.equals("Approved")) {
            holder.number_progress_bar.progress = 100
            holder.redem_btn.isEnabled = true
            holder.redem_btn.setBackgroundResource(R.drawable.button_style)
        } else {
            holder.number_progress_bar.progress =
                (currentPoints.toFloat() / points * 100).roundToInt()
            holder.redem_btn.isEnabled = false
            holder.redem_btn.setBackgroundResource(R.drawable.graybutton)
        }

        if (dreamGift.Status.equals("Redeemed"))
            holder.number_progress_bar.progress = 100


        holder.detail_btn.setOnClickListener {
            callBackDreamGift.onClickDreamGiftDetail(
                position,
                lstDreamGift
            )
        }

        holder.redem_btn.setOnClickListener {
            callBackDreamGift.onClickDreamGiftRedeem(
                position,
                lstDreamGift,
                dreamGift.DreamGiftId.toString()
            )
        }




        holder.cancel_img.setOnClickListener(View.OnClickListener {
            if (BlockMultipleClick.click()) {
                return@OnClickListener
            }


            DeactivateDialogue.showPopUpDialog(
                holder.itemView.context,
                true,
                "No",
                "Yes",
                "Are you sure you want to cancel\n the dream gift?",
                object : DialogueCallBack {
                    override fun onResponse(response: kotlin.String) {

                    }

                    override fun onAgain() {
                        callBackDreamGift.onClickDreamGiftRemove(
                            position,
                            lstDreamGift,
                            dreamGift.DreamGiftId.toString(),
                            dreamGift.GiftStatusId.toString()
                        )
                    }


                })


        })


    }

    override fun getItemCount(): Int {
        return lstDreamGift!!.size
    }


}