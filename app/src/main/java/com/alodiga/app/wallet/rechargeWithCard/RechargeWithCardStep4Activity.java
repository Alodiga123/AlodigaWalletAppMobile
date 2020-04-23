package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoneyRemittence;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class RechargeWithCardStep4Activity extends AppCompatActivity {

    private Button btnProcessTransaction, backToLoginBtn;
    private TextView card,cardholder,cvv,card_type, date_recharge_expired, product,amount;
    private String responsetxt = "";
    private boolean serviceStatus;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private SoapObject response, response_;
     processRecharge mAuthTask;
     AddContactTask mAuthTask_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_with_card_resume);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        btnProcessTransaction= findViewById(R.id.ProcessTransaction);
        card= findViewById(R.id.card);
        cardholder= findViewById(R.id.cardholder);
        cvv= findViewById(R.id.cvv);
        card_type= findViewById(R.id.card_type);
        date_recharge_expired= findViewById(R.id.date_recharge);
        product= findViewById(R.id.product);
        amount= findViewById(R.id.amount);


        card.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumber());
        cardholder.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
        cvv.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardCVV());
        card_type.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardTypeId().getName());
        date_recharge_expired.setText(Session.getTarjetahabienteSelect().getCardInfo().getMonth()+" / "+Session.getTarjetahabienteSelect().getCardInfo().getYear());
        product.setText(Session.getTarjetahabienteSelect().getProduct().getName().split("-")[0]);
        amount.setText(Session.getTarjetahabienteSelect().getAmount());





        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), RechargeWithCardStep3CodeActivity.class);
                startActivity(show);
            }
        });


        btnProcessTransaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(Session.getIsConstantsEmpty() && Session.getTarjetahabienteSelect().getSave()){
                    addTask();
                }

                cargar();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RechargeWithCardStep4Activity.this, RechargeWithCardStep3CodeActivity.class);
        startActivity(i);
        finish();
    }


    public void cargar(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new processRecharge();
        mAuthTask.execute((Void) null);
    }


    public class processRecharge extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                String responseCode;

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUserId());
                map.put("amountRecharge", Session.getTarjetahabienteSelect().getAmount());
                map.put("currency", Constants.CURRENCY_INFO_PAYMENT);

                if (Session.getIsConstantsEmpty()){
                    map.put("cardNumber", Utils.aloDesencript(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumber()));
                    map.put("expirationYear", Session.getTarjetahabienteSelect().getCardInfo().getYear());
                    map.put("expirationMonth", Session.getTarjetahabienteSelect().getCardInfo().getMonth());
                    map.put("cvv", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardCVV());
                    map.put("cardHolderName", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
                }else{
                    map.put("paymentInfoId", Session.getTarjetahabienteSelect().getCardInfo().getId());
                }


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_SAVE_RECHARGE_AFINITAS, Constants.ALODIGA);
                responseCode = response.getProperty("codigoRespuesta").toString();


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
                }  else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_CARD)) {
                    responsetxt = getString(R.string.web_services_response_29);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DOES_NOT_HAVE_AN_ASSOCIATED_COMPANION_CARD)) {
                    responsetxt = getString(R.string.web_services_response_30_);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CARD_NUMBER_EXISTS)) {
                    responsetxt = getString(R.string.web_services_response_50);
                    serviceStatus = true;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NOT_ALLOWED_TO_CHANGE_STATE)) {
                    responsetxt = getString(R.string.web_services_response_51);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_AUTHENTICALLY_IMPOSSIBLE)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_UNABLE_TO_ACCESS_DATA)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THERE_ARE_NO_RECORDS_FOR_THE_REQUESTED_SEARCH)) {
                    responsetxt = getString(R.string.web_services_response_58);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_NUMBER_OF_ORDERS_ALLOWED_IS_EXCEEDED)) {
                    responsetxt = getString(R.string.web_services_response_60);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO)) {
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
            progressDialogAlodiga.dismiss();

            if (success) {

                Session.setRechargeWhitCardIdTransaccion(response.getProperty("idTransaction").toString());

                Intent show;
                show = new Intent(RechargeWithCardStep4Activity.this, RechargeWithCardStep5Activity.class);
                startActivity(show);



            } else {

                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
            }


        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public void addTask() {
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask_ = new AddContactTask();
        mAuthTask_.execute((Void) null);

    }


    public class AddContactTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                String responseCode;

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("userId", Session.getUserId());
                map.put("estado", "");
                map.put("ciudad", "");
                map.put("zipCode", "");
                map.put("addres1", "");
                map.put("paymentPatnerId", "2");
                map.put("paymentTypeId", "1");
                map.put("creditCardTypeId", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardTypeId().getId());
                map.put("creditCardName", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
                map.put("creditCardNumber", Utils.aloDesencript(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumber()));
                map.put("creditCardCVV", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardCVV());
                map.put("creditCardDate", Session.getTarjetahabienteSelect().getCardInfo().getYear()+"-"+Session.getTarjetahabienteSelect().getCardInfo().getMonth());


                response_ = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_SAVEPAYMENTINFO, Constants.ALODIGA);
                responseCode = response_.getProperty("codigoRespuesta").toString();


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
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                }

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
            mAuthTask_ = null;
            //showProgress(false);
            if (success) {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        getString(R.string.payment_method_save_succefull));;

            } else {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
            }
            progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask_ = null;
        }
    }


}
