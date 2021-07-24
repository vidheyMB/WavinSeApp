package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.loyaltyworks.wavinseapp.R;
import com.loyaltyworks.wavinseapp.model.ObjHelpTopicList;

import java.util.List;

public class HelpTopicAdapter extends ArrayAdapter<ObjHelpTopicList> {
    private Context context;
    private List<ObjHelpTopicList> helpTopic;


    public HelpTopicAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ObjHelpTopicList> location) {
        super(context, resource, location);
        this.context = context;
        this.helpTopic = location;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public ObjHelpTopicList getItem(int position) {
        return helpTopic.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.spinner_row, null);
        }

        TextView category = (TextView) convertView.findViewById(R.id.item_spinner);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)category.getLayoutParams();
        layoutParams.setMargins(8, 0, 0, 0);
        category.setLayoutParams(layoutParams);
        category.setText(this.getItem(position).getHelpTopicName());
        if(position==0){
            category.setTextColor(context.getResources().getColor(R.color.grey));
        }
        //AppController.hideProgressDialog(context);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.spinner_popup_row, null);
        }
        TextView category = (TextView) convertView.findViewById(R.id.item_spinner);
        category.setText(this.getItem(position).getHelpTopicName());
        //AppController.hideProgressDialog(context);

        //Log.d("======DATA=======",this.getItem(position).getName());
        return convertView;
    }
}
