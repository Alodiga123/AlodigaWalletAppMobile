package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterPais;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class PaymentStep1Activity extends AppCompatActivity {
    private static Button next, quote;
    private static TableLayout txtTableLayout;
    private static Spinner reloadcard_source, rate_included, destination_country, Correspondent, delivery_method;
    private String responsetxt = "";
    private boolean serviceStatus;
    static SoapObject response, response_;
    static ObjGenericObject[] listSpinner_reloadcard_source = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_rate_included = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_destination_country= new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_Correspondent= new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_delivery_method= new ObjGenericObject[0];



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


        //reloadcard_source
       /* new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES, Constants.ALODIGA);
                    stringResponse = response.toString();
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_reloadcard_source = getListGeneric(response);
                                SpinAdapterPais spinAdapterPais;
                                spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_reloadcard_source);
                                reloadcard_source.setAdapter(spinAdapterPais);
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


        //rate_included
        new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES, Constants.ALODIGA);
                    stringResponse = response.toString();
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_rate_included = getListGeneric(response);
                                SpinAdapterPais spinAdapterPais;
                                spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_rate_included);
                                rate_included.setAdapter(spinAdapterPais);
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
        }).start();*/

        //destination_country
        new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("login", Constants.USUARIO);
                    map.put("password", Constants.PASSWORD);

                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN, Constants.REMITTANCE);
                    stringResponse = response.toString();
                    responseCode = response.getProperty("code").toString();
                    serviceAnswer(responseCode);

                    if(serviceStatus){
                        String token= response.getProperty("token").toString();

                        HashMap<String, String> map_ = new HashMap<String, String>();
                        map_.put("token", token);

                        response = WebService.invokeGetAutoConfigString(map_, Constants.WEB_SERVICES_METHOD_COUNTRIES, Constants.REMITTANCE);
                        stringResponse = response.toString();
                        responseCode = response.getProperty("code").toString();
                        serviceAnswer(responseCode);

                        if (serviceStatus) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listSpinner_destination_country = getListGeneric_contry(response);
                                    SpinAdapterPais spinAdapterPais;
                                    spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_destination_country);
                                    destination_country.setAdapter(spinAdapterPais);
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

        //Correspondent
      /*  new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES, Constants.ALODIGA);
                    stringResponse = response.toString();
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_Correspondent = getListGeneric(response);
                                SpinAdapterPais spinAdapterPais;
                                spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_Correspondent);
                                Correspondent.setAdapter(spinAdapterPais);
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


        //delivery_method
        new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES, Constants.ALODIGA);
                    stringResponse = response.toString();
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_delivery_method = getListGeneric(response);
                                SpinAdapterPais spinAdapterPais;
                                spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_delivery_method);
                                delivery_method.setAdapter(spinAdapterPais);
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
        }).start();*/

        quote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                quote.setText(R.string.quote_);
                txtTableLayout.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);



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


}
