package com.loyaltyworks.wavinseapp.ui.plumberretailerquery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.ObjQueryCenterAPIInfo
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import kotlinx.android.synthetic.main.row_plumber_retailer_query.view.*

class PlumberRetailerQueryAdapter(
    var objQueryCenterAPIInfo: List<ObjQueryCenterAPIInfo>?,
    var clickonItemClick: ClickOnItemClick
) : RecyclerView.Adapter<PlumberRetailerQueryAdapter.ViewHolder>() {

    interface ClickOnItemClick {
        fun onClickOnItemClick(objQueryCenterAPIInfo: ObjQueryCenterAPIInfo)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val memberName = itemView.memberName as TextView
        val memberShipId = itemView.memberShipId as TextView
        val topicTxt = itemView.topicTxt as TextView
        val branch = itemView.branch as TextView
        val QueryID = itemView.QueryID as TextView
        val status = itemView.status as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_plumber_retailer_query,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val objQueryCenterAPIInfo = objQueryCenterAPIInfo?.get(position)

        holder.memberName.text = objQueryCenterAPIInfo!!.MemberName
        holder.memberShipId.text = objQueryCenterAPIInfo!!.MembershipID
        holder.topicTxt.text = objQueryCenterAPIInfo!!.HelpTopic
        holder.branch.text = objQueryCenterAPIInfo!!.BranchName
        holder.QueryID.text = objQueryCenterAPIInfo!!.QueryID
        holder.status.text = objQueryCenterAPIInfo!!.QueryStatus

        holder.itemView.setOnClickListener {
            if (BlockMultipleClick.click()) return@setOnClickListener
            clickonItemClick.onClickOnItemClick(objQueryCenterAPIInfo)
        }

    }

    override fun getItemCount(): Int {
        return objQueryCenterAPIInfo!!.size
    }

}