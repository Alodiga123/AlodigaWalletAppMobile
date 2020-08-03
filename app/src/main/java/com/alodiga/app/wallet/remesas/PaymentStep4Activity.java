package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.duallibrary.model.ObjProccessRemittence;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class PaymentStep4Activity extends AppCompatActivity {
    private static Button process, backToLoginBtn;
    private static TextView reloadcard_source_, amount_to_send, Correspondent, delivery_method, shipping_rate, exchange_rate, Amount_to_deliver, total_to_pay, name, editTextTelephone, location, state, city, codezip, av, emailuser,lastName;
    static ProgressDialogAlodiga progressDialogAlodiga;
    private String responsetxt = "";
    private boolean serviceStatus;
    static SoapObject response;
    String datosRespuesta = "";
    private static String stringResponse = "";
    taskProcess mAuthTask_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step4_layout);
        process=findViewById(R.id.process);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);

        process.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //en proceso
            }
        });

        reloadcard_source_= findViewById(R.id.reloadcard_source_);
        Correspondent= findViewById(R.id.Correspondent);
        delivery_method= findViewById(R.id.delivery_method);
        shipping_rate= findViewById(R.id.shipping_rate);
        exchange_rate= findViewById(R.id.exchange_rate);
        Amount_to_deliver= findViewById(R.id.Amount_to_deliver);
        total_to_pay= findViewById(R.id.total_to_pay);

        name= findViewById(R.id.name);
        emailuser= findViewById(R.id.emailuser);
        lastName= findViewById(R.id.lastName);
        editTextTelephone= findViewById(R.id.editTextTelephone);
        location= findViewById(R.id.location);
        state= findViewById(R.id.state);
        city= findViewById(R.id.city);
        codezip= findViewById(R.id.codezip);
        av= findViewById(R.id.av);

        reloadcard_source_.setText(Session.getPay().getReloadcard_source().getName());
        Correspondent.setText(Session.getPay().getCorrespondent().getName());
        delivery_method.setText(Session.getPay().getDelivery_method().getName());
        shipping_rate.setText(Session.getPay().getShipping_rate());
        exchange_rate.setText(Session.getPay().getExchange_rate());
        Amount_to_deliver.setText(Session.getPay().getActual_amount_to_send());
        total_to_pay.setText(Session.getPay().getActual_amount_to_pay());

        name.setText(Session.getRemittenceDestinatario().getName()+" "+Session.getRemittenceDestinatario().getSecondname());
        emailuser.setText(Session.getRemittenceDestinatario().getEmail());
        lastName.setText(Session.getRemittenceDestinatario().getLastName() + " "+ Session.getRemittenceDestinatario().getSecondSurmane());
        editTextTelephone.setText(Session.getRemittenceDestinatario().getTelephone());
        location.setText(Session.getRemittenceDestinatario().getLocation().getName());
        state.setText(Session.getRemittenceDestinatario().getState().getName());
        city.setText(Session.getRemittenceDestinatario().getCity().getName());
        codezip.setText(Session.getRemittenceDestinatario().getCodeZip());
        av.setText(Session.getRemittenceDestinatario().getAv());




        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep4Activity.this, PaymentStep3Activity.class);
                startActivity(i);
                finish();
            }
        });


        process.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep4Activity.this, PaymentStep5Activity.class);
                startActivity(i);
                finish();*/
                procesar();
            }
        });



    }


    public void procesar(){

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask_ = new taskProcess();
        mAuthTask_.execute((Void) null);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(PaymentStep4Activity.this, PaymentStep3Activity.class);
        startActivity(i);
        finish();
    }

    public class taskProcess extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();

            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUserId());
                map.put("amountOrigin", Session.getObjResumeRemittence().getRealAmountToSend());
                map.put("totalAmount", Session.getObjResumeRemittence().getAmountToSendRemettence());
                map.put("amountDestiny", Session.getObjResumeRemittence().getReceiverAmount());
                //map.put("correspondentId", Session.getPay().getCorrespondent().getId());
                map.put("exchangeRateId",Session.getObjResumeRemittence().getExchangeRateSource().getId());
                map.put("ratePaymentNetworkId", Session.getObjResumeRemittence().getRatePaymentNetwork().getId());
                map.put("originCurrentId", Session.getObjResumeRemittence().getExchangeRateSource().getCurrency().getId());
                map.put("destinyCurrentId", Session.getObjResumeRemittence().getExchangeRateDestiny().getCurrency().getId());
                map.put("paymentNetworkId", Session.getObjResumeRemittence().getRatePaymentNetwork().getPaymentNetwork().getId());
                map.put("deliveryFormId", Session.getPay().getDelivery_method().getId());


                map.put("addressId", Session.getRemettencesDireccionId());

                if(Session.getRemettencesDireccionId().equals(Constants.REMITTENCE_ID)){
                    map.put("remittentCountryId", Session.getRemittenceRemitente().getLocation().getId());
                    map.put("remittentStateId", Session.getRemittenceRemitente().getState().getId());
                    map.put("remittentStateName", Session.getRemittenceRemitente().getState().getName());
                    map.put("remittentCityName", Session.getRemittenceRemitente().getCity().getName());
                    map.put("remittentCityId", Session.getRemittenceRemitente().getCity().getId());
                    map.put("remittentAddress", Session.getRemittenceRemitente().getAv());
                    map.put("remittentZipCode", Session.getRemittenceRemitente().getCodeZip());
                }else{
                    map.put("remittentCountryId", "");
                    map.put("remittentStateId",  "");
                    map.put("remittentStateName", "");
                    map.put("remittentCityName", "");
                    map.put("remittentCityId",  "");
                    map.put("remittentAddress",  "");
                    map.put("remittentZipCode",  "");
                }



                map.put("receiverFirstName", Session.getRemittenceDestinatario().getName());
                map.put("receiverMiddleName", Session.getRemittenceDestinatario().getSecondname());
                map.put("receiverLastName", Session.getRemittenceDestinatario().getLastName());
                map.put("receiverSecondSurname", Session.getRemittenceDestinatario().getSecondSurmane());
                map.put("receiverPhoneNumber", Session.getRemittenceDestinatario().getTelephone());
                map.put("receiverEmail", Session.getRemittenceDestinatario().getEmail());
                map.put("receiverCountryId", Session.getRemittenceDestinatario().getLocation().getId());
                map.put("receiverCityId", Session.getRemittenceDestinatario().getCity().getId());
                map.put("receiverCityName", Session.getRemittenceDestinatario().getCity().getName());
                map.put("receiverStateId", Session.getRemittenceDestinatario().getState().getId());
                map.put("receiverStateName", Session.getRemittenceDestinatario().getState().getName());
                map.put("receiverAddress", Session.getRemittenceDestinatario().getAv());
                map.put("receiverZipCode", Session.getRemittenceDestinatario().getCodeZip());


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_PROCESSREMETTENCEACCOUNT, Constants.ALODIGA);
                stringResponse = response.toString();
                responseCode = response.getProperty("codigoRespuesta").toString();
                //serviceAnswer(responseCode);


                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO_) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
                    responsetxt = getString(R.string.web_services_response_00);
                    serviceStatus = true;
                    return serviceStatus;

                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_AUTHENTICATION_FAILED)) {
                    responsetxt = getString(R.string.web_services_response_01_Remittence);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_MISSING_PARAMETERS)) {
                    responsetxt = getString(R.string.web_services_response_02_Remittence);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DISABLED_ACCOUNT)) {
                    responsetxt = getString(R.string.web_services_response_03_Remittence);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_RECORDS_NOT_FOUND)) {
                    responsetxt = getString(R.string.web_services_response_28_Remittence);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TOKEN_NOT_FOUND)) {
                    responsetxt = getString(R.string.web_services_response_50_Remittence);
                    serviceStatus = false;

                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_GENERAL_ERROR) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                } else {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                }
                progressDialogAlodiga.dismiss();
            } catch (IllegalArgumentException e) {
                progressDialogAlodiga.dismiss();
                responsetxt = getString(R.string.web_services_response_99);
                serviceStatus = false;
                e.printStackTrace();
                System.err.println(e);
                return false;
            } catch (Exception e) {
                progressDialogAlodiga.dismiss();
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

                ObjProccessRemittence processRemittence= new ObjProccessRemittence();

                processRemittence.setFechaHora(response.getProperty("fechaHora").toString());
                processRemittence.setCodigoRespuesta(response.getProperty("codigoRespuesta").toString());
                processRemittence.setMensajeRespuesta(response.getProperty("mensajeRespuesta").toString());
                processRemittence.setId(response.getProperty("id").toString());
                processRemittence.setApplicationDate(response.getProperty("applicationDate").toString());
                processRemittence.setCommentary(response.getProperty("commentary").toString());
                processRemittence.setAmountOrigin(response.getProperty("amountOrigin").toString());
                processRemittence.setTotalAmount(response.getProperty("totalAmount").toString());;
                processRemittence.setSendingOptionSMS(response.getProperty("sendingOptionSMS").toString());
                processRemittence.setAmountDestiny(response.getProperty("amountDestiny").toString());;
                processRemittence.setBank(response.getProperty("bank").toString());
                processRemittence.setPaymentServiceId(response.getProperty("paymentServiceId").toString());
                processRemittence.setAdditionalChanges(response.getProperty("additionalChanges").toString());
                processRemittence.setCreationDate(response.getProperty("creationDate").toString());
                processRemittence.setCreationHour(response.getProperty("CreationHour").toString());
                processRemittence.setLocalSales(response.getProperty("localSales").toString());
                processRemittence.setReserveField1(response.getProperty("reserveField1").toString());
                processRemittence.setRemittent(response.getProperty("remittent").toString());
                processRemittence.setReceiver(response.getProperty("receiver").toString());
                processRemittence.setCorrespondent(response.getProperty("Correspondent").toString());
                processRemittence.setAddressReciever(response.getProperty("addressReciever").toString());
                processRemittence.setExchangeRate(response.getProperty("exchangeRate").toString());
                processRemittence.setRatePaymentNetwork(response.getProperty("ratePaymentNetwork").toString());
                processRemittence.setLanguage(response.getProperty("language").toString());
                processRemittence.setOriginCurrent(response.getProperty("originCurrent").toString());
                processRemittence.setDestinyCurrent(response.getProperty("destinyCurrent").toString());
                processRemittence.setPaymentMethod(response.getProperty("paymentMethod").toString());
                processRemittence.setPaymentNetwork(response.getProperty("paymentNetwork").toString());
                processRemittence.setPaymentNetworkPoint(response.getProperty("paymentNetworkPoint").toString());
                processRemittence.setCashBox(response.getProperty("cashBox").toString());
                processRemittence.setCashier(response.getProperty("cashier").toString());
                processRemittence.setStatus(response.getProperty("status").toString());
                processRemittence.setDeliveryForm(response.getProperty("deliveryForm").toString());
                processRemittence.setAmountTransferTotal(response.getProperty("amountTransferTotal").toString());

                Session.setProcessRemittence(processRemittence);

                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep4Activity.this, PaymentStep5Activity.class);
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
            mAuthTask_ = null;
        }
    }





}
