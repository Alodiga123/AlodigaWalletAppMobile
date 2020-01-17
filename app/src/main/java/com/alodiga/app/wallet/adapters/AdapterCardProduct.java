package com.alodiga.app.wallet.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.activateDesativateCard.ActivateDeactivateCardStep1Activity;
import com.alodiga.app.wallet.balance.BalanceStep1Activity;
import com.alodiga.app.wallet.companionCards.CompanionCardsStep1Activity;
import com.alodiga.app.wallet.exchange.ExchangeStep1Activity;
import com.alodiga.app.wallet.manualRecharge.ManualRechargeStep1Activity;
import com.alodiga.app.wallet.manualRemoval.ManualRemovalStep1Activity;
import com.alodiga.app.wallet.model.ObjMoney;
import com.alodiga.app.wallet.paymentComerce.PaymentComerceStep1Activity;
import com.alodiga.app.wallet.reloadCard.ReloadCardStep1Activity;
import com.alodiga.app.wallet.topup.TopupStep1Activity;
import com.alodiga.app.wallet.transference.TransferenceStep1Activity;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.validate.ValidateAccountCode3Activity;
import com.alodiga.app.wallet.validate.ValidateAccountCode4Activity;
import com.alodiga.app.wallet.validate.ValidateAccountStep5Activity;

import java.util.List;
import java.util.Locale;

public class AdapterCardProduct extends RecyclerView.Adapter<AdapterCardProduct.GroceryProductViewHolder> implements AdapterView.OnItemSelectedListener {
    Context context;
    private String responsetxt = "";
    private boolean serviceStatus;
    private List<ObjMoney> grocderyItemList;

    public AdapterCardProduct(List<ObjMoney> grocderyItemList, Context context) {
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
        holder.txtProductPrice.setText(grocderyItemList.get(position).getProductPriceEncrip());
        //holder.itemView.setBackgroundColor(Color.parseColor("#00BEDF"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show;
                switch(Session.getCumplimient()) {
                    case "1":
                        show = new Intent(context, ValidateAccountStep5Activity.class);
                        context.startActivity(show);
                        break;
                    case "3":
                        show = new Intent(context, ValidateAccountCode3Activity.class);
                        context.startActivity(show);
                        break;
                    case "4":
                        show = new Intent(context, ValidateAccountCode4Activity.class);
                        context.startActivity(show);
                        break;
                    default:
                String productName = grocderyItemList.get(position).getProductName();

                AlertDialog.Builder ADBuilder = new AlertDialog.Builder(context, R.style.yourDialog);
                ADBuilder.setTitle(Html.fromHtml("Operaciones " + productName));
                Configuration config = new Configuration();

                final String idioma = Locale.getDefault().getLanguage();


                //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
                final ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.menu_simple);
                if (idioma.equals("en")) {

                        //R.string.menu_recharge
                    arrayAdapter.add("    Check balance");
                    //arrayAdapter.add("    Reload from Main");
                } else {

                        //R.string.menu_recharge
                    arrayAdapter.add("    Consultar Saldo");
                    //arrayAdapter.add("    Recargar desde Principal");

                }



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
                        if (arrayAdapter.getItem(_item).toString() == "    Check balance" || arrayAdapter.getItem(_item).toString() == "    Consultar Saldo") {
                            // Intent show = new Intent(context, PaymentComerceStep1Activity.class);
                            // context.startActivity(show);
                            Session.setCardBalance(grocderyItemList.get(position).getProductPrice());
                            Intent show = new Intent(context, BalanceStep1Activity.class);
                            context.startActivity(show);
                        }

                        /*if (arrayAdapter.getItem(_item).toString() == "    Reload from Main" || arrayAdapter.getItem(_item).toString() == "    Recargar desde Principal") {

                            if (idioma.equals("en")) {
                                Toast toast = Toast.makeText(context, "Functionality not available", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(context, "Funcionalidad no disponible", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }*/

                    }
                });

                ADBuilder.show();//Mostramos el dialogo

            }


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

        public GroceryProductViewHolder(View view) {
            super(view);
            imageProductImage = view.findViewById(R.id.idProductImage);
            txtProductName = view.findViewById(R.id.idProductName);
            txtProductPrice = view.findViewById(R.id.idProductPrice);
        }
    }

}
