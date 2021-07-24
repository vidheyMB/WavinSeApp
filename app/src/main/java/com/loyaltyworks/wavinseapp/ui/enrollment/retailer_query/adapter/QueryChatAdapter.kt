package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.model.QueryChatElementResponse
import com.vmb.fileSelect.FileSelector
import kotlinx.android.synthetic.main.row_left_chat_cell.view.*

class QueryChatAdapter(
    var queryListingResponse: QueryChatElementResponse,
    var chatImageDisplay: ChatImageDisplay,
) : RecyclerView.Adapter<QueryChatAdapter.ViewHolder>() {

    var leftItemView: View? = null
    var rightItemView: View? = null

    val LEFT_CELL = 1
    val RIGHT_CELL = 2

    interface ChatImageDisplay {
        fun onClickChatImage(Url: String?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val row_query_sender = itemView.row_query_sender
        val  row_query_missed_call = itemView.row_query_missed_call
        val row_query_time = itemView.row_query_time
        val row_query_text = itemView.row_query_text
//        val row_query_text_pdf = itemView.pdf
        val chatImage = itemView.chatImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == LEFT_CELL) {
            //if (leftItemView == null)
            leftItemView = LayoutInflater.from(parent.context).inflate(
                R.layout.row_left_chat_cell,
                parent,
                false
            )
           ViewHolder(leftItemView!!)
            //itemView=leftItemView;
        } else {
            //if(rightItemView==null)
            rightItemView = LayoutInflater.from(parent.context).inflate(
                R.layout.row_right_chat_cell,
                parent,
                false
            )
           return ViewHolder(rightItemView!!)
            //itemView=rightItemView;
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (queryListingResponse.objQueryResponseJsonList!![position].UserType.equals("CUSTOMER",true)) RIGHT_CELL else LEFT_CELL
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lstQueryDetail = queryListingResponse.objQueryResponseJsonList!![position]
        holder.row_query_sender?.text = lstQueryDetail.RepliedBy.toString()
        holder.row_query_time?.text = AppController.dateToNewUIFormatss(lstQueryDetail.JCreatedDate.toString())
        if (!lstQueryDetail.QueryResponseInfo.isNullOrEmpty() || !lstQueryDetail.ImageUrl?.isNullOrEmpty()!!) {
            if (!lstQueryDetail.ImageUrl?.isNullOrEmpty()!!) {

                if(lstQueryDetail.ImageUrl!!.contains(".pdf") || lstQueryDetail.ImageUrl!!.contains(".txt")
                    || lstQueryDetail.ImageUrl!!.contains(".txt") ) {
                    val extension =
                        lstQueryDetail.ImageUrl.toString().substringAfterLast(".").toLowerCase()
                    if (!FileSelector.isImage(extension)) {

                        Log.d("fjksdjfksdl", lstQueryDetail.ImageUrl.toString())

                        holder.chatImage.visibility = View.GONE
//                        holder.row_query_text_pdf.visibility = View.VISIBLE

//                    holder.chatImage.setImageBitmap(FileSelector.getThumbnail(holder.itemView.context, extension))

//                        holder.row_query_text_pdf.text = lstQueryDetail.ImageUrl.toString().substringAfterLast("/")

                    }else{
                        holder.chatImage.visibility = View.VISIBLE
//                        holder.row_query_text_pdf.visibility = View.GONE

                    }
                }else{
                    holder.chatImage.visibility = View.VISIBLE
//                    holder.row_query_text_pdf.visibility = View.GONE

                }



                if (!lstQueryDetail.QueryResponseInfo.isNullOrEmpty()) {
                    holder.row_query_text.visibility = View.VISIBLE
                    holder.row_query_text.text = lstQueryDetail.QueryResponseInfo.toString()
                } else holder.row_query_text.visibility = View.GONE
                holder.row_query_missed_call.visibility = View.GONE
                Glide.with(holder.itemView)
                    .load(
                        BuildConfig.PROMO_IMAGE_BASE + lstQueryDetail.ImageUrl!!.replace(
                            "~",
                            ""
                        )
                    )
                    .placeholder(R.drawable.ic_default_img)
                    .error(R.drawable.ic_error)
                    .apply(RequestOptions().transform(RoundedCorners(50)))
                    .into(holder.chatImage)
            } else {
                holder.row_query_text.visibility = View.VISIBLE
                holder.chatImage.visibility = View.GONE
                holder.row_query_missed_call.visibility = View.GONE
                holder.row_query_text.text = lstQueryDetail.QueryResponseInfo.toString()
            }
        } else {
            holder.chatImage.visibility = View.GONE
            holder.row_query_text.visibility = View.GONE
            holder.row_query_missed_call.visibility = View.VISIBLE
        }

        holder.chatImage.setOnClickListener(View.OnClickListener {
            chatImageDisplay.onClickChatImage(
                BuildConfig.PROMO_IMAGE_BASE + lstQueryDetail.ImageUrl!!
                    .replace("~", "")
            )
        })
    }

    override fun getItemCount(): Int {
        return queryListingResponse.objQueryResponseJsonList?.size!!
    }

}