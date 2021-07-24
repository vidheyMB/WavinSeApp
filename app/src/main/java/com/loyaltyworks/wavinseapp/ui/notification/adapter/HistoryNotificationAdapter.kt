package com.loyaltyworks.wavinseapp.ui.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.model.HistoryNotificationResponse
import com.loyaltyworks.wavinseapp.model.LstPushHistory
import kotlinx.android.synthetic.main.row_history_notifications.view.*


class HistoryNotificationAdapter(
    var hisotryListingResponse: HistoryNotificationResponse?,
    var itemClicked: ItemClicked
) : RecyclerView.Adapter<HistoryNotificationAdapter.ViewHolder>() {

    interface ItemClicked {
        fun itemclicks(notificationHistory: LstPushHistory?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NReadOrNot = itemView.NReadOrNot
        val NImage = itemView.NImage
       val NtitleLL = itemView.NtitleLL
        val Ntitle = itemView.Ntitle
        val Ndate = itemView.Ndate
        val Ndesc = itemView.Ndesc
        val NdescExpandable = itemView.NdescExpandable
        val view = itemView.view
        val NReadMore = itemView.NReadMore
        val cardView = itemView.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_history_notifications,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationHistory = hisotryListingResponse!!.lstPushHistoryJson!![position]

        if (notificationHistory.ImagesURL != null && !notificationHistory.ImagesURL.isNullOrEmpty())
            Glide.with(holder.itemView)
                .load(BuildConfig.PROMO_IMAGE_BASE + notificationHistory.ImagesURL)
                .placeholder(R.drawable.ic_default_img) //                .error(R.drawable.ic_default_img)
                .into(holder.NImage)
        else holder.NImage.visibility = View.GONE

        if (notificationHistory.IsRead === 1) holder.NReadOrNot.setVisibility(View.INVISIBLE) else holder.NReadOrNot.setVisibility(
            View.VISIBLE
        )

        holder.Ntitle.text = notificationHistory.Title
        holder.Ndesc.text = notificationHistory.PushMessage
        holder.NdescExpandable.text = notificationHistory.PushMessage
        try {
            holder.Ndate.text = AppController.dateToNewUIFormats(notificationHistory.JCreatedDate.toString().split(" ")[0])

            if (notificationHistory.PushMessage!!.length > 96) {
                holder.NReadMore.text = holder.view.context.getString(R.string.read_more)
                holder.NReadMore.visibility = View.VISIBLE
                holder.NdescExpandable.visibility = View.GONE
                holder.Ndesc.visibility = View.VISIBLE
            } else {
                holder.NReadMore.visibility = View.INVISIBLE
                holder.NdescExpandable.visibility = View.GONE
                holder.Ndesc.visibility = View.VISIBLE
            }

        }catch (e: Exception){}

        holder.itemView.setOnClickListener(View.OnClickListener {
            itemClicked.itemclicks(notificationHistory)
            holder.NReadOrNot.visibility = View.INVISIBLE
            if (holder.NReadMore.text.toString().equals(holder.view.context.getString(R.string.read_more), ignoreCase = true)) {
                holder.NdescExpandable.visibility = View.VISIBLE
                holder.Ndesc.visibility = View.GONE
                holder.NReadMore.text = holder.view.context.getString(R.string.read_less)
            } else {
                holder.NdescExpandable.visibility = View.GONE
                holder.Ndesc.visibility = View.VISIBLE
                holder.NReadMore.text = holder.view.context.getString(R.string.read_more)
            }
        })


    }

    override fun getItemCount(): Int {
        return hisotryListingResponse!!.lstPushHistoryJson?.size!!
    }

    fun removeItem(position: Int) {

        hisotryListingResponse!!.lstPushHistoryJson?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getData(): List<LstPushHistory?>? {
        return hisotryListingResponse!!.lstPushHistoryJson
    }


}
