package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.CityList

class CityAdapter(context: Context, @LayoutRes resource: Int, locationStateCities: List<CityList>) : ArrayAdapter<CityList?>(context, resource, locationStateCities as List<CityList?>) {
    private val locationStateCities: List<CityList> = locationStateCities
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): CityList? {
        return locationStateCities[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inf = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inf.inflate(R.layout.spinner_row, null)
        }
        val category = convertView!!.findViewById<View>(R.id.item_spinner) as TextView
        val layoutParams = category.layoutParams as LinearLayout.LayoutParams
        layoutParams.setMargins(8, 0, 0, 0)
        category.layoutParams = layoutParams
        category.text = getItem(position)?.CityName

        if(position == 0) {
            category.setTextColor(context.resources.getColor(R.color.grey))
        }else{
            category.setTextColor(context.resources.getColor(R.color.black))
        }


//        if (position == 0) {
//            category.setTextColor(context.resources.getColor(R.color.referText))
//        }
        //AppController.hideProgressDialog(context);
        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inf = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inf.inflate(R.layout.spinner_popup_row, null)
        }
        val category = convertView!!.findViewById<View>(R.id.item_spinner) as TextView
        category.text = getItem(position)?.CityName
        //AppController.hideProgressDialog(context);

        //Log.d("======DATA=======",this.getItem(position).getName());
        return convertView
    }

}