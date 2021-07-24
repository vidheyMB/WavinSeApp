package com.loyaltyworks.wavinseapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.utils.dialogBox.ProgressDialogue
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


/**
 *   Convert bytes to pdf
 */

fun writeBytesAsPdf(name: String, bytes: ByteArray) {
    val path = ApplicationClass.appContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
    var file = File(path, "$name.pdf")
    var os = FileOutputStream(file)
    os.write(bytes)
    os.close()
}

fun writeBas64AsPdf(activity: Activity, name: String, data: String, pdfPreview: Boolean) {
    val downloadFolder = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
    val dwldsPath: File = File(downloadFolder, "/$name.pdf")
    val pdfAsBytes: ByteArray = Base64.decode(data, 0)
    val os: FileOutputStream
    os = FileOutputStream(dwldsPath, false)
    os.write(pdfAsBytes)
    os.flush()
    os.close()

//    if(pdfPreview){
//        openPDF("$name.pdf")
//    }
}

fun pdfCreationFromBase64(context: Context, productName: String, res: String?) {
    if (res == null || res.isEmpty()) {
        return
    }
    val fileName = productName + "_" + SimpleDateFormat(
        "yyyyMMdd_HHmmss",
        Locale.getDefault()
    ).format(System.currentTimeMillis())

    Log.d("TAG", "onClickDownloadEWarranty: $fileName")
    val dwldsPath =
        File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + fileName + ".pdf")

    val pdfAsBytes = Base64.decode(res, 0)
    val os: FileOutputStream
    try {
        os = FileOutputStream(dwldsPath, false)
        os.write(pdfAsBytes)
        os.flush()
        os.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val apkURI = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider",
                dwldsPath
            )
            intent.setDataAndType(apkURI, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            intent.setDataAndType(Uri.fromFile(dwldsPath), "application/pdf")
        }
        ProgressDialogue.dismissDialog()
        context.startActivity(intent)

    } catch (e: ActivityNotFoundException) {
    }
}


fun bitmapToBase64Convert(bitmap: Bitmap): String? {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream) //compress to which format you want.
    val byte_arr = stream.toByteArray()
    return Base64.encodeToString(byte_arr, Base64.NO_WRAP)
}


@RequiresApi(Build.VERSION_CODES.P)
fun getCapturedImageAsBitmap(selectedPhotoUri: Uri): Bitmap {
    return when {
        Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
            ApplicationClass.appContext?.contentResolver,
            selectedPhotoUri
        )
        else -> {
            val source =
                ImageDecoder.createSource(
                    ApplicationClass.appContext?.contentResolver!!,
                    selectedPhotoUri
                )
            ImageDecoder.decodeBitmap(source)
        }
    }
}

fun getResizedBitmap(image: Bitmap?, maxSize: Int): Bitmap {
    var width = image?.width
    var height = image?.height
    val bitmapRatio = width!!.toFloat() / height!!.toFloat()
    if (bitmapRatio > 1) {
        width = maxSize
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxSize
        width = (height * bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(image!!, width, height, true)
}

@SuppressLint("SimpleDateFormat")
fun getLastDayOfMonth(dates: String): String {

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val dateString = dates
        var convertedDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("d/M/yyyy"))
        convertedDate = convertedDate.withDayOfMonth(convertedDate.month.length(convertedDate.isLeapYear))
        System.out.println("dateofmonth " + convertedDate.toString().split("-")[2])
        return convertedDate.toString().split("-")[2]

    }else{

        val date = dates
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val convertedDate = dateFormat.parse(date)
        val c = Calendar.getInstance()
        c.time = convertedDate
        c[Calendar.DAY_OF_MONTH] = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        System.out.println("dateofmonth2 " + c[Calendar.DAY_OF_MONTH].toString())
        return  c[Calendar.DAY_OF_MONTH].toString()
    }
}

/** Currency formatter*/
fun currencyFormat(amount: String?): String? {
    return try {
        val formatter = DecimalFormat("###,###,##0.00")
        formatter.format(amount?.toDouble())
    } catch (e: Exception) {
        e.printStackTrace()
        "0.00"
    }
}