package com.example.c2paplicationmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.graphics.Color;

import java.util.ArrayList;


public class CustomListAdapter extends BaseAdapter {

    private boolean isNegative;

    private ArrayList<NewsItem> listData;

    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, ArrayList<NewsItem> listData) {
        this.listData = listData;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            TextView tHeadlineView = (TextView) convertView.findViewById(R.id.title);
            holder.headlineView = tHeadlineView ;
            holder.reportedDateView = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TextView tReporterNameView = (TextView) convertView.findViewById(R.id.reporter);
        if(listData.get(position).isNegative()){
            tReporterNameView.setTextColor(Color.WHITE);
        }else{
            tReporterNameView.setTextColor(Color.WHITE);
        }
        holder.reporterNameView = tReporterNameView;
        holder.headlineView.setText(listData.get(position).getHeadline());
        holder.reporterNameView.setText("Bs " + listData.get(position).getReporterName());
        holder.reportedDateView.setText(listData.get(position).getDate());
        return convertView;
    }

    static class ViewHolder {
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
    }

}