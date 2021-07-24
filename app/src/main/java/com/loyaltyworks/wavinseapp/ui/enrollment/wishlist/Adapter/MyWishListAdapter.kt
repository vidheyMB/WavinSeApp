package com.loyaltyworks.wavinseapp.ui.enrollment.wishlist.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.ObjCatalogue
import kotlinx.android.synthetic.main.row_my_wish_list.view.*

class MyWishListAdapter(var objCatalogue: List<ObjCatalogue>, var onItemClickListener: OnClickCallBack
) : RecyclerView.Adapter<MyWishListAdapter.ViewHolder>() {

    interface OnClickCallBack {
        fun onClickRedeemResponse(
            position: Int,
            dreamGiftArrayList: List<ObjCatalogue>,
            dreamGiftID: Int?,
            redeemBtn: CardView?
        )

        fun onClickDetialsResponse(
            position: Int,
            dreamGiftArrayList: List<ObjCatalogue>,
            dreamGiftID: Int?
        )

        fun onClickRemoveResponse(position: Int, dreamGiftArrayList: List<ObjCatalogue>)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myDreamGiftImg = itemView.my_dream_gift_img
        val dream_gift_name = itemView.dream_gift_name
        val dream_value = itemView.dream_value
        val point_required_description = itemView.point_required_description
        val dream_gift_redeem = itemView.dream_gift_redeem
        val dream_gift_details = itemView.dream_gift_details
        val remove = itemView.remove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_my_wish_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val _objCatalogue = objCatalogue[position]

        Glide.with(holder.itemView).asBitmap().error(R.drawable.ic_default_img)
            .placeholder(R.drawable.ic_default_img).load(
                BuildConfig.PROMO_IMAGE_BASE + "UploadFiles/CatalogueImages/" +_objCatalogue.ProductImage
            ).into(holder.myDreamGiftImg)


        holder.dream_gift_name.text = _objCatalogue.ProductName
        holder.dream_value.text = _objCatalogue.CashValue.toString()
//        holder.redemption_expected.text = _objCatalogue.ActualRedemptionDate.toString()

        if (_objCatalogue.PointReqToAcheiveProduct!!.toInt() <= 0 && _objCatalogue.RedeemablePointBalance!!.toInt() >= _objCatalogue.PointsRequired!!.toInt()) {
            holder.point_required_description.text =  "Congratulations! You are eligible to redeem your Dream Gift."
            holder.dream_gift_redeem.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.red))
            holder.dream_gift_redeem.isEnabled = true
            holder.dream_gift_redeem.isClickable = true
        }else{
            holder.dream_gift_redeem.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.grey))
            holder.dream_gift_redeem.isClickable = false
            holder.dream_gift_redeem.isEnabled = false
            val pointsReq = _objCatalogue.CashValue!! - _objCatalogue.RedeemablePointBalance!!
            holder.point_required_description.text = "You need " +  pointsReq + " more points to redeem"
        }

        holder.remove.setOnClickListener {
            onItemClickListener.onClickRemoveResponse(position, objCatalogue)
        }

        holder.dream_gift_redeem.setOnClickListener {
            onItemClickListener.onClickRedeemResponse(position, objCatalogue, _objCatalogue.DreamGiftId, holder.dream_gift_redeem)
        }

        holder.dream_gift_details.setOnClickListener {onItemClickListener.onClickDetialsResponse(position, objCatalogue, _objCatalogue.DreamGiftId)}

    }

    override fun getItemCount(): Int {
        return objCatalogue.size
    }
}