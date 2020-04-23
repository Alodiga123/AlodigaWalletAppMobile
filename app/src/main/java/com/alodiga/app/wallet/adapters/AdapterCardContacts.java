package com.alodiga.app.wallet.adapters;

import android.app.Activity;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.balance.BalanceStep1Activity;
import com.alodiga.app.wallet.companionCards.CompanionCardsStep2Activity;
import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjCompanionCards;
import com.alodiga.app.wallet.model.ObjPaymentInfo;
import com.alodiga.app.wallet.model.ObjTarjetahabiente;
import com.alodiga.app.wallet.rechargeWithCard.RechargeWhithCarContactsDrop;
import com.alodiga.app.wallet.rechargeWithCard.RechargeWithCardStep1Activity;
import com.alodiga.app.wallet.rechargeWithCard.RechargeWithCardStep2Activity;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.WebService;
import com.alodiga.app.wallet.validate.ValidateAccountCode3Activity;
import com.alodiga.app.wallet.validate.ValidateAccountCode4Activity;
import com.alodiga.app.wallet.validate.ValidateAccountStep5Activity;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AdapterCardContacts extends RecyclerView.Adapter<AdapterCardContacts.GroceryProductViewHolder> implements AdapterView.OnItemSelectedListener {
    Context context;
    private List<ObjPaymentInfo> grocderyItemList;
    private String responsetxt = "";
    private boolean serviceStatus;
    static SoapObject response;
    private static String stringResponse = "";
    String datosRespuesta = "";
    final String idioma = Locale.getDefault().getLanguage();



    public AdapterCardContacts(List<ObjPaymentInfo> grocderyItemList, Context context) {
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
    public void onBindViewHolder(final GroceryProductViewHolder holder, final int position) {
        //holder.imageProductImage.setImageResource(grocderyItemList.get(position).getImageCard());

        holder.idProductType.setText(grocderyItemList.get(position).getCreditCardNumber());
        holder.txtProductName.setText(grocderyItemList.get(position).getCreditCardName());
        holder.txtProductPrice.setText(grocderyItemList.get(position).getCreditCardTypeId().getName());

        holder.linearSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjTarjetahabiente tarjetahabiente= new ObjTarjetahabiente();
                tarjetahabiente.setCardInfo(grocderyItemList.get(position));
                Session.setTarjetahabienteSelect(tarjetahabiente);
                //Session.setIsTarjetahabienteSelect(true);
                Intent show = new Intent(context, RechargeWithCardStep2Activity.class);
                context.startActivity(show);
            }


        });

        holder.linearDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {





                ObjPaymentInfo paymentInfo= grocderyItemList.get(position);
                ObjTarjetahabiente tarjetahabiente = new ObjTarjetahabiente();
                tarjetahabiente.setCardInfo(paymentInfo);
                Session.setTarjetahabienteSelect(tarjetahabiente);
                //Toast toast = Toast.makeText(context, "En Proceso", Toast.LENGTH_SHORT);
                //toast.show();

                androidx.appcompat.app.AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context, R.style.yourDialog);
                dialogo1.setTitle(R.string.payment_method_drop_question);
                dialogo1.setMessage(tarjetahabiente.getCardInfo().getCreditCardName());
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dropContact();
                    }
                });
                dialogo1.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        //cancelar();
                    }
                });
                dialogo1.show();

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

    void dropContact(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
                    map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                    map.put("userId", Session.getUserId());
                    map.put("paymentInfoId", Session.getTarjetahabienteSelect().getCardInfo().getId());
                    map.put("status", "false");

                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_CHANGE_STATUS_PAYMENT_INFO, Constants.ALODIGA);
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        Intent show = new Intent(context, RechargeWhithCarContactsDrop.class);
                        context.startActivity(show);
                    } else {

                        Toast toast = Toast.makeText(context, responsetxt, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

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

    public void serviceAnswer(String responseCode) {
        if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
            if (idioma.equals("en")) {
                responsetxt="welcome";
            } else {
                responsetxt="Bienvenido";
            }
            serviceStatus = true;

        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
            if (idioma.equals("en")) {
                responsetxt="Internal error";
            } else {
                responsetxt="Error interno";
            }
            serviceStatus = false;
        }
    }
}
