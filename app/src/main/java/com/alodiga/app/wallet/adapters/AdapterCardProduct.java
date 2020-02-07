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
import com.alodiga.app.wallet.companionCards.CompanionCardsStep2Activity;
import com.alodiga.app.wallet.exchange.ExchangeStep1Activity;
import com.alodiga.app.wallet.manualRecharge.ManualRechargeStep1Activity;
import com.alodiga.app.wallet.manualRemoval.ManualRemovalStep1Activity;
import com.alodiga.app.wallet.model.ObjCompanionCards;
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
    private List<ObjCompanionCards> grocderyItemList;

    public AdapterCardProduct(List<ObjCompanionCards> grocderyItemList, Context context) {
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
        holder.imageProductImage.setImageResource(grocderyItemList.get(position).getImageCard());
        holder.txtProductName.setText(grocderyItemList.get(position).getNameCard());
        holder.txtProductPrice.setText(grocderyItemList.get(position).getNumberCardEncrip());

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
                //String productName = grocderyItemList.get(position).getProductName();

                AlertDialog.Builder ADBuilder2 = new AlertDialog.Builder(context, R.style.yourDialog);


                //ADBuilder2.setTitle(Html.fromHtml("Operaciones" ));
                Configuration config = new Configuration();

                final String idioma = Locale.getDefault().getLanguage();


                //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
                final ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.menu_simple);
                if (idioma.equals("en")) {
                    ADBuilder2.setTitle(Html.fromHtml("Operations" ));

                        //R.string.menu_recharge
                    arrayAdapter.add("    Check balance");
                    arrayAdapter.add("    Recharge from main");
                } else {
                    ADBuilder2.setTitle(Html.fromHtml("Operaciones" ));

                        //R.string.menu_recharge
                    arrayAdapter.add("    Consultar Saldo");
                    arrayAdapter.add("    Recargar desde Principal");

                }

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

                        //Creamos un toast para mostrar el elemento selecionado
                        if (arrayAdapter.getItem(_item).toString() == "    Check balance" || arrayAdapter.getItem(_item).toString() == "    Consultar Saldo") {
                            Session.setCardBalance(grocderyItemList.get(position).getNumberCard());
                            Intent show = new Intent(context, BalanceStep1Activity.class);
                            context.startActivity(show);
                        }

                        if (arrayAdapter.getItem(_item).toString() == "    Recharge from main" || arrayAdapter.getItem(_item).toString() == "    Recargar desde Principal") {
                               Session.setTranferenceCardToCardDest(grocderyItemList.get(position).getNumberCard());
                               Session.setTranferenceCardToCardEncripDest(grocderyItemList.get(position).getNumberCardEncrip());
                               Session.setDestinationNameValue(grocderyItemList.get(position).getNameCard());
                               Session.setUsuarioDestionId(grocderyItemList.get(position).getUserDestinationId());
                               Intent show = new Intent(context, CompanionCardsStep2Activity.class);
                            context.startActivity(show);
                        }

                    }
                });

                        ADBuilder2.show();//Mostramos el dialogo

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
