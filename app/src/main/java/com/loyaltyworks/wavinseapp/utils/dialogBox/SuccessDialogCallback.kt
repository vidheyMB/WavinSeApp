package com.loyaltyworks.wavinseapp.utils.dialogBox

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.loyaltyworks.wavinseapp.R

object SuccessDialogCallback {

        private var approveDialogue: SuccessDialogCallback? =
                null
        private var context: Context? = null
        var mDialogEditAddressVerification: Dialog? = null

        fun getInstance(): SuccessDialogCallback? {
                if (approveDialogue == null) approveDialogue = SuccessDialogCallback
                return approveDialogue
        }


        interface CustomApproveCallback {
                fun onClickOK(mDialogs: Dialog?)
        }

        @SuppressLint("StaticFieldLeak")
        fun setApproveDialogue(
                context: Context?,
                status:Boolean,
                msg: String,
                msgs: String,
                customApproveCallback: CustomApproveCallback
        ) {
                SuccessDialogCallback.context = context
                if (context == null) return
                if (mDialogEditAddressVerification != null) return
                mDialogEditAddressVerification = Dialog(context, R.style.Theme_Dialog)
                mDialogEditAddressVerification!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialogEditAddressVerification!!.setContentView(R.layout.success_below_popup_layout)
                mDialogEditAddressVerification!!.setCanceledOnTouchOutside(false)
                val textDialog = mDialogEditAddressVerification!!.findViewById<View>(R.id.textDialog) as TextView
                val approved = mDialogEditAddressVerification!!.findViewById<View>(R.id.approved) as TextView
                mDialogEditAddressVerification!!.setCancelable(false)

                val img = mDialogEditAddressVerification!!.findViewById<View>(R.id.img) as ImageView
                val textNo = mDialogEditAddressVerification!!.findViewById<View>(R.id.textNo) as TextView
                val window = mDialogEditAddressVerification!!.window

                window!!.setGravity(Gravity.CENTER)

                mDialogEditAddressVerification!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

                val layoutParams = mDialogEditAddressVerification!!.window!!.attributes
                layoutParams.windowAnimations = R.style.DialogAnimation
                val textOk = mDialogEditAddressVerification!!.findViewById<View>(R.id.textOk) as TextView
                textOk.setOnClickListener {
                        mDialogEditAddressVerification!!.dismiss()
                        mDialogEditAddressVerification = null
                        customApproveCallback.onClickOK(mDialogEditAddressVerification)
                }

                if(status){
                        textNo.visibility = View.VISIBLE
                        textOk.text = "YES"
                        textNo.text = "NO"
                }else{
                        textOk.text = "OK"
                        textNo.visibility = View.GONE
                }

                textDialog.text = msg
                Log.d(ContentValues.TAG, "setApproveDialogue: $msgs")


                if(msgs.contentEquals("New claim successfully submitted")){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_success))
                        approved.text = msg
                        textDialog.text = msgs
                }

                if(msgs.contentEquals("Do you want to redeem this voucher?")){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_group_4422))
                        approved.text = "Are you sure...? "
                        textDialog.text = msgs
                }

                if(msgs.contentEquals("Member is de-activated!")){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_group_4421))
                        approved.text = msg
                        textDialog.text = msgs
                }

                if(msgs.contentEquals("Unfortunately your redemption has not been completed. Your points redeemed will be reinstated shortly. Please try again later once")){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_group_4423))
                        approved.text = msg
                        textDialog.text = msgs
                }

                if(msg == "Thank you for redeeming."){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_success))
                        approved.text = msg
                        textDialog.text = "Your Product has successfully redeemed"

                }
                if(msgs == "The E-voucher will be sent to your registered email id shortly."){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_success))
                        approved.text = msg
                        textDialog.text = "The E-voucher will be sent to your registered email id shortly. "
                }

                if(msgs == "Claim successfully resubmitted"){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_success))
                        approved.text = msg
                        textDialog.text = msgs

                }

                if(msg.contentEquals("Thankyou...")){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_success))
                        approved.text = "Thank you..."
                        textDialog.text = msgs
                }

                if(msg.contentEquals("Redemption Failed")){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_success))
                        approved.text = "Oops..."
                        textDialog.text = msgs
                }

                if(msg == "Failed"){
                        img.setImageDrawable(context.getDrawable(R.drawable.ic_group_4423))
                        approved.text = msg
                        textDialog.text = msgs
                }

                textNo.setOnClickListener {
                        mDialogEditAddressVerification!!.dismiss()
                        mDialogEditAddressVerification = null

                }

                mDialogEditAddressVerification!!.show()
        }


}