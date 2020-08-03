package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
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


public class RechargeWithCardStep4Activity extends AppCompatActivity {

    private Button btnProcessTransaction, backToLoginBtn;
    private TextView card,cardholder,cvv,card_type, date_recharge_expired, product,amount;
    private String responsetxt = "";
    private boolean serviceStatus;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private SoapObject response, response_;
     processRecharge mAuthTask;
     AddContactTask mAuthTask_;
     Boolean isSaveTrue=true;

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

        card.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumberEnmas());
        cardholder.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
        cvv.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardCVV());
        card_type.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardTypeId().getName());
        date_recharge_expired.setText(Session.getTarjetahabienteSelect().getCardInfo().getMonth()+" / "+Session.getTarjetahabienteSelect().getCardInfo().getYear());
        product.setText(Session.getTarjetahabienteSelect().getProduct().getName().split("-")[0]);
        amount.setText(Session.getTarjetahabienteSelect().getAmount());
        
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
            }
        });


        btnProcessTransaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                process();
            }
        });
    }


    public void process(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();

        if(Session.getIsConstantsEmpty() && Session.getTarjetahabienteSelect().getSave() && isSaveTrue ){
            addTask();
        }

        cargar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RechargeWithCardStep4Activity.this, RechargeWithCardStep3CodeActivity.class);
        startActivity(i);
        finish();
    }


    public void cargar(){
        //progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        //progressDialogAlodiga.show();
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

                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_CREDENTIALS_WS_INAVAILABLE)) {
                    responsetxt = getString(R.string.web_services_response_143);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_CALL_ISSUER)) {
                    responsetxt = getString(R.string.web_services_response_150);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_NOT_AUTHORIZED)) {
                    responsetxt = getString(R.string.web_services_response_151);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_INVALID_TRADE)) {
                    responsetxt = getString(R.string.web_services_response_152);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_RETAIN_CARD)) {
                    responsetxt = getString(R.string.web_services_response_153);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_INVALID_TRANSACTION_AFINITAS)) {
                    responsetxt = getString(R.string.web_services_response_154);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_RETRY_AFINITAS)) {
                    responsetxt = getString(R.string.web_services_response_155);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TRANSACTION_NOT_PERMITTED)) {
                    responsetxt = getString(R.string.web_services_response_156);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_INVALID_CARD)) {
                    responsetxt = getString(R.string.web_services_response_157);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_FORMAT_ERROR)) {
                    responsetxt = getString(R.string.web_services_response_158);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_INSUFFICIENT_FUNDS)) {
                    responsetxt = getString(R.string.web_services_response_159);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_EXPIRED_CARD_AFINITAS)) {
                    responsetxt = getString(R.string.web_services_response_160);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_INVALID_PIN)) {
                    responsetxt = getString(R.string.web_services_response_161);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_DEFERRED_PAYMENT_NOT_PERMITTED)) {
                    responsetxt = getString(R.string.web_services_response_162);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_LIMIT_EXCEEDED)) {
                    responsetxt = getString(R.string.web_services_response_163);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TYPE_OF_PLAN_TERM_INVALID)) {
                    responsetxt = getString(R.string.web_services_response_164);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_DUPLICATED_TRANSACTION)) {
                    responsetxt = getString(R.string.web_services_response_165);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_EXCESSED_AUTHORIZATIONS)) {
                    responsetxt = getString(R.string.web_services_response_166);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_CP_NOT_PERMITTED_BY_TH)) {
                    responsetxt = getString(R.string.web_services_response_167);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TERMINAL_ERROR)) {
                    responsetxt = getString(R.string.web_services_response_168);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_UNACTIVATED_CARD)) {
                    responsetxt = getString(R.string.web_services_response_169);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_INVALID_COIN)) {
                    responsetxt = getString(R.string.web_services_response_170);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_CHIP_READING_ERROR)) {
                    responsetxt = getString(R.string.web_services_response_171);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_INVALID_CHIP)) {
                    responsetxt = getString(R.string.web_services_response_172);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_CHIP_NOT_SUPPORTED)) {
                    responsetxt = getString(R.string.web_services_response_173);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_UNKNOWN)) {
                    responsetxt = getString(R.string.web_services_response_174);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_THE_DEVICE_IS_NOT_ACTIVE)) {
                    responsetxt = getString(R.string.web_services_response_175);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_THE_BRANCH_IS_NOT_ACTIVE)) {
                    responsetxt = getString(R.string.web_services_response_176);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TRADE_IS_NOT_ACTIVE)) {
                    responsetxt = getString(R.string.web_services_response_177);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_THE_REQUEST_IS_EMPTY)) {
                    responsetxt = getString(R.string.web_services_response_178);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_MISSING_PARAMETER_ON_REQUEST)) {
                    responsetxt = getString(R.string.web_services_response_179);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_RESOURCE_NOT_FOUND)) {
                    responsetxt = getString(R.string.web_services_response_180);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_ANSWER_EMPTY)) {
                    responsetxt = getString(R.string.web_services_response_181);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_THE_TRANSACTION_EXCEEDS_THE_PERMITTED_AMOUNT)) {
                    responsetxt = getString(R.string.web_services_response_182);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TRANSACTION_EXCEEDS_THE_ALLOWED_DAILY_AMOUNT)) {
                    responsetxt = getString(R.string.web_services_response_183);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TRANSACTION_EXCEEDS_THE_MONTHLY_AMOUNT_ALLOWED)) {
                    responsetxt = getString(R.string.web_services_response_184);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_NON_ACTIVE_PROMOTIONS)) {
                    responsetxt = getString(R.string.web_services_response_185);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_PROMOTION_NOT_ACTIVE)) {
                    responsetxt = getString(R.string.web_services_response_186);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_THE_TRANSACTION_IS_NOT_WITHIN_THE_PERMITTED_SCHEDULE)) {
                    responsetxt = getString(R.string.web_services_response_187);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_THE_TRANSACTION_DOES_NOT_EXIST)) {
                    responsetxt = getString(R.string.web_services_response_188);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TRANSACTION_WITH_NOT_APPROVED_SOURCE)) {
                    responsetxt = getString(R.string.web_services_response_189);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_INVALID_MEMBERSHIP)) {
                    responsetxt = getString(R.string.web_services_response_190);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TRANSACTION_CANCELED_PREVIOUSLY)) {
                    responsetxt = getString(R.string.web_services_response_191);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_PREVIOUSLY_REVERSED_TRANSACTION)) {
                    responsetxt = getString(R.string.web_services_response_192);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_EXCEED_PERMITTED_DAILY_TRANSACTIONS)) {
                    responsetxt = getString(R.string.web_services_response_193);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_THE_CORPORATE_IS_NOT_ACTIVE)) {
                    responsetxt = getString(R.string.web_services_response_194);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_ANSWER_NOT_FOUND)) {
                    responsetxt = getString(R.string.web_services_response_195);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_TIME_EXCEEDED_TO_PERFORM_CANCELLATION)) {
                    responsetxt = getString(R.string.web_services_response_196);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_METHOD_SERVICE_NOT_AVAILABLE)) {
                    responsetxt = getString(R.string.web_services_response_144);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                } else {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                }
                progressDialogAlodiga.dismiss();


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
                Session.setObjUserHasProducts(getListProduct(response));
                Session.setRechargeWhitCardIdTransaccion(response.getProperty("idTransaction").toString());

                Intent show;
                show = new Intent(RechargeWithCardStep4Activity.this, RechargeWithCardStep5Activity.class);
                startActivity(show);



            } else {

                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
            }

            //progressDialogAlodiga.dismiss();
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public void addTask() {
        //progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        //progressDialogAlodiga.show();
        mAuthTask_ = new AddContactTask();
        mAuthTask_.execute((Void) null);

    }


    public class AddContactTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            String prueba= Session.getNumberCard();
            String pr= Session.getAccountNumber();

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
                        isSaveTrue=false;

            } else {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
            }
            //progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask_ = null;
        }
    }
    protected ArrayList<ObjUserHasProduct> getListProduct(SoapObject response) {
        //ObjUserHasProduct[] obj2_aux= new ObjUserHasProduct[response.getPropertyCount()-3];
        //ObjUserHasProduct[] obj2 = new ObjUserHasProduct[response.getPropertyCount()-3];
        ArrayList<ObjUserHasProduct> obj2 = new ArrayList<>();
        for (int i = 4; i < response.getPropertyCount()-1; i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjUserHasProduct object = new ObjUserHasProduct(obj.getProperty("id").toString(), obj.getProperty("name").toString(), obj.getProperty("currentBalance").toString(), obj.getProperty("symbol").toString(), obj.getProperty("isPayTopUp").toString());
            if (object.getName().equals("Tarjeta Prepagada") || object.getName().equals("Prepaid Card") ){
                Session.setAffiliatedCard(Boolean.parseBoolean(Session.getPrepayCardAsociate()));
                object.setNumberCard(Session.getNumberCard_aux());
            }else{
                object.setNumberCard(Session.getAccountNumber_aux());
            }
            obj2.add(object);
            //obj2[i-3] = object;
        }

        return obj2;
    }


}
