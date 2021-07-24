package com.loyaltyworks.wavinseapp.utils

import android.content.Context
import android.text.format.DateUtils

object AppController {

    fun dateFormat(reqDate: String): String {
        var year:String = ""
        var day:String = ""
        var month:String = ""
        if(reqDate.isNotEmpty()) {
             year = reqDate.split("/")[2]
             day = reqDate.split("/")[0]
             month = reqDate.split("/")[1]
            return "$year-$month-$day"
        }else{
            return ""
        }
    }


    fun dateUIFormat(reqDate: String): String? {
        val year = reqDate.split("/".toRegex()).toTypedArray()[2]
        val day = reqDate.split("/".toRegex()).toTypedArray()[1]
        val month = reqDate.split("/".toRegex()).toTypedArray()[0]
        return "$day/$month/$year"
    }


    fun dateAPIFormats(reqDate: String?): String? {
        if (reqDate != null && !reqDate.isEmpty()) {
            val dateFor: String = reqDate
            return dateFor.split("/".toRegex())
                .toTypedArray()[2] + "-" + dateFor.split("/".toRegex())
                .toTypedArray()[1] + "-" + dateFor.split("/".toRegex()).toTypedArray()[0]
        }
        return reqDate
    }

    fun dateAPIFormat(reqDate: String?): String? {
        return reqDate?.split("/".toRegex())!!
            .toTypedArray()[1] + "/" + reqDate.split("/".toRegex())
            .toTypedArray()[0] + "/" + reqDate.split("/".toRegex()).toTypedArray()[2]
    }


    fun dateNewAPIFormat(reqDate: String?): String? {
        return reqDate?.split("/".toRegex())!!
            .toTypedArray()[1] + "/" + reqDate.split("/".toRegex())
            .toTypedArray()[0] + "/" + reqDate.split("/".toRegex()).toTypedArray()[2]
    }


    fun appOpenedToday(context: Context): Boolean {
        return DateUtils.isToday(PreferenceHelper.getLongValue(context, "TodayDate"))
    }

    fun dateToNewUIFormatss(reqDate: String): String? {  // input date : mm/dd/yyyy hh:MM:ss am/pm
        val dateFor = reqDate.split(" ".toRegex()).toTypedArray()[0]
        val time = reqDate.split(" ".toRegex()).toTypedArray()[1]
        val ampm = reqDate.split(" ".toRegex()).toTypedArray()[2]
        return dateFor.split("/".toRegex()).toTypedArray()[1] + "/" + dateFor.split("/".toRegex())
            .toTypedArray()[0] + "/" + dateFor.split(
            "/".toRegex()
        ).toTypedArray()[2] + " " + time + " " + ampm

//        return reqDate;
    }

    fun dateToNewUIFormats(reqDate: String): String? {  // input date : mm/dd/yyyy hh:MM:ss am/pm
        return reqDate.split("/".toRegex()).toTypedArray()[1] + "/" + reqDate.split("/".toRegex())
            .toTypedArray()[0] + "/" + reqDate.split("/".toRegex()).toTypedArray()[2]
    }


  /*  fun shareApp(context: Activity, videoItem: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        //        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share to your friends by using this \n\n"+Constants.PLAY_STORE_LINK);
        shareIntent.putExtra(
            Intent.EXTRA_TEXT, """
     Referral Code is : $videoItem
     
     Share to your friends by using this 
     
     ${BuildConfig.PLAY_STORE_LINK}
     """.trimIndent()
        )
        context.startActivityForResult(Intent.createChooser(shareIntent, "Refer and Earn"), 101)
    }*/


    /*Open Chat listing to order */
//    fun whatsapp(context: Context, phone: String) {
//        val formattedNumber = phone
//        try {
//            val packageManager = context.packageManager
//            val i = Intent(Intent.ACTION_VIEW)
//            val url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(
//                "",
//                "UTF-8"
//            )
//            i.setPackage("com.whatsapp")
//            i.data = Uri.parse(url)
//            if (i.resolveActivity(packageManager) != null) {
//                context.startActivity(i)
//            } else Toast.makeText(
//                context,
//                context.resources.getString(R.string.whatsapp_is_not_installed_in_your_phone),
//                Toast.LENGTH_SHORT
//            ).show()
//        } catch (e: Exception) {
//            Toast.makeText(
//                context,
//                context.resources.getString(R.string.whatsapp_is_not_installed_in_your_phone),
//                Toast.LENGTH_SHORT
//            ).show()
//            e.printStackTrace()
//        }
//    }


}