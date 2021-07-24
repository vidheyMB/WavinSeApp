package com.loyaltyworks.wavinseapp.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.loyaltyworks.wavinseapp.R

object ActivateAccountDialogue {

    private var dialog: Dialog?= null

    interface SuccessCallBack {
        fun onOk()
    }

    fun showSuccessPopUpDialog(context: Context ,msgText :String, SuccessCallBack: SuccessCallBack) {

        if(dialog != null) return

        dialog =  Dialog(context, R.style.Theme_Dialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        val window = dialog!!.window
        window!!.setGravity(Gravity.CENTER)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        dialog?.setContentView(R.layout.activation_success_dialog)
        dialog?.show()

        val ok = dialog?.findViewById<View>(R.id.textOk) as TextView

        ok.setOnClickListener {
            SuccessCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }

    }

}