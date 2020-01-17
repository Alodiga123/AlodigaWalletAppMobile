package com.alodiga.app.wallet.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.activateDesativateCard.ActivateDeactivateCardStep1Activity;
import com.alodiga.app.wallet.activeCard.ActiveCardActivity;
import com.alodiga.app.wallet.balance.BalanceStep1Activity;
import com.alodiga.app.wallet.companionCards.CompanionCardsStep1Activity;
import com.alodiga.app.wallet.deactivateCard.DeactiveCardActivity;
import com.alodiga.app.wallet.exchange.ExchangeStep1Activity;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.manualRecharge.ManualRechargeStep1Activity;
import com.alodiga.app.wallet.manualRemoval.ManualRemovalStep1Activity;
import com.alodiga.app.wallet.model.ObjMoney;
import com.alodiga.app.wallet.model.ObjTransaction;
import com.alodiga.app.wallet.paymentComerce.PaymentComerceStep1Activity;
import com.alodiga.app.wallet.reloadCard.ReloadCardStep1Activity;
import com.alodiga.app.wallet.topup.TopupStep1Activity;
import com.alodiga.app.wallet.transference.TransferenceStep1Activity;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.WebService;
import com.alodiga.app.wallet.validate.ValidateAccountCode3Activity;
import com.alodiga.app.wallet.validate.ValidateAccountCode4Activity;
import com.alodiga.app.wallet.validate.ValidateAccountStep5Activity;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class AdapterMoneyProduct extends RecyclerView.Adapter<AdapterMoneyProduct.GroceryProductViewHolder> implements AdapterView.OnItemSelectedListener {
    Context context;
    private String responsetxt = "";
    private boolean serviceStatus;
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
        holder.txtProductPrice.setText(grocderyItemList.get(position).getProductPriceEncrip());
        holder.txtProductWeight.setText(grocderyItemList.get(position).getProductWeight());
        holder.txtProductQty.setText(grocderyItemList.get(position).getProductQty());


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
                    if (productName.equals("Tarjeta Prepagada")) {
                        //arrayAdapter.add("    Reload Card");
                        //arrayAdapter.add("    Remove Wallet");
                        arrayAdapter.add("    Activate/Deactivate Card");
                        arrayAdapter.add("    Check balance");
                        //arrayAdapter.add("    Companion Cards");

                    } else {
                        //R.string.menu_recharge
                        arrayAdapter.add("    Manual Recharge");
                        // R.string.menu_aoutMoney
                        arrayAdapter.add("    Manual Withdrawal");
                        // R.string.menu_convert
                        arrayAdapter.add("    Convert");
                        // R.string.menu_transfer
                        arrayAdapter.add("    To transfer");
                        //  R.string.menu_pay_comerce
                        arrayAdapter.add("    QR Payment Shops");

                        if (grocderyItemList.get(position).isTopup()) {
                            // R.string.menu_recharge_n_i
                            arrayAdapter.add("    TopUp");
                        }
                    }

                } else {
                    if (productName.equals("Tarjeta Prepagada")) {
                       // arrayAdapter.add("    Recargar Tarjeta");
                       // arrayAdapter.add("    Extraer a Billetera");
                        arrayAdapter.add("    Activar/Desactivar Tarjeta");
                        arrayAdapter.add("    Consultar Saldo");
                        //arrayAdapter.add("    Tarjetas Compañeras");
                    } else {
                        //R.string.menu_recharge
                        arrayAdapter.add("    Recarga Manual");
                        // R.string.menu_aoutMoney
                        arrayAdapter.add("    Retiro Manual");
                        // R.string.menu_convert
                        arrayAdapter.add("    Convertir");
                        // R.string.menu_transfer
                        arrayAdapter.add("    Transferir");
                        //  R.string.menu_pay_comerce
                        arrayAdapter.add("    Pago QR Comercios");

                        if (grocderyItemList.get(position).isTopup()) {
                            // R.string.menu_recharge_n_i
                            arrayAdapter.add("    TopUp");
                        }
                    }
                }


                //if(){}

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
                        if (arrayAdapter.getItem(_item).toString() == "    Recarga Manual" || arrayAdapter.getItem(_item).toString() == "    Manual Recharge") {
                            Intent show = new Intent(context, ManualRechargeStep1Activity.class);
                            context.startActivity(show);
                        }
                        if (arrayAdapter.getItem(_item).toString() == "    Retiro Manual" || arrayAdapter.getItem(_item).toString() == "    Manual Withdrawal") {
                            Intent show = new Intent(context, ManualRemovalStep1Activity.class);
                            context.startActivity(show);
                        }
                        if (arrayAdapter.getItem(_item).toString() == "    Convertir" || arrayAdapter.getItem(_item).toString() == "    Convert") {
                            Intent show = new Intent(context, ExchangeStep1Activity.class);
                            context.startActivity(show);
                        }
                        if (arrayAdapter.getItem(_item).toString() == "    Transferir" || arrayAdapter.getItem(_item).toString() == "    To transfer") {
                            Intent show = new Intent(context, TransferenceStep1Activity.class);
                            context.startActivity(show);
                        }

                        if (arrayAdapter.getItem(_item).toString() == "    Pago QR Comercios" || arrayAdapter.getItem(_item).toString() == "    QR Payment Shops") {
                            Intent show = new Intent(context, PaymentComerceStep1Activity.class);
                            context.startActivity(show);
                        }
                        if (arrayAdapter.getItem(_item).toString() == "    TopUp") {
                            Intent show = new Intent(context, TopupStep1Activity.class);
                            context.startActivity(show);
                        }

                        /*if (arrayAdapter.getItem(_item).toString() == "    Recargar Tarjeta" || arrayAdapter.getItem(_item).toString() == "    Reload Card") {
                            // Intent show = new Intent(context, PaymentComerceStep1Activity.class);
                            // context.startActivity(show)
                            if (idioma.equals("en")) {
                                Toast toast = Toast.makeText(context, "Functionality not available", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(context, "Funcionalidad no disponible", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            //Intent show = new Intent(context, ReloadCardStep1Activity.class);
                            //context.startActivity(show);

                        }*/
                        /*if (arrayAdapter.getItem(_item).toString() == "    Extraer a Billetera" || arrayAdapter.getItem(_item).toString() == "    Remove Wallet") {
                            // Intent show = new Intent(context, PaymentComerceStep1Activity.class);
                            // context.startActivity(show);
                            if (idioma.equals("en")) {
                                Toast toast = Toast.makeText(context, "Functionality not available", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(context, "Funcionalidad no disponible", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }*/
                        if (arrayAdapter.getItem(_item).toString() == "    Check balance" || arrayAdapter.getItem(_item).toString() == "    Consultar Saldo") {
                            // Intent show = new Intent(context, PaymentComerceStep1Activity.class);
                            // context.startActivity(show);
                            Session.setCardBalance(grocderyItemList.get(position).getProductPrice());
                            Intent show = new Intent(context, BalanceStep1Activity.class);
                            context.startActivity(show);
                        }

                        /*if (arrayAdapter.getItem(_item).toString() == "    Tarjetas Compañeras" || arrayAdapter.getItem(_item).toString() == "    Companion Cards") {


                            //Session.setCardBalanceMain(grocderyItemList.get(position).getProductPrice());
                            //Intent show = new Intent(context, CompanionCardsStep1Activity.class);
                            //context.startActivity(show);

                            if (idioma.equals("en")) {
                                Toast toast = Toast.makeText(context, "Functionality not available", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(context, "Funcionalidad no disponible", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }*/


                        if (arrayAdapter.getItem(_item).toString() == "    Activate/Deactivate Card" || arrayAdapter.getItem(_item).toString() == "    Activar/Desactivar Tarjeta") {
                           Session.setCardSelectActiveDeactive(grocderyItemList.get(position).getProductPrice());
                           Intent show = new Intent(context, ActivateDeactivateCardStep1Activity.class);
                            context.startActivity(show);


                           /* new Thread(new Runnable() {
                                public void run() {
                                    ProgressDialogAlodiga progressDialogAlodiga = new ProgressDialogAlodiga(context,"Cargando");
                                    progressDialogAlodiga.show();
                                    try {

                                        Calendar cal = Calendar.getInstance();
                                        TimeZone tz = cal.getTimeZone();
                                        String responseCode = null;
                                        WebService webService = new WebService();
                                        HashMap<String, String> map = new HashMap<String, String>();
                                        map.put("userId", Session.getUserId());
                                        map.put("card", grocderyItemList.get(position).getProductPrice());
                                        map.put("timeZone", tz.getID());

                                        SoapObject response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_ACTIVE_DEACTIVE_STATUS, Constants.ALODIGA);
                                        String stringResponse = response.toString();
                                        responseCode = response.getProperty("codigoRespuesta").toString();
                                        String datosRespuesta = response.getProperty("mensajeRespuesta").toString();

                                        if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
                                            Session.setIsactivateCard(true);
                                            Intent show = new Intent(context, ActivateDeactivateCardStep1Activity.class);
                                            context.startActivity(show);
                                        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THERE_IS_NO_SEARCH_RECORD)){
                                            if (idioma.equals("en")) {
                                                Toast toast = Toast.makeText(context, "Card not found", Toast.LENGTH_SHORT);
                                                toast.show();
                                                Intent show = new Intent(context, MainActivity.class);
                                                context.startActivity(show);
                                            } else {
                                                Toast toast = Toast.makeText(context, "Tarjeta no encontrada", Toast.LENGTH_SHORT);
                                                toast.show();
                                                Intent show = new Intent(context, MainActivity.class);
                                                context.startActivity(show);
                                            }
                                        }
                                        else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)){
                                            if (idioma.equals("en")) {
                                                Toast toast = Toast.makeText(context, "Internal error", Toast.LENGTH_SHORT);
                                                toast.show();
                                            } else {
                                                Toast toast = Toast.makeText(context, "Error interno", Toast.LENGTH_SHORT);
                                                toast.show();
                                            }
                                        }else {
                                            Intent show = new Intent(context, ActiveCardActivity.class);
                                            context.startActivity(show);

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    progressDialogAlodiga.dismiss();
                                }
                            }).start();

                            */


                        }
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
