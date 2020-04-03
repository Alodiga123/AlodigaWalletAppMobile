package com.alodiga.app.wallet.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.balance.BalanceStep1Activity;
import com.alodiga.app.wallet.companionCards.CompanionCardsStep2Activity;
import com.alodiga.app.wallet.model.ObjCompanionCards;
import com.alodiga.app.wallet.model.ObjTarjetahabiente;
import com.alodiga.app.wallet.rechargeWithCard.RechargeWithCardStep1Activity;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.validate.ValidateAccountCode3Activity;
import com.alodiga.app.wallet.validate.ValidateAccountCode4Activity;
import com.alodiga.app.wallet.validate.ValidateAccountStep5Activity;

import java.util.List;
import java.util.Locale;

public class AdapterCardContacts extends RecyclerView.Adapter<AdapterCardContacts.GroceryProductViewHolder> implements AdapterView.OnItemSelectedListener {
    Context context;
    private List<ObjTarjetahabiente> grocderyItemList;

    public AdapterCardContacts(List<ObjTarjetahabiente> grocderyItemList, Context context) {
        this.grocderyItemList = grocderyItemList;
        this.context = context;
    }

    @Override
    public GroceryProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_card_contact, parent, false);
        GroceryProductViewHolder gvh = new GroceryProductViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryProductViewHolder holder, final int position) {
        //holder.imageProductImage.setImageResource(grocderyItemList.get(position).getImageCard());

        holder.idProductType.setText(grocderyItemList.get(position).getType_card());
        holder.txtProductName.setText(grocderyItemList.get(position).getCardholder_name());
        holder.txtProductPrice.setText(grocderyItemList.get(position).getCard_number());

        holder.linearSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Session.setTarjetahabienteSelect(grocderyItemList.get(position));
                Session.setIsTarjetahabienteSelect(true);
                Intent show = new Intent(context, RechargeWithCardStep1Activity.class);
                context.startActivity(show);
            }


        });

        holder.linearDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Session.setTarjetahabienteSelect(grocderyItemList.get(position));
                Toast toast = Toast.makeText(context, "En Proceso", Toast.LENGTH_SHORT);
                toast.show();
            }


        });


       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show;

                AlertDialog.Builder ADBuilder2 = new AlertDialog.Builder(context, R.style.yourDialog);


                final String idioma = Locale.getDefault().getLanguage();


                //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
                final ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.menu_simple);
                    ADBuilder2.setTitle(Html.fromHtml("Operations" ));


                //Creamos un boton para cancelar el dialog
                        ADBuilder2.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//Cerramos el dialogo
                    }

                });

                //Capturamos el evento 'OnClick' de los elementos en el dialogo
                ADBuilder2.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int _item) {


                    }
                });

                        ADBuilder2.show();//Mostramos el dialogo

            }


        });*/
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
        //ImageView imageProductImage;
        LinearLayout linearSelect;
        LinearLayout linearDrop;
        TextView idProductType;
        TextView txtProductName;
        TextView txtProductPrice;

        public GroceryProductViewHolder(View view) {
            super(view);
            linearSelect = view.findViewById(R.id.linearSelect);
            linearDrop = view.findViewById(R.id.linearDrop);

            idProductType= view.findViewById(R.id.idProductType);
            txtProductName = view.findViewById(R.id.idProductName);
            txtProductPrice = view.findViewById(R.id.idProductPrice);
        }
    }

}
