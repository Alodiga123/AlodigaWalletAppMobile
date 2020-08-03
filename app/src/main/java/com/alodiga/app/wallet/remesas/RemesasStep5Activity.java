package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RemesasStep5Activity extends AppCompatActivity {
    private static View view;
    private static TextView amountValue, conceptValue;

    private static TextView txtAccountSourceValue, acountNumberValue, destinationPhoneValue, destinationLastNameValue, destinationNameValue, txtAmountValue, txtConceptValue;

    private static Button btnProcessTransaction, backToLoginBtn;
    SoapObject response;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private String responsetxt = "";
    private boolean serviceStatus;
    private String balancePrepaidCard = "";
    private String balanceAlocoins = "";
    private String balanceAlodiga = "";
    private ProcessOperationTransferenceTask mAuthTask = null;

    public RemesasStep5Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remesas_step5_layout);

        amountValue = findViewById(R.id.txtAmountValue_2);
        conceptValue = findViewById(R.id.txtConceptValue_2);
        acountNumberValue = findViewById(R.id.txtAccountNumberValue_2);
        destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue_2);
        destinationLastNameValue = findViewById(R.id.txtDestinationLastNameValue_2);
        destinationNameValue = findViewById(R.id.txtDestinationNameValue_2);
        btnProcessTransaction = findViewById(R.id.btnProcessTransaction);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);
        txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue_2);
        acountNumberValue.setText(Session.getDestinationAccountNumber());
        destinationPhoneValue.setText(Session.getDestinationPhoneValue());
        destinationLastNameValue.setText(Session.getDestinationLastNameValue());
        destinationNameValue.setText(Session.getDestinationNameValue());
        conceptValue.setText(Session.getDestinationConcept());
        amountValue.setText(Session.getGetDestinationAmount() +" $");
        txtAccountSourceValue.setText(Session.getMoneySelected().getName());

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        btnProcessTransaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                procesar();
            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RemesasStep5Activity.this, RemesasStep4codeActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RemesasStep5Activity.this, RemesasStep4codeActivity.class);
        startActivity(i);
        finish();
    }
    public void procesar() {


        mAuthTask = new ProcessOperationTransferenceTask("1", Session.getEmail(), Session.getMoneySelected().getId(), Session.getGetDestinationAmount(), Session.getDestinationConcept(), "1", Session.getUsuarioDestionId());
        mAuthTask.execute((Void) null);


    }

    protected ArrayList<ObjUserHasProduct> getListProduct(SoapObject response) {
        //ObjUserHasProduct[] obj2_aux= new ObjUserHasProduct[response.getPropertyCount()-3];
        //ObjUserHasProduct[] obj2 = new ObjUserHasProduct[response.getPropertyCount()-3];
        ArrayList<ObjUserHasProduct> obj2 = new ArrayList<>();
        for (int i = 4; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjUserHasProduct object = new ObjUserHasProduct(obj.getProperty("id").toString(), obj.getProperty("name").toString(), obj.getProperty("currentBalance").toString(), obj.getProperty("symbol").toString(), obj.getProperty("isPayTopUp").toString());
            if (object.getName().equals("Tarjeta Prepagada") || object.getName().equals("Prepaid Card") ){
                Session.setAffiliatedCard(Boolean.parseBoolean(Session.getPrepayCardAsociate()));
                object.setNumberCard(Session.getNumberCard());
            }else{
                object.setNumberCard(Session.getAccountNumber());
            }

            obj2.add(object);
            //obj2[i-3] = object;
        }

        return obj2;
    }

    public class ProcessOperationTransferenceTask extends AsyncTask<Void, Void, Boolean> {


        private String cryptogramShop, emailUser, productId, amountPayment,
                conceptTransaction, cryptogramUser, idUserDestination;

        public ProcessOperationTransferenceTask(String cryptogramShop_, String emailUser_, String productId_, String amountPayment_, String conceptTransaction_, String cryptogramUser_, String idUserDestination_) {
            this.cryptogramShop = cryptogramShop_; //nulo
            this.emailUser = emailUser_; //email
            this.productId = productId_; // idproductos
            this.amountPayment = amountPayment_; //monto
            this.conceptTransaction = conceptTransaction_; //paymentshop
            this.cryptogramUser = cryptogramUser_; // nulo
            this.idUserDestination = idUserDestination_; //destino
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();

            try {

                boolean availableBalance = true;
                String responseCode;
                String responseMessage = "";


                if (availableBalance) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("cryptogramUserSource", cryptogramShop);
                    map.put("emailUser", emailUser);
                    map.put("productId", productId);
                    map.put("amountTransfer", amountPayment);
                    map.put("conceptTransaction", conceptTransaction);
                    map.put("cryptogramUserDestination", cryptogramUser);
                    map.put("idUserDestination", idUserDestination);

                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_TRANSFERENCE, Constants.ALODIGA);
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    responseMessage = response.getProperty("mensajeRespuesta").toString();

                    if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

                        serviceStatus = true;
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
                        responsetxt = getString(R.string.web_services_response_99);
                        serviceStatus = false;
                    }
                } else {
                    responsetxt = getString(R.string.insuficient_balance);
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
        protected void onPreExecute() {
            progressDialogAlodiga.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {

                Session.setAlocoinsBalance(balanceAlocoins);
                Session.setHealthCareCoinsBalance(balancePrepaidCard);
                Session.setAlodigaBalance(balanceAlodiga);
                Session.setObjUserHasProducts(getListProduct(response));
                Session.setOperationTransference(response.getProperty("idTransaction").toString());
                Intent i = new Intent(RemesasStep5Activity.this, RemesasStep6Activity.class);
                startActivity(i);
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

}
