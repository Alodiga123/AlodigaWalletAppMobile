package com.alodiga.app.wallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.model.ObjMoney;

import java.util.List;

public class AdapterMoneyProduct extends RecyclerView.Adapter<AdapterMoneyProduct.GroceryProductViewHolder>{
    private List<ObjMoney> grocderyItemList;
    Context context;

    public AdapterMoneyProduct(List<ObjMoney> grocderyItemList, Context context) {
        this.grocderyItemList = grocderyItemList;
        this.context = context;
    }

    @Override
    public GroceryProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_card, parent, false);
        GroceryProductViewHolder gvh = new GroceryProductViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryProductViewHolder holder, final int position) {
        holder.imageProductImage.setImageResource(grocderyItemList.get(position).getProductImage());
        holder.txtProductName.setText(grocderyItemList.get(position).getProductName());
        holder.txtProductPrice.setText(grocderyItemList.get(position).getProductPrice());
        holder.txtProductWeight.setText(grocderyItemList.get(position).getProductWeight());
        holder.txtProductQty.setText(grocderyItemList.get(position).getProductQty());


        holder.imageProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = grocderyItemList.get(position).getProductName().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return grocderyItemList.size();
    }

    public class GroceryProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProductImage;
        TextView txtProductName;
        TextView txtProductPrice;
        TextView txtProductWeight;
        TextView txtProductQty;
        public GroceryProductViewHolder(View view) {
            super(view);
            imageProductImage=view.findViewById(R.id.idProductImage);
            txtProductName=view.findViewById(R.id.idProductName);
            txtProductPrice = view.findViewById(R.id.idProductPrice);
            txtProductWeight = view.findViewById(R.id.idProductWeight);
            txtProductQty = view.findViewById(R.id.idProductQty);
        }
    }

}