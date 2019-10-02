package com.example.c2paplicationmobile;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class SpinAdapterPais extends ArrayAdapter<ObjGenericObject> {

private Context context;
private ObjGenericObject[] values;

public SpinAdapterPais(@NonNull Context context, @LayoutRes int resource, @NonNull ObjGenericObject[] values) {
        super(context, resource, values);

        this.context = context;
        this.values = values;
        }

public int getCount(){
        return values.length;
        }

public ObjGenericObject getItem(int position){ return values[position]; }

public long getItemId(int position){
        return position;
        }

public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        label.setHint("Producto");
        label.setTextSize(16);

        label.setPadding(10,5,5,0);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position].getName());
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
        }

public View getDropDownView(int position,
        View convertView,
        ViewGroup parent) {

        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setPadding(10, 5, 5,5);
        label.setHint("Producto");
        //label.setTextSize(15);
        // label.setBackgroundColor(Color.parseColor("#F5F6CE"));

        label.setText(values[position].getName());
        return label;
        }

        }
