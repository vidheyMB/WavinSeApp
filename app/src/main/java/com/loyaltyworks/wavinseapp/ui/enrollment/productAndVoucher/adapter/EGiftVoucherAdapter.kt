package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.model.ObjCatalogueFixedPoint
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import com.loyaltyworks.wavinseapp.model.RedeemGiftResponse
import kotlinx.android.synthetic.main.row_e_gift_voucher.view.*
import java.util.*

class EGiftVoucherAdapter(
    var redeemGiftResponse: RedeemGiftResponse,
    var onItemClickListener: voucherListAdpaterCallback
) : RecyclerView.Adapter<EGiftVoucherAdapter.ViewHolder>() {

    var mCataloguePointMap: Map<String, ArrayList<String>>? = null
    var defaultData = arrayOf("0")


    var objCatalogueFixedPoint: ObjCatalogueFixedPoint? = null


    /*CUST_ACCOUNT_TYPE	1	Individual
    CUST_ACCOUNT_TYPE	2	Shared*/
    var amount = "0"

    interface voucherListAdpaterCallback {
        fun onRedeemVoucherFromAdapter(CatalogueVouchers: ObjCatalogueList, amount: String)
        fun onAddToWishListVoucherFromAdapter(
            CatalogueVouchers: ArrayList<ObjCatalogueList>,
            pos: Int,
            mailid: String?,
            redeemablePoint: String?,
            name: String?
        )
        fun onDetailVoucherFromAdapter(CatalogueVouchers: ObjCatalogueList)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val redeem = itemView.redeem
        val amount = itemView.amount
        val mSpinnerHost = itemView.mng_price_spinner
        val voucher_range = itemView.voucher_range
        val voucher_name = itemView.voucher_name
        val voucher_image = itemView.voucher_image












    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_e_gift_voucher,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val redeemGiftResponses = redeemGiftResponse.ObjCatalogueList!![position]


        holder.voucher_name.text = redeemGiftResponses.ProductName
//        holder.voucher_range.text = "${redeemGiftResponses.CountryCurrencyCode} ${redeemGiftResponses.min_points.toString()} - ${redeemGiftResponses.max_points.toString()}"



        if(redeemGiftResponses.Product_type == 1) {

            holder.voucher_range.text = "INR ${redeemGiftResponses.min_points.toString()} - ${redeemGiftResponses.max_points.toString()}"
            holder.amount.visibility = View.VISIBLE
            holder.mSpinnerHost.visibility = View.GONE
            holder.mSpinnerHost.adapter = ArrayAdapter(
                holder.itemView.context,
                android.R.layout.simple_spinner_item,
                defaultData
            )

            holder.redeem.visibility = View.VISIBLE

        }else{

            holder.voucher_range.visibility = View.INVISIBLE
            holder.amount.setText("")
            holder.amount.visibility = View.GONE

            if (!redeemGiftResponse.ObjCatalogueFixedPoints.isNullOrEmpty()) {
//                if (redeemGiftResponse.ObjCatalogueFixedPoints != null)
                    holder.mSpinnerHost.adapter =
                    VoucherPointsAdapter(
                        holder.itemView.context,/*redeemGiftResponses.ObjCatalogueFixedPoints!![0].ProductCode!!*/
                        redeemGiftResponses.ObjCatalogueFixedPoints!!
                    )/* else holder.mSpinnerHost.adapter = ArrayAdapter(
                    holder.itemView.context,
                    R.layout.spinner_row_small_size,
                    defaultData
                )*/
            } else holder.mSpinnerHost.adapter = ArrayAdapter(
                holder.itemView.context,
                R.layout.spinner_row_small_size,
                defaultData
            )

            holder.mSpinnerHost.visibility = View.VISIBLE


        }



        if (redeemGiftResponses.ProductImage != null) {
            try {

                Glide.with(holder.itemView.context).asBitmap().error(R.drawable.ic_baseline_photo_size_select_actual_24)
                    .placeholder(R.drawable.ic_baseline_photo_size_select_actual_24).load(
                        redeemGiftResponses.ProductImage!!
                    )
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .optionalFitCenter().into(holder.voucher_image)

               holder.voucher_image.setPadding(0, 0, 0, 0)

            } catch (e: Exception) {}


            holder.redeem.setOnClickListener {
                if(BlockMultipleClick.click()) return@setOnClickListener


//            String name = holder.mName.getText().toString();
//            String email = holder.mEmail.getText().toString();
//            name= AppController.getInstance().getLoginUserDetails(context).getFirstName();
//            email=AppController.getInstance().getLoginUserDetails(context).getEmail();
                if (holder.amount.text.toString().isNotEmpty()) amount = holder.amount.text.toString()

                if (redeemGiftResponses.Product_type == 0)
                    amount = objCatalogueFixedPoint!!.FixedPoints.toString()


                onItemClickListener.onRedeemVoucherFromAdapter(redeemGiftResponse.ObjCatalogueList!![position], amount)

            }

            holder.itemView.setOnClickListener {
                if(BlockMultipleClick.click()) return@setOnClickListener
                onItemClickListener.onDetailVoucherFromAdapter(redeemGiftResponse.ObjCatalogueList!![position])
            }

        }



        holder.mSpinnerHost?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                objCatalogueFixedPoint = parent!!.getItemAtPosition(position) as ObjCatalogueFixedPoint

                Log.d("fjdskf", objCatalogueFixedPoint!!.FixedPoints.toString())


            }

        }


    }


    override fun getItemCount(): Int {
        return redeemGiftResponse.ObjCatalogueList?.size!!
    }

}