package com.example.c2paplicationmobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class Manual_Removal_Activity extends AppCompatActivity {


    private static String stringResponse = "";
    private String responsetxt = "";
    private boolean serviceStatus;
    String datosRespuesta = "";
    static SoapObject response;
    static ObjGenericObject[] listSpinner_pais = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_producto = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_banco = new ObjGenericObject[0];


    static ProgressDialogAlodiga progressDialogAlodiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_removal_layout);
        final Spinner  spinner_pais = (Spinner) findViewById(R.id.spinner_pais);
        final Spinner  spinnerbank = (Spinner) findViewById(R.id.spinnerbank);
        final Spinner  spinnerproducto = (Spinner) findViewById(R.id.spinnerproducto);

        spinnerbank.setEnabled(false);
        spinnerproducto.setEnabled(false);

        //progressDialogAlodiga = new ProgressDialogAlodiga(this,"cargando..");
        //progressDialogAlodiga.show();

        new Thread(new Runnable() {
            public void run() {
                try{
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    response = webService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES, Constants.ALODIGA);
                    stringResponse = response.toString();
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listSpinner_pais = getListGeneric(response);
                            SpinAdapterPais spinAdapterPais;
                            spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_pais);
                            spinner_pais.setAdapter(spinAdapterPais);

                        }
                    });}else {

                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                responsetxt);
                    }

                }catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        }).start();


        spinner_pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinnerbank.setEnabled(true);
                final ObjGenericObject pais = (ObjGenericObject) spinner_pais.getSelectedItem();
                Toast.makeText(getApplicationContext(),"id" + pais.getId() ,Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                    public void run() {
                        try{

                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("countryId",pais.getId());
                            response = webService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_BANK, Constants.ALODIGA);
                            stringResponse = response.toString();
                            responseCode = response.getProperty("codigoRespuesta").toString();
                            datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                            serviceAnswer(responseCode);

                            if (serviceStatus){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listSpinner_banco = getListGeneric(response);
                                    SpinAdapterBank spinAdapterBank;
                                    spinAdapterBank = new SpinAdapterBank(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_banco);
                                    spinnerbank.setAdapter(spinAdapterBank);
                                }
                            });

                            }else{
                                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                        responsetxt);
                            }

                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        spinnerbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinnerproducto.setEnabled(true);
                final ObjGenericObject bank = (ObjGenericObject) spinnerbank.getSelectedItem();
                Toast.makeText(getApplicationContext(),"id" + bank.getId() ,Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    public void run() {
                        try{

                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("BankId",bank.getId());
                            response = webService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PRODUCT, Constants.ALODIGA);
                            stringResponse = response.toString();
                            responseCode = response.getProperty("codigoRespuesta").toString();
                            datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                            serviceAnswer(responseCode);

                            if (serviceStatus){


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listSpinner_producto = getListGeneric(response);
                                    SpinAdapterProduct spinAdapterProduct;
                                    spinAdapterProduct = new SpinAdapterProduct(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_producto);
                                    spinnerproducto.setAdapter(spinAdapterProduct);
                                }
                            });}else{
                                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                    responsetxt);
                            }




                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }



    protected ObjGenericObject[] getListGeneric (SoapObject response){

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount()-3];

        for(int i=3; i<response.getPropertyCount(); i++)
        {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjGenericObject object = new ObjGenericObject(obj.getProperty("name").toString(),obj.getProperty("id").toString());
           
            obj2[i-3] = object;
        }

        return obj2;
    }


public void serviceAnswer(String responseCode ){
    if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
    {
        responsetxt = getString(R.string.web_services_response_00);
        serviceStatus = true;
        //return serviceStatus;

    }
    else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS))
    {
        responsetxt = getString(R.string.web_services_response_01);
        serviceStatus = false;

    } else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA))
    {
        responsetxt = getString(R.string.web_services_response_03);
        serviceStatus = false;
    }
    else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA))
    {
        responsetxt = getString(R.string.web_services_response_04);
        serviceStatus = false;
    }
    else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS))
    {
        responsetxt = getString(R.string.web_services_response_05);
        serviceStatus = false;
    }
    else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO))
    {
        responsetxt = getString(R.string.web_services_response_06);
        serviceStatus = false;
    }
    else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE))
    {
        responsetxt = getString(R.string.web_services_response_08);
        serviceStatus = false;
    }
    else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO))
    {
        responsetxt = getString(R.string.web_services_response_12);
        serviceStatus = false;
    }
    else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO))
    {
        responsetxt = getString(R.string.web_services_response_95);
        serviceStatus = false;
    }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE))
    {
        responsetxt = getString(R.string.web_services_response_96);
        serviceStatus = false;
    }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE))
    {
        responsetxt = getString(R.string.web_services_response_97);
        serviceStatus = false;
    }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES))
    {
        responsetxt = getString(R.string.web_services_response_98);
        serviceStatus = false;
    }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO))
    {
        responsetxt = getString(R.string.web_services_response_99);
        serviceStatus = false;
    }

}








}





