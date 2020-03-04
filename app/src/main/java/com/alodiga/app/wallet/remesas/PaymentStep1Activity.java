package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterGenericRemittence;
import com.alodiga.app.wallet.adapters.SpinAdapterPais;
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoney;
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoneyRemittence;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjCountry;
import com.alodiga.app.wallet.model.ObjCurrency;
import com.alodiga.app.wallet.model.ObjExchangeRate;
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.model.ObjPaymentNetwork;
import com.alodiga.app.wallet.model.ObjRatePaymentNetwork;
import com.alodiga.app.wallet.model.ObjRemittencePay;
import com.alodiga.app.wallet.model.ObjResumeRemittence;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentStep1Activity extends AppCompatActivity {
    private static Button next, quote;
    private static TableLayout txtTableLayout;
    private static Spinner reloadcard_source, rate_included_, destination_country, Correspondent, delivery_method;
    private static Switch  rate_included;
    private static TextView exchange_rate, shipping_rate, actual_amount_to_send, Actual_amount_to_pay;
    private static EditText amount;
    private String responsetxt = "";
    private boolean serviceStatus;
    static SoapObject response, response_, response_reloadcard_source, response_destination_country, response_Correspondent,response_delivery_method;
    static ObjTransferMoney[] listSpinner_reloadcard_source;
    static ObjGenericObject[] listSpinner_destination_country= new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_Correspondent= new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_delivery_method= new ObjGenericObject[0];
    static String token;
    ObjRemittencePay pay= new ObjRemittencePay();
    ObjResumeRemittence  ObjResume= new ObjResumeRemittence();

    taskResume mAuthTask;
    taskToken mAuthTask_;
    static ProgressDialogAlodiga progressDialogAlodiga;



    String datosRespuesta = "";
    private static String stringResponse = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step1_layout);
        next=findViewById(R.id.next);
        quote= findViewById(R.id.quote);
        txtTableLayout= findViewById(R.id.txtTableLayout);
        txtTableLayout.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);

        reloadcard_source= findViewById(R.id.reloadcard_source);
        rate_included= findViewById(R.id.rate_included);
        destination_country=findViewById(R.id.destination_country);
        Correspondent=findViewById(R.id.Correspondent);
        delivery_method=findViewById(R.id.delivery_method);

        amount=findViewById(R.id.amount_);
        exchange_rate= findViewById(R.id.exchange_rate);
        shipping_rate= findViewById(R.id.shipping_rate);
        actual_amount_to_send= findViewById(R.id.actual_amount_to_send);
        Actual_amount_to_pay= findViewById(R.id.Actual_amount_to_pay);

        //Spinner producto a debitar
        new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response_reloadcard_source = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETPRODUCTSREMETTENCEBYUSER, Constants.ALODIGA);
                    //stringResponse = response_reloadcard_source.toString();
                    responseCode = response_reloadcard_source.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response_reloadcard_source.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_reloadcard_source = getListProduct(response_reloadcard_source);
                                SpinAdapterTransferMoneyRemittence spinAdapterProduct;
                                spinAdapterProduct = new SpinAdapterTransferMoneyRemittence(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_reloadcard_source);
                                reloadcard_source.setAdapter(spinAdapterProduct);
                            }
                        });
                    } else {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                responsetxt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        //destination_country
        new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("login", Constants.USUARIO);
                    map.put("password", Constants.PASSWORD);

                    response_destination_country = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN,Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);
                    //stringResponse = response_destination_country.toString();
                    responseCode = response_destination_country.getProperty("code").toString();
                    serviceAnswer(responseCode);

                    if(serviceStatus){
                        token= response_destination_country.getProperty("token").toString();

                        HashMap<String, String> map_ = new HashMap<String, String>();
                        map_.put("token", token);

                        response_destination_country = WebService.invokeGetAutoConfigString(map_, Constants.WEB_SERVICES_METHOD_COUNTRIES, Constants.REMITTANCE,Constants.CONSTANT_WSREMITTENCEMOBILE);
                        //stringResponse = response_destination_country.toString();
                        responseCode = response_destination_country.getProperty("code").toString();
                        serviceAnswer(responseCode);

                        if (serviceStatus) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listSpinner_destination_country = getListGeneric_contry(response_destination_country);
                                    SpinAdapterGenericRemittence spinAdapter;
                                    spinAdapter = new SpinAdapterGenericRemittence(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_destination_country);
                                    destination_country.setAdapter(spinAdapter);
                                }
                            });
                        } else {
                            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                    responsetxt);
                        }


                    }else{

                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                responsetxt);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        destination_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Correspondent.setEnabled(true);
                delivery_method.setAdapter(null);

                final ObjGenericObject pais = (ObjGenericObject) destination_country.getSelectedItem();
                //Toast.makeText(getApplicationContext(),"id" + pais.getId() ,Toast.LENGTH_SHORT).show();

                //Correspondent
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("login", Constants.USUARIO);
                            map.put("password", Constants.PASSWORD);

                            response_Correspondent = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN, Constants.REMITTANCE,Constants.CONSTANT_WSREMITTENCEMOBILE);
                            //stringResponse = response_Correspondent.toString();
                            responseCode = response_Correspondent.getProperty("code").toString();
                            serviceAnswer(responseCode);

                            if(serviceStatus){
                                token= response_Correspondent.getProperty("token").toString();

                                HashMap<String, String> map_c = new HashMap<String, String>();
                                map_c.put("token", token);
                                map_c.put("countryId", pais.getId());
                                response_Correspondent = WebService.invokeGetAutoConfigString(map_c, Constants.WEB_SERVICES_METHOD_PAYMENTBYCONTRY, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);
                                //stringResponse = response_Correspondent.toString();
                                responseCode = response_Correspondent.getProperty("code").toString();

                                serviceAnswer(responseCode);

                                if (serviceStatus) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listSpinner_Correspondent = getListGeneric_contry(response_Correspondent);
                                            SpinAdapterGenericRemittence spinAdapter;
                                            spinAdapter = new SpinAdapterGenericRemittence(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_Correspondent);
                                            Correspondent.setAdapter(spinAdapter);
                                        }
                                    });
                                } else {
                                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                            responsetxt);
                                }


                            }else{

                                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                        responsetxt);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Correspondent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                delivery_method.setEnabled(true);

                final ObjGenericObject correspondent = (ObjGenericObject) Correspondent.getSelectedItem();
                //Toast.makeText(getApplicationContext(),"id" + pais.getId() ,Toast.LENGTH_SHORT).show();

                //Correspondent
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("login", Constants.USUARIO);
                            map.put("password", Constants.PASSWORD);

                            response_delivery_method = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN, Constants.REMITTANCE,Constants.CONSTANT_WSREMITTENCEMOBILE);
                            //stringResponse = response_delivery_method.toString();
                            responseCode = response_delivery_method.getProperty("code").toString();
                            serviceAnswer(responseCode);

                            if(serviceStatus){
                                token= response_delivery_method.getProperty("token").toString();

                                HashMap<String, String> map_c = new HashMap<String, String>();
                                map_c.put("token", token);
                                map_c.put("paymentNetworkId", correspondent.getId());
                                response_delivery_method = WebService.invokeGetAutoConfigString(map_c, Constants.WEB_SERVICES_METHOD_LOADDELIVERYFORMBYPAYMENTNETWORK, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);
                                //stringResponse = response_delivery_method.toString();
                                responseCode = response_delivery_method.getProperty("code").toString();

                                serviceAnswer(responseCode);

                                if (serviceStatus) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listSpinner_delivery_method = getListGeneric_contry(response_delivery_method);
                                            SpinAdapterGenericRemittence spinAdapter;
                                            spinAdapter = new SpinAdapterGenericRemittence(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_delivery_method);
                                            delivery_method.setAdapter(spinAdapter);
                                        }
                                    });
                                } else {
                                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                            responsetxt);
                                }


                            }else{

                                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                        responsetxt);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //Seteo de decimales al campo de monto
        amount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("^\\ (\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');
                    cashAmountBuilder.insert(0, ' ');

                    amount.setText(cashAmountBuilder.toString());
                    // keeps the cursor always to the right
                    Selection.setSelection(amount.getText(), cashAmountBuilder.toString().length());

                }

            }
        });

        quote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String amount_= amount.getText().toString();

                if (amount_.equals("") || amount_.length() == 0) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.amount_info_invalid));
                }else{
                    cotizar();
                }

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep1Activity.this, PaymentStep2Activity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void cotizar(){
        boolean isStatusRate= rate_included.isChecked();
        pay.setRate_included(isStatusRate);
        txtTableLayout.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask_ = new taskToken();
        mAuthTask_.execute((Void) null);

    }


    public void Thread2(String token){
        //progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        //progressDialogAlodiga.show();
        mAuthTask = new taskResume(token);
        mAuthTask.execute((Void) null);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(PaymentStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


    public void serviceAnswer(String responseCode) {
        if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO_) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
            responsetxt = getString(R.string.web_services_response_00);
            serviceStatus = true;
            //return serviceStatus;
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
        }
    }

    protected ObjGenericObject[] getListGeneric(SoapObject response) {

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 3];

        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjGenericObject object = new ObjGenericObject(obj.getProperty("name").toString(), obj.getProperty("id").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }

    protected ObjGenericObject[] getListGeneric_contry(SoapObject response) {

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 2];

        for (int i = 2; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjGenericObject object = new ObjGenericObject(obj.getProperty("name").toString(), obj.getProperty("id").toString());
            obj2[i - 2] = object;
        }

        return obj2;
    }



    public class taskToken extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();

            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("login", Constants.USUARIO);
                map.put("password", Constants.PASSWORD);

                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);
                stringResponse = response.toString();
                responseCode = response.getProperty("code").toString();
                serviceAnswer(responseCode);


                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO_) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

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
                token= response.getProperty("token").toString();

               Thread2(token);

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





    public class taskResume extends AsyncTask<Void, Void, Boolean> {

        private final String token;

        public taskResume(String token) {
            this.token = token;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            WebService webService = new WebService();

            pay.setDestination_country((ObjGenericObject) destination_country.getSelectedItem());
            pay.setCorrespondent((ObjGenericObject) Correspondent.getSelectedItem());
            pay.setDelivery_method((ObjGenericObject) delivery_method.getSelectedItem());
            pay.setAmount_(amount.getText().toString());


            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("token", token);
                map.put("countrySourceId", Session.getDireccionUsuario().getPaisId());
                map.put("countryId", pay.getDestination_country().getId());
                map.put("ratePaymentNetworkId", pay.getDelivery_method().getId());
                map.put("realAmountToSend", pay.getAmount_());
                map.put("isIncludeRate", String.valueOf(pay.getRate_included()));


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_REMETTENCE_SUMARY, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);
                responseCode = response.getProperty("code").toString();
                //responseMessage = response.getProperty("message").toString();



                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO_) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

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
            mAuthTask = null;
            //showProgress(false);
            if (success) {
                String amountToSendRemettence= response.getProperty("amountToSendRemettence").toString();
                String receiverAmount = response.getProperty("receiverAmount").toString();

                /**exchangeRateDestiny**/
                SoapObject res_exchangeRateDestiny = (SoapObject) response.getProperty("exchangeRateDestiny");

                SoapObject res_exchangeRateDestiny_beginingDate = (SoapObject) res_exchangeRateDestiny.getProperty("beginingDate");
                String beginingDate_nanos= res_exchangeRateDestiny_beginingDate.getProperty("nanos").toString();

                SoapObject res_exchangeRateDestiny_country = (SoapObject) res_exchangeRateDestiny.getProperty("country");
                ObjCountry country= new ObjCountry(res_exchangeRateDestiny_country.getProperty("id").toString(),
                        res_exchangeRateDestiny_country.getProperty("name").toString(),
                        res_exchangeRateDestiny_country.getProperty("alternativeName3").toString(),
                        res_exchangeRateDestiny_country.getProperty("code").toString(),
                        res_exchangeRateDestiny_country.getProperty("shortName").toString());

                SoapObject res_exchangeRateDestiny_currency = (SoapObject) res_exchangeRateDestiny.getProperty("currency");
                ObjCurrency currency= new ObjCurrency(res_exchangeRateDestiny_currency.getProperty("id").toString(),
                        res_exchangeRateDestiny_currency.getProperty("iso").toString(),
                        res_exchangeRateDestiny_currency.getProperty("name").toString(),
                        res_exchangeRateDestiny_currency.getProperty("symbol").toString());

                //Parte 1_final
                ObjExchangeRate exchangeRateDestiny=new ObjExchangeRate(res_exchangeRateDestiny.getProperty("amount").toString(),
                        beginingDate_nanos,country,currency, res_exchangeRateDestiny.getProperty("id").toString()) ;

                /**exchangeRateSource**/

                SoapObject res_exchangeRateSource = (SoapObject) response.getProperty("exchangeRateSource");

                SoapObject res_exchangeRateSource_beginingDate = (SoapObject) res_exchangeRateSource.getProperty("beginingDate");
                String beginingDate_nanos_= res_exchangeRateSource_beginingDate.getProperty("nanos").toString();

                SoapObject res_exchangeRateSource_country = (SoapObject) res_exchangeRateSource.getProperty("country");
                ObjCountry country_= new ObjCountry(res_exchangeRateSource_country.getProperty("id").toString(),
                        res_exchangeRateSource_country.getProperty("name").toString(),
                        res_exchangeRateSource_country.getProperty("alternativeName2").toString(),
                        res_exchangeRateSource_country.getProperty("alternativeName3").toString(),
                        res_exchangeRateSource_country.getProperty("code").toString(),
                        res_exchangeRateSource_country.getProperty("shortName").toString(),
                        null);

                SoapObject res_xchangeRateSource_currency = (SoapObject) res_exchangeRateDestiny.getProperty("currency");
                ObjCurrency currency_= new ObjCurrency(res_exchangeRateDestiny_currency.getProperty("id").toString(),
                        res_exchangeRateDestiny_currency.getProperty("iso").toString(),
                        res_exchangeRateDestiny_currency.getProperty("name").toString(),
                        res_exchangeRateDestiny_currency.getProperty("symbol").toString());

                //Parte 2_final
                ObjExchangeRate  exchangeRateSource=new ObjExchangeRate(res_exchangeRateSource.getProperty("amount").toString(),
                        beginingDate_nanos_,country_,currency_, res_exchangeRateSource.getProperty("id").toString());

                /**ratePaymentNetwork***/
                SoapObject res_ratePaymentNetwork = (SoapObject) response.getProperty("ratePaymentNetwork");


                SoapObject res_ratePaymentNetwork_beginingDate = (SoapObject) res_ratePaymentNetwork.getProperty("beginingDate");
                String beginingDate_nanos_1= res_ratePaymentNetwork_beginingDate.getProperty("nanos").toString();
                String id=res_ratePaymentNetwork.getProperty("id").toString();


                SoapObject res_ratePaymentNetwork_paymentNetwork = (SoapObject) res_ratePaymentNetwork.getProperty("paymentNetwork");
                String id_1= res_ratePaymentNetwork_paymentNetwork.getProperty("id").toString();
                String name_=res_ratePaymentNetwork_paymentNetwork.getProperty("name").toString();
                String enabled= res_ratePaymentNetwork_paymentNetwork.getProperty("enabled").toString();
                SoapObject res_ratePaymentNetwork_country = (SoapObject) res_ratePaymentNetwork_paymentNetwork.getProperty("country");

                ObjCountry country_1= new ObjCountry(res_ratePaymentNetwork_country.getProperty("id").toString(),
                        res_ratePaymentNetwork_country.getProperty("name").toString(),
                        res_ratePaymentNetwork_country.getProperty("alternativeName3").toString(),
                        res_ratePaymentNetwork_country.getProperty("code").toString(),
                        res_ratePaymentNetwork_country.getProperty("shortName").toString(),
                        res_ratePaymentNetwork_country.getProperty("iso").toString());

                ObjPaymentNetwork paymentNetwork= new ObjPaymentNetwork(res_ratePaymentNetwork_paymentNetwork.getProperty("id").toString(),
                        res_ratePaymentNetwork_paymentNetwork.getProperty("name").toString(),
                        res_ratePaymentNetwork_paymentNetwork.getProperty("enabled").toString(),
                        country_1);

                //Parte 3_final
                ObjRatePaymentNetwork ratePaymentNetwork= new ObjRatePaymentNetwork(res_ratePaymentNetwork.getProperty("amount").toString(),
                        beginingDate_nanos_1,res_ratePaymentNetwork.getProperty("id").toString(),paymentNetwork);

                /***OBJETO FINAL****/
                ObjResume = new ObjResumeRemittence(amountToSendRemettence,
                        receiverAmount,
                        exchangeRateDestiny,
                        exchangeRateSource,
                        ratePaymentNetwork);

                String hola=ObjResume.getAmountToSendRemettence();

                Session.setObjResumeRemittence(ObjResume);

                ObjResumeRemittence prueba = Session.getObjResumeRemittence();

                /**SETEO DE CAMPOS**/
                exchange_rate.setText(Session.getObjResumeRemittence().getExchangeRateSource().getAmount());
                shipping_rate.setText(Session.getObjResumeRemittence().getRatePaymentNetwork().getAmount());
                actual_amount_to_send.setText(Session.getObjResumeRemittence().getReceiverAmount());
                Actual_amount_to_pay.setText(Session.getObjResumeRemittence().getAmountToSendRemettence());


                String exchange = Session.getObjResumeRemittence().getExchangeRateSource().getAmount();
                String shipping = Session.getObjResumeRemittence().getRatePaymentNetwork().getAmount();
                String actual = Session.getObjResumeRemittence().getReceiverAmount();
                String Actual = Session.getObjResumeRemittence().getAmountToSendRemettence();

                quote.setText(R.string.quote_);
                txtTableLayout.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

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


    protected ObjTransferMoney[] getListProduct(SoapObject response) {
        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }

}
