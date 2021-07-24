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

object DeactivateDialogue {
    private var dialog: Dialog?= null

    fun showPopUpDialog(context: Context, boolean: Boolean, closeText: String, OkText :String,msgText :String, dialogueCallBack: DialogueCallBack) {

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
        dialog?.setContentView(R.layout.deactive_active_custom_alert)
        dialog?.show()

        val mClose = dialog?.findViewById<View>(R.id.textCancel) as TextView
        val mText = dialog?.findViewById<View>(R.id.textDialog) as TextView
        val ok = dialog?.findViewById<View>(R.id.textOk) as TextView

        mText.text = msgText
        ok.text = OkText
        mClose.text = closeText

        if(!boolean) {
            mClose.visibility = View.GONE
        }

        mClose.setOnClickListener {
            dialog?.dismiss()
            dialog = null
        }

        ok.setOnClickListener {
            dialogueCallBack.onAgain()
            dialog?.dismiss()
            dialog = null
        }

    }

}