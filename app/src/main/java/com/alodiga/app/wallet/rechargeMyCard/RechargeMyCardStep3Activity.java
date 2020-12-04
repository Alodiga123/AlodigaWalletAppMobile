package com.alodiga.app.wallet.rechargeMyCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.transference.TransferenceStep1Activity;
import com.alodiga.app.wallet.transference.TransferenceStep4codeActivity;
import com.alodiga.app.wallet.transference.TransferenceStep5Activity;
import com.alodiga.app.wallet.transference.TransferenceStep6Activity;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class RechargeMyCardStep3Activity extends AppCompatActivity {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static EditText amountValue, conceptValue;
    private static TextView txtConceptValue_2, txtAmountValue_2, txtAccountSourceValue_2;
    private static Button btnProcessTransaction, backToLoginBtn;
    private String responsetxt = "";
    private String balancePrepaidCard = "";
    private String balanceAlocoins = "";
    private String balanceAlodiga = "";
    private boolean serviceStatus;
    SoapObject response;
    private ProcessOperationRechargeWalletTask mAuthTask = null;



    public RechargeMyCardStep3Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_my_card_step_3);

        txtAccountSourceValue_2 = findViewById(R.id.txtAccountSourceValue_2);
        txtAmountValue_2 = findViewById(R.id.txtAmountValue_2);
        txtConceptValue_2 = findViewById(R.id.txtConceptValue_2);


        btnProcessTransaction = findViewById(R.id.btnProcessTransaction);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);

        txtAmountValue_2.setText(Session.getGetDestinationAmount() +" $");
        txtAccountSourceValue_2.setText(Session.getMoneySelected().getName());
        txtConceptValue_2.setText(getResources().getString(R.string.recharge_card_title));
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        btnProcessTransaction.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mAuthTask = new RechargeMyCardStep3Activity.ProcessOperationRechargeWalletTask(Session.getEmail(), Session.getMoneySelected().getId(), Session.getGetDestinationAmount());
                mAuthTask.execute((Void) null);
            }
        });

        backToLoginBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                        Intent i = new Intent(RechargeMyCardStep3Activity.this, RechargeMyCardStep2codeActivity.class);
                        startActivity(i);
                        finish();

            }
        });






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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RechargeMyCardStep3Activity.this, RechargeMyCardStep2codeActivity.class);
        startActivity(i);
        finish();
    }

    public class ProcessOperationRechargeWalletTask extends AsyncTask<Void, Void, Boolean> {
        private String  emailUser, productId, amountPayment;
        public ProcessOperationRechargeWalletTask(String productId_,String amountPayment_,String emailUser_) {
            this.amountPayment = amountPayment_; //nulo
            this.emailUser = emailUser_; //email
            this.productId = productId_; // idproductos
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
                map.put("email", emailUser);
                map.put("amountRecharge", amountPayment);
                map.put("productI", productId);

         //       response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_DISPERTION, Constants.ALODIGA);
         //       responseCode = response.getProperty("codigoRespuesta").toString();
         //       responseMessage = response.getProperty("mensajeRespuesta").toString();
                    responseCode = "00";


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
           // Session.setObjUserHasProducts(getListProduct(response));
           // Session.setOperationTransference(response.getProperty("idTransaction").toString());
            Intent i = new Intent(RechargeMyCardStep3Activity.this, RechargeMyCardStep4Activity.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(RechargeMyCardStep3Activity.this, RechargeMyCardStep4Activity.class);
            startActivity(i);
            finish();
         /*   new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    responsetxt);*/
        }
        progressDialogAlodiga.dismiss();
    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }
}



}
