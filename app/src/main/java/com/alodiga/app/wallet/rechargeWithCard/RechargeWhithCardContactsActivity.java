package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.AdapterCardContacts;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjCompanionCards;
import com.alodiga.app.wallet.model.ObjCreditCardTypeId;
import com.alodiga.app.wallet.model.ObjPaymentInfo;
import com.alodiga.app.wallet.model.ObjTarjetahabiente;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class RechargeWhithCardContactsActivity extends AppCompatActivity {

    private static Spinner spinnerProduct;
    private Button step1_next_button, backToLoginBtn, add;
    private RecyclerView mRecyclerView;
    private AdapterCardContacts mAdapter;
    private List<ObjTarjetahabiente> mProductList;
    private List<ObjPaymentInfo> mPaymentList;
    private String responsetxt = "";
    private boolean serviceStatus;
    static ProgressDialogAlodiga progressDialogAlodiga2;
    private SoapObject response, response_;
    UserGetListContact mAuthTask_;
    String responseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_card_contact);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);
        add= findViewById(R.id.add);

        getList();

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
                finish();
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                Intent show;
                show = new Intent(getApplicationContext(), RechargeWithCardContactsAddActivity.class);
                startActivity(show);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RechargeWhithCardContactsActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    public void getList(){
        progressDialogAlodiga2 = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga2.show();
        mAuthTask_ = new UserGetListContact();
        mAuthTask_.execute((Void) null);
    }

    public class UserGetListContact extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("userId", Session.getUserId());


                response_ = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETPAYMENTINFO, Constants.ALODIGA);
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
            mAuthTask_ = null;
            //showProgress(false);
            if (success) {
                Session.setIsConstantsEmpty(false);

                try {
                    mPaymentList=getlistPaymentInfo(response_);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                mRecyclerView = findViewById(R.id.idRecyclerView);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mAdapter = new AdapterCardContacts(mPaymentList, RechargeWhithCardContactsActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                    registerForContextMenu(mRecyclerView);
;

                progressDialogAlodiga2.dismiss();

            } else {

                if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NOT_ASSOCIATED_PAYMENT_INFO)){
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.web_services_response_220));

                    Session.setIsConstantsEmpty(true);

                    Intent i = new Intent(RechargeWhithCardContactsActivity.this, RechargeWithCardStep1Activity.class);
                    startActivity(i);
                    finish();

                }else{

                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            responsetxt);

                    Intent i = new Intent(RechargeWhithCardContactsActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }


            }

        }



        @Override
        protected void onCancelled() {
            mAuthTask_ = null;
        }
    }

     List<ObjPaymentInfo> getlistPaymentInfo(SoapObject response ) throws ParseException {

          List<ObjPaymentInfo> listPayment= new ArrayList<ObjPaymentInfo>();
          ObjPaymentInfo payment;
         Date date = null;
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

         for (int i = 3; i < response.getPropertyCount(); i++) {
             payment= new ObjPaymentInfo();
             SoapObject obj = (SoapObject) response.getProperty(i);
             payment.setCreditCardCVV(obj.getProperty("creditCardCVV").toString());
             payment.setCreditCardDate(format.parse(obj.getProperty("creditCardDate").toString()));
             payment.setYear(obj.getProperty("creditCardDate").toString().split("-")[0]);
             payment.setMonth(obj.getProperty("creditCardDate").toString().split("-")[1]);
             payment.setCreditCardName(obj.getProperty("creditCardName").toString());
             payment.setCreditCardNumber(Utils.aloEncrpter(obj.getProperty("creditCardNumber").toString()));
             SoapObject creditCard = (SoapObject) obj.getProperty("creditCardTypeId");
             payment.setCreditCardTypeId( new ObjCreditCardTypeId(creditCard.getProperty("enabled").toString(),creditCard.getProperty("id").toString(),creditCard.getProperty("lengh").toString(),creditCard.getProperty("name").toString()));
             payment.setEnabled(Boolean.parseBoolean(obj.getProperty("enabled").toString()));
             payment.setId(obj.getProperty("id").toString());
             SoapObject paymentPatner = (SoapObject) obj.getProperty("paymentPatnerId");
             payment.setPaymentPatnerId(paymentPatner.getProperty("id").toString());
             SoapObject paymentType = (SoapObject) obj.getProperty("paymentTypeId");
             payment.setPaymentTypeId(paymentType.getProperty("id").toString());
             payment.setUserId(obj.getProperty("userId").toString());

             listPayment.add(payment);

         }
        return listPayment;
    }
}
