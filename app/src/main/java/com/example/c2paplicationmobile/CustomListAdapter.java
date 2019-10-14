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
            tHeadlineView.setTextSize(2);
            holder.headlineView = tHeadlineView ;
            holder.reportedDateView = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        TextView tReporterNameView = (TextView) convertView.findViewById(R.id.reporter);
        tReporterNameView.setTextSize(10);



        holder.reporterNameView = tReporterNameView;

        holder.headlineView.setText(listData.get(position).getHeadline());

        holder.headlineView.setTextSize(18);
        holder.reporterNameView.setText("Bs " + listData.get(position).getReporterName());



        TextView tdateView = (TextView) convertView.findViewById(R.id.date);
        if(listData.get(position).isNegative()){

            tdateView.setTextColor(Color.WHITE);
        }else{
            tdateView.setTextColor(Color.RED);



        }
        tdateView.setText(listData.get(position).getDate());
        holder.reportedDateView = tdateView;



        return convertView;
    }

    static class ViewHolder {
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
    }

}