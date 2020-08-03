package com.alodiga.app.wallet.topup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterGeneric;
import com.alodiga.app.wallet.adapters.SpinAdapterPais;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.model.ObjTopUpInfos;
import com.alodiga.app.wallet.duallibrary.model.ObjtopUpInfos_IsOpenRange;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;


public class TopupStep1Activity extends AppCompatActivity {

    static ProgressDialogAlodiga progressDialogAlodiga;
    private static String stringResponse = "";
    SoapObject response;
    String datosRespuesta = "";
    ObjGenericObject[] listSpinner_pais = new ObjGenericObject[0];
    ObjGenericObject[] listSpinner_languaje = new ObjGenericObject[0];
    String getmobileNumberRegister, getmobileNumberRegisterR;
    ObjGenericObject getcountry, getlanguaje;
    Spinner spinnerCountry, spinnerIdioma;
    private Button next, backToLoginBtn;
    private String responsetxt = "";
    private ProcessTopup mAuthTask = null;
    private EditText mobileNumberRegister, mobileNumberRegisterR;

    private boolean serviceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup_step1_layout);
        next = findViewById(R.id.next);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerIdioma = findViewById(R.id.spinnerIdioma);
        mobileNumberRegister = findViewById(R.id.mobileNumberRegister);
        mobileNumberRegisterR = findViewById(R.id.mobileNumberRegisterR);


        //Spinner Country
        new Thread(new Runnable() {
            SoapObject response1;

            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    //map.put("userId", Session.getUserId());
                    response1 = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES_TOPUP, Constants.ALODIGA);
                    stringResponse = response1.toString();
                    responseCode = response1.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response1.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_pais = getListGeneric(response1, true);
                                SpinAdapterPais spinAdapterPais;
                                spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_pais);
                                spinnerCountry.setAdapter(spinAdapterPais);
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


        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ObjGenericObject obj = (ObjGenericObject) spinnerCountry.getSelectedItem();
                mobileNumberRegister.setText("+" + obj.getId());
                mobileNumberRegisterR.setText("+" + obj.getId());


                //openrange
                //mobileNumberRegister.setText("+5353693280");
                //mobileNumberRegisterR.setText("+93789113612");

                //denominacion fija
                //mobileNumberRegister.setText("+542612594080");
                //mobileNumberRegisterR.setText("+3054444441");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


