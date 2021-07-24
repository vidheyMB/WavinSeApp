package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.model.ObjCustomerAllQueryList
import com.loyaltyworks.wavinseapp.model.QueryListingResponse
import kotlinx.android.synthetic.main.query_listing_element.view.*

class SupportAdapter(var queryListingResponse : QueryListingResponse, var onItemClickListener: OnClickCallBack) : RecyclerView.Adapter<SupportAdapter.ViewHolder>() {

    interface OnClickCallBack{
        fun onQueryListItemClickResponse(itemView: View,position: Int, productList: List<ObjCustomerAllQueryList?>?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val row_query_ref_no = itemView.row_query_ref_no
        val row_query_date = itemView.row_query_date
        val row_query_time = itemView.row_query_time
        val row_query_text = itemView.row_query_text
        val row_query_status = itemView.row_query_status
//        val status_icon = itemView.status_icon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.query_listing_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lstQueryDetail = queryListingResponse.objCustomerAllQueryJsonList!![position]

        holder.row_query_ref_no.text = lstQueryDetail.CustomerTicketRefNo
        holder.row_query_date.text = AppController.dateToNewUIFormats(lstQueryDetail.JCreatedDate.toString().split(" ")[0])
        holder.row_query_time.text = lstQueryDetail.JCreatedDate.toString().split(" ")[1].removeSuffix(" AM")
        holder.row_query_text.text = lstQueryDetail.HelpTopic.toString()
        holder.row_query_status.text = lstQueryDetail.TicketStatus.toString()

        when (lstQueryDetail.TicketStatus) {
            "Closed" -> {
                holder.row_query_status.text = "Closed"
                holder.row_query_status.setBackgroundColor(holder.itemView.context.resources.getColor(
                    R.color.red))
//                holder.status_icon.setImageResource(R.drawable.ic_close_red)
            }
            "Resolved" -> {
                holder.row_query_status.text = "Resolved"
                holder.row_query_status.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.red))
//                holder.status_icon.setImageResource(R.drawable.ic_mask_group_12)
            }
            else -> {
                holder.row_query_status.text = "Pending"
                holder.row_query_status.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.primary_color))
//                holder.status_icon.setImageResource(R.drawable.ic_mask_group_9)
            }
        }
        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener!!.onQueryListItemClickResponse(v,position,queryListingResponse.objCustomerAllQueryJsonList)
        }

    }

    override fun getItemCount(): Int {
        return queryListingResponse.objCustomerAllQueryJsonList?.size!!
    }

}