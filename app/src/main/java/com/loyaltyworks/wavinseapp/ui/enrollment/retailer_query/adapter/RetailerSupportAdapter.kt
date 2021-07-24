package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LsrProductDetail

class RetailerSupportAdapter(
    var lstProductDetails: List<LsrProductDetail>?, var clickOnItemList: ClickOnItemList
) : RecyclerView.Adapter<RetailerSupportAdapter.ViewHolder>() {

    interface ClickOnItemList {
        fun onClickItemList(/*lstProductDetails: LsrProductDetail*/)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        val categoryname = itemView.category_name as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.query_listing_element,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val _listProductDetails = lstProductDetails?.get(position)

        holder.itemView.setOnClickListener {
            clickOnItemList.onClickItemList()
        }


    }

    override fun getItemCount(): Int {
        return 10
    }

}