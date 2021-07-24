package com.loyaltyworks.wavinseapp.adapter;

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
import com.loyaltyworks.wavinseapp.model.CommonSpinner;

import java.util.ArrayList;
import java.util.Objects;

public class CommonAdapter extends ArrayAdapter<CommonSpinner> {
    private Context context;
    private ArrayList<CommonSpinner> genderList;


    public CommonAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<CommonSpinner> location) {
        super(context, resource, location);
        this.context = context;
        this.genderList = location;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public CommonSpinner getItem(int position) {
        return genderList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.spinner_row, null);
        }

        TextView category = (TextView) convertView.findViewById(R.id.item_spinner);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)category.getLayoutParams();
        layoutParams.setMargins(8, 0, 0, 0);
        category.setLayoutParams(layoutParams);
        category.setText(this.getItem(position).getName());



        //AppController.hideProgressDialog(context);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.spinner_popup_row, null);
        }
        TextView category = (TextView) convertView.findViewById(R.id.item_spinner);
        category.setText(Objects.requireNonNull(this.getItem(position)).getName());




        //AppController.hideProgressDialog(context);

        //Log.d("======DATA=======",this.getItem(position).getName());
        return convertView;
    }
}
