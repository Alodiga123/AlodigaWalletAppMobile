package com.alodiga.app.wallet.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.manualRemoval.ManualRemovalStep1Activity;
import com.alodiga.app.wallet.model.ObjMoney;
import com.alodiga.app.wallet.model.ObjTransaction;
import com.alodiga.app.wallet.paymentComerce.PaymentComerceStep1Activity;
import com.alodiga.app.wallet.transference.TransferenceStep1Activity;
import com.alodiga.app.wallet.utils.Constants;

import java.util.List;

public class AdapterMoneyProduct extends RecyclerView.Adapter<AdapterMoneyProduct.GroceryProductViewHolder> implements AdapterView.OnItemSelectedListener {
    Context context;
    private List<ObjMoney> grocderyItemList;

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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = grocderyItemList.get(position).getProductName();


                AlertDialog.Builder ADBuilder = new AlertDialog.Builder(context,R.style.yourDialog);
                ADBuilder.setTitle(Html.fromHtml("Operaciones "+productName));


                //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
                final ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.menu_simple);

                arrayAdapter.add("    Retiro Manual");
                arrayAdapter.add("    Transferir");
                arrayAdapter.add("    Pago QR Comercios");

                //Creamos un boton para cancelar el dialog
                ADBuilder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//Cerramos el dialogo
                    }

                });

                //Capturamos el evento 'OnClick' de los elementos en el dialogo
                ADBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int _item) {

                        //Creamos un toast para mostrar el elemento selecionado

                        if(arrayAdapter.getItem(_item).toString()=="    Transferir"){
                            Intent show = new Intent(context, TransferenceStep1Activity.class);
                            context.startActivity(show);
                        }
                        if(arrayAdapter.getItem(_item).toString()=="    Retiro Manual"){
                            Intent show = new Intent(context, ManualRemovalStep1Activity.class);
                            context.startActivity(show);
                        }
                        if(arrayAdapter.getItem(_item).toString()=="    Pago QR Comercios"){
                            Intent show = new Intent(context, PaymentComerceStep1Activity.class);
                            context.startActivity(show);
                        }
                    }
                });

                ADBuilder.show();//Mostramos el dialogo

            }
        });
    }

    @Override
    public int getItemCount() {
        return grocderyItemList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class GroceryProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProductImage;
        TextView txtProductName;
        TextView txtProductPrice;
        TextView txtProductWeight;
        TextView txtProductQty;

        public GroceryProductViewHolder(View view) {
            super(view);
            imageProductImage = view.findViewById(R.id.idProductImage);
            txtProductName = view.findViewById(R.id.idProductName);
            txtProductPrice = view.findViewById(R.id.idProductPrice);
            txtProductWeight = view.findViewById(R.id.idProductWeight);
            txtProductQty = view.findViewById(R.id.idProductQty);
        }
    }

}