//Spinner Languaje
        new Thread(new Runnable() {
            SoapObject response2;

            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response2 = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_LANGUAJE_TOPUP, Constants.ALODIGA);
                    stringResponse = response2.toString();
                    responseCode = response2.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response2.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_languaje = getListGeneric(response2, false);
                                SpinAdapterGeneric spinAdapterLanguaje;
                                spinAdapterLanguaje = new SpinAdapterGeneric(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_languaje);
                                spinnerIdioma.setAdapter(spinAdapterLanguaje);
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

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                getmobileNumberRegister = mobileNumberRegister.getText().toString();
                getmobileNumberRegisterR = mobileNumberRegisterR.getText().toString();
                getcountry = (ObjGenericObject) spinnerCountry.getSelectedItem();
                getlanguaje = (ObjGenericObject) spinnerIdioma.getSelectedItem();
                ObjGenericObject obj = (ObjGenericObject) spinnerCountry.getSelectedItem();

                if (getmobileNumberRegister.length() == 0 || getmobileNumberRegister.equals("") || getmobileNumberRegister.equals("+" + obj.getId())
                        || getmobileNumberRegisterR.length() == 0 || getmobileNumberRegisterR.equals("") || getmobileNumberRegisterR.equals("+" + obj.getId())) {


                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.invalid_all_question));
                } else {
                    Session.setPhonenumberTopup(getmobileNumberRegisterR);
                    Session.setNumberDestinationTopup(getmobileNumberRegister);
                    Session.setLanguajeTopup(getlanguaje.getId());
                    Session.setCountryTopup(getcountry.getName());

                    entrar();
                }

            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(TopupStep1Activity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(TopupStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();

    }
    public void entrar() {

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new ProcessTopup(Session.getPhonenumberTopup(), Session.getNumberDestinationTopup());
        mAuthTask.execute((Void) null);

    }

    public void serviceAnswer(String responseCode) {
        if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
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

    protected ObjGenericObject[] getListGeneric(SoapObject response, boolean isContry) {

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 3];
        ObjGenericObject object;
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            if (isContry) {
                object = new ObjGenericObject(obj.getProperty("name").toString(), obj.getProperty("code").toString());
            } else {
                object = new ObjGenericObject(obj.getProperty("description").toString(), obj.getProperty("id").toString());
            }
            obj2[i - 3] = object;
        }

        return obj2;
    }

    private ObjTopUpInfos[] getDatosDenominacionFija(SoapObject response) {
        ObjTopUpInfos[] obj2 = new ObjTopUpInfos[response.getPropertyCount() - 3];
        ObjTopUpInfos object;
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);

            object = new ObjTopUpInfos(
                    obj.getProperty("commissionPercent").toString(),
                    obj.getProperty("country").toString(),
                    obj.getProperty("coutryId").toString(),
                    obj.getProperty("denomination").toString(),
                    obj.getProperty("denominationReceiver").toString(),
                    obj.getProperty("denominationSale").toString(),
                    obj.getProperty("destinationCurrency").toString(),
                    obj.getProperty("isOpenRange").toString(),
                    obj.getProperty("operatorid").toString(),
                    obj.getProperty("opertador").toString(),
                    obj.getProperty("skuid").toString(),
                    obj.getProperty("wholesalePrice").toString());

            obj2[i - 3] = object;
        }

        return obj2;

    }

    public class ProcessTopup extends AsyncTask<Void, Void, Boolean> {


        private String Phone, receiverNumber;

        public ProcessTopup(String phone, String receiverNumber) {
            Phone = phone;
            this.receiverNumber = receiverNumber;
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
                    map.put("phoneNumber", Utils.processPhone(Phone));
                    map.put("receiverNumber", Utils.processPhone(receiverNumber));

                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_LIST_TOPUP, Constants.ALODIGA);
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
                        responsetxt = responseMessage;
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

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {


                SoapObject obj_isOpenRange = (SoapObject) response.getProperty(3);
                String isOpenRange = obj_isOpenRange.getProperty("isOpenRange").toString();
                if (isOpenRange.trim().equals("true")) {
                    Session.setTypeTopup(Constants.TOPUP_OR);

                    ObjtopUpInfos_IsOpenRange objIsOpenRange =
                            new ObjtopUpInfos_IsOpenRange(
                                    obj_isOpenRange.getProperty("commissionPercent").toString(),
                                    obj_isOpenRange.getProperty("country").toString(),
                                    obj_isOpenRange.getProperty("coutryId").toString(),
                                    obj_isOpenRange.getProperty("destinationCurrency").toString(),
                                    obj_isOpenRange.getProperty("increment").toString(),
                                    obj_isOpenRange.getProperty("isOpenRange").toString(),
                                    obj_isOpenRange.getProperty("maximumAmount").toString(),
                                    obj_isOpenRange.getProperty("minimumAmount").toString(),
                                    obj_isOpenRange.getProperty("operatorid").toString(),
                                    obj_isOpenRange.getProperty("opertador").toString(),
                                    obj_isOpenRange.getProperty("skuid").toString(),
                                    obj_isOpenRange.getProperty("wholesalePrice").toString());
                    Session.setObjIsOpenRangeTopup(objIsOpenRange);
                } else {
                    Session.setTypeTopup(Constants.TOPUP_DF);
                    Session.setDatosDenominacionFijaTopup(getDatosDenominacionFija(response));
                }


                Intent i = new Intent(TopupStep1Activity.this, TopupStep2Activity.class);
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
