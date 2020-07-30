package com.alodiga.app.wallet.companionCards;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.manualRemoval.ManualRemovalStep2WelcomeActivity;
import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CompanionCardsStep5Activity extends AppCompatActivity {
    private static TextView amountValue, conceptValue;
    private static TextView txtRemitenteNameValue_rem, txtTransactionId_3, txtAccountSourceValue, txtDateTimeValue_3, acountNumberValue, destinationPhoneValue, destinationLastNameValue, destinationNameValue, txtAmountValue, txtConceptValue;
    private static Button btnProcessFinisTransference;
    private static Button btnShareInformation;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private String responsetxt = "";
    private boolean serviceStatus;
    private UserProductTask mAuthTask = null;


    public CompanionCardsStep5Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companioncardstep5_layout);


        txtRemitenteNameValue_rem= findViewById(R.id.txtRemitenteNameValue_rem);
        txtRemitenteNameValue_rem.setText(Session.getUsername());
        //nombre
        destinationNameValue = findViewById(R.id.txtDestinationNameValue_3);
        //apellido
        //telefono
        //Destino
        acountNumberValue = findViewById(R.id.txtAccountNumberValue_3);
        //Monto
        amountValue = findViewById(R.id.txtAmountValue_3);
        //concepto
        conceptValue = findViewById(R.id.txtConceptValue_3);
        //Origen
        txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue_3);
        //fecha
        txtDateTimeValue_3 = findViewById(R.id.txtDateTimeValue_3);
        //transaccion
        txtTransactionId_3 = findViewById(R.id.txtTransactionId_3);

        btnProcessFinisTransference = findViewById(R.id.btnProcessFinisTransference);
        btnShareInformation = findViewById(R.id.btnShareInformationTransference);

        destinationNameValue.setText(Session.getDestinationNameValue());
        acountNumberValue.setText(Session.getTranferenceCardToCardEncripDest());
        amountValue.setText(Session.getGetDestinationAmount());
        conceptValue.setText(Session.getDestinationConcept());
        txtAccountSourceValue.setText(Session.getTranferenceCardToCardEncrip());
        txtDateTimeValue_3.setText(new Timestamp(new Date().getTime()).toGMTString());
        txtTransactionId_3.setText(Session.getOperationTransference());



        btnShareInformation.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.confirmation_title_successfull_Alodiga)+
                        "\n" + getString(R.string.destination_name_)+" "+ Session.getDestinationNameValue() + " " + Session.getDestinationLastNameValue() +
                        "\n" + getString(R.string.destinationCard_dest)+" " + Session.getTranferenceCardToCardEncripDest() +
                        "\n" + getString(R.string.destination_name_1)+" " + Session.getUsername() +
                        "\n" + getString(R.string.destinationCard_source)+" " + Session.getTranferenceCardToCardEncrip() +
                        "\n" + getString(R.string.destination_amount)+" " + Session.getGetDestinationAmount()  +
                        "\n" + getString(R.string.destination_concept)+" " + Session.getDestinationConcept() +
                        "\n" + getString(R.string.destination_date_time)+" " + new Timestamp(new Date().getTime()).toGMTString() +
                        "\n" + getString(R.string.number_trans)+" " + Session.getOperationTransference());
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
            }
        });

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        btnProcessFinisTransference.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent i = new Intent(CompanionCardsStep5Activity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();
            }
        });


    }


    public void updateProduct() {

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new UserProductTask();
        mAuthTask.execute((Void) null);

    }

    private ArrayList<ObjUserHasProduct> getElementsProduct(String elementGet, String response) {
        ArrayList<ObjUserHasProduct> objUserHasProducts = new ArrayList<ObjUserHasProduct>();
        String elementgetId = "id=";
        String elementGetName = "nombreProducto=";
        String elementGetCurrentBalance = "saldoActual=";
        String elementGetSymbol = "simbolo=";
        String litaProd = "respuestaListadoProductos=";

        for (int i = 1; i < getLenghtFromResponseJson(litaProd, response); i++) {
            ObjUserHasProduct objUserHasProduct = new ObjUserHasProduct(response.split(elementgetId)[i].split(";")[0], response.split(elementGetName)[i].split(";")[0], response.split(elementGetCurrentBalance)[i].split(";")[0], response.split(elementGetSymbol)[i].split(";")[0]);
            objUserHasProducts.add(objUserHasProduct);
        }

        return objUserHasProducts;
    }

    private String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }

    private Integer getLenghtFromResponseJson(String v, String response) {
        return (response.split(v).length);
    }

    public class UserProductTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;


            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("usuarioId", Session.getUserId());


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_UPDATE_PRODUCT, Constants.REGISTRO_UNIFICADO);
                responseCode = response.getProperty("codigoRespuesta").toString();
                responseMessage = response.getProperty("mensajeRespuesta").toString();


                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

                    responsetxt = getString(R.string.web_services_response_00);
                    serviceStatus = true;
                    return serviceStatus;

                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS)) {
                    responsetxt = getString(R.string.web_services_response_01);
                    serviceStatus = false;

                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA)) {
                    responsetxt = getString(R.string.web_services_response_03);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA)) {
                    responsetxt = getString(R.string.web_services_response_04);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS)) {
                    responsetxt = getString(R.string.web_services_response_05);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO)) {
                    responsetxt = getString(R.string.web_services_response_06);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE)) {
                    responsetxt = getString(R.string.web_services_response_08);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO)) {
                    responsetxt = getString(R.string.web_services_response_12);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_AMOUNT_LIMIT)) {
                    responsetxt = getString(R.string.web_services_response_30);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_ACCOUNT)) {
                    responsetxt = getString(R.string.web_services_response_31);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_CUSTOMER)) {
                    responsetxt = getString(R.string.web_services_response_32);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_HAS_NOT_BALANCE)) {
                    responsetxt = getString(R.string.web_services_response_33);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO)) {
                    responsetxt = getString(R.string.web_services_response_95);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE)) {
                    responsetxt = getString(R.string.web_services_response_96);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE)) {
                    responsetxt = getString(R.string.web_services_response_97);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES)) {
                    responsetxt = getString(R.string.web_services_response_98);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                } else {
                    responsetxt = responseMessage;
                    serviceStatus = false;
                }
                //progressDialogAlodiga.dismiss();
            } catch (IllegalArgumentException e) {
                responsetxt = getString(R.string.web_services_response_99);
                serviceStatus = false;
                e.printStackTrace();
                System.err.println(e);
                return false;
            } catch (Exception e) {
                responsetxt = getString(R.string.web_services_response_99);
                serviceStatus = false;
                e.printStackTrace();
                System.err.println(e);
                return false;
            }
            return serviceStatus;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);
            if (success) {
                Intent show;
                show = new Intent(getApplicationContext(), ManualRemovalStep2WelcomeActivity.class);
                startActivity(show);
                finish();


            } else {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
            }
            progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    @Override
    public void onBackPressed() {
        Intent show = new Intent(CompanionCardsStep5Activity.this, MainActivity.class);
        finish();
        startActivity(show);

    }
}
