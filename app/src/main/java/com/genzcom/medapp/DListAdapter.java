package com.genzcom.medapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by GENZCOM on 1/11/2015.
 */
public class DListAdapter extends ArrayAdapter<Doctor>{

    Context context;
    Doctor doctor;
    public DListAdapter(Context context, int textViewResourceId,
                         List<Doctor> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
    }

    private class ViewHolder {
        TextView name;

    }

    public View getView(int position,View convertView,ViewGroup parent)
    {
        doctor = getItem(position);
        ViewHolder holder=null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.doc_item, null);
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.txt_doc_item);
            holder.name.setTextSize(12);
            holder.name.setTextColor(Color.BLUE);
        }
        holder.name.setText(doctor.getName() +" " +doctor.getPhone() );
        return convertView;
    }
}
