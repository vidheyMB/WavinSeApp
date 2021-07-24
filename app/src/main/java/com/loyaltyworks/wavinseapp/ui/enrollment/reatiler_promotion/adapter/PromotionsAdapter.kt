package com.loyaltyworks.wavinseapp.ui.enrollment.reatiler_promotion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.GetPromotionResponse
import com.loyaltyworks.wavinseapp.model.LstPromotionList
import kotlinx.android.synthetic.main.row_offer_and_promotions.view.*

class PromotionsAdapter(
    var whatsNewResponse: GetPromotionResponse,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PromotionsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(offersPromotions: LstPromotionList)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val offer_tv = itemView.promo_title
        val offers_img = itemView.promo_image
        val next_ll = itemView.promo_details

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_offer_and_promotions,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val promotionListing = whatsNewResponse.LstPromotionJsonList!![position]
        Glide.with(holder.itemView).asBitmap().error(R.drawable.ic_default_img)
            .placeholder(R.drawable.ic_default_img).load(
                BuildConfig.PROMO_IMAGE_BASE + promotionListing.ProImage!!.replace(
                    "..",
                    ""
                )
            ).into(holder.offers_img)

        holder.offer_tv.text = promotionListing.PromotionName

        holder.next_ll.setOnClickListener {
            itemClickListener.onItemClicked(whatsNewResponse.LstPromotionJsonList!![position])
        }

    }

    override fun getItemCount(): Int {
        return whatsNewResponse.LstPromotionJsonList?.size!!
    }

}