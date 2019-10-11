package com.example.c2paplicationmobile;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.ksoap2.serialization.SoapObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Manual_Removal_Activity extends AppCompatActivity  {
    private static FragmentManager fragmentManager;
    private EditText edtAmount,edtCOD,edtdescript;
    private Button signFind;
    private static String stringResponse = "";
    private String responsetxt = "";
    private boolean serviceStatus;
    String datosRespuesta = "";
    static SoapObject response;
    static ObjGenericObject[] listSpinner_pais = new ObjGenericObject[0];
    static ObjTransferMoney[] listSpinner_producto = new ObjTransferMoney[0];
    static ObjGenericObject[] listSpinner_banco = new ObjGenericObject[0];
    UserRemovalTask mAuthTask;
    ObjGenericObject getbank;
    ObjTransferMoney getproduct;
    String getaccountBank, getDescrip, getAmountRecharge;
    Spinner  spinner_pais,spinnerbank,spinnerproducto;
    static ProgressDialogAlodiga progressDialogAlodiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_removal_layout);
        spinner_pais = (Spinner) findViewById(R.id.spinner_pais);
        spinnerbank = (Spinner) findViewById(R.id.spinnerbank);
        spinnerproducto = (Spinner) findViewById(R.id.spinnerproducto);
        edtAmount= findViewById(R.id.edtAmount);
        signFind = (Button) findViewById(R.id.signFind);
        edtCOD= findViewById(R.id.edtCOD);
        edtdescript= findViewById(R.id.edtdescript);


        spinnerbank.setEnabled(false);
        spinnerproducto.setEnabled(false);

        progressDialogAlodiga = new ProgressDialogAlodiga(this,"cargando..");
        progressDialogAlodiga.show();

        //Spinner Pais
        new Thread(new Runnable() {
            public void run() {
                try{
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId",Session.getUserId());
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


        //Spinner Bank
        spinner_pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerbank.setEnabled(true);
                spinnerbank.setAdapter(null);
                spinnerproducto.setAdapter(null);

                final ObjGenericObject pais = (ObjGenericObject) spinner_pais.getSelectedItem();
                //Toast.makeText(getApplicationContext(),"id" + pais.getId() ,Toast.LENGTH_SHORT).show();

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

        //Spinner Producto
        spinnerbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinnerproducto.setEnabled(true);
                final ObjGenericObject bank = (ObjGenericObject) spinnerbank.getSelectedItem();
                //Toast.makeText(getApplicationContext(),"id" + bank.getId() ,Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    public void run() {
                        try{

                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("bankId",bank.getId().trim());
                            map.put("userId",Session.getUserId().trim());
                            response = webService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PRODUCT, Constants.ALODIGA);
                            stringResponse = response.toString();
                            responseCode = response.getProperty("codigoRespuesta").toString();
                            datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                            serviceAnswer(responseCode);

                            if (serviceStatus){


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listSpinner_producto = getListProduct(response);
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

        progressDialogAlodiga.dismiss();

        //Seteo de decimales al campo de monto
        edtAmount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().matches("^\\ (\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$"))
                {
                    String userInput= ""+s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length()-2, '.');
                    cashAmountBuilder.insert(0, ' ');

                    edtAmount.setText(cashAmountBuilder.toString());
                    // keeps the cursor always to the right
                    Selection.setSelection(edtAmount.getText(), cashAmountBuilder.toString().length());

                }

            }
        });

        signFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String getcuenta = edtCOD.getText().toString();
                String getmonto = edtAmount.getText().toString();
                String getdescrip = edtdescript.getText().toString();
                getproduct = (ObjTransferMoney) spinnerproducto.getSelectedItem();


                if (getcuenta.equals("") || getcuenta.length() == 0) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.recharge_id_invalid));
                /*} /*else if (getcuenta.length() != 20) {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.recharge_id_invalid_long));*/
                }else if (getmonto.equals("") || getmonto.length() == 0) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.amount_info_invalid));
                }else if (Float.parseFloat(getproduct.getCurrency().trim()) < Float.parseFloat(getmonto.trim())){
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.insuficient_balance));
                }else if (getdescrip.equals("") || getdescrip.length() == 0){
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.recharge_descrip_invalid));
                }else {
                    RemovalTask();
                }

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

    protected ObjTransferMoney[] getListProduct (SoapObject response){

        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount()-3];

        for(int i=3; i<response.getPropertyCount(); i++)
        {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(),obj.getProperty("name").toString() + " "+obj.getProperty("symbol").toString()+" - " +obj.getProperty("currentBalance").toString(),obj.getProperty("currentBalance").toString() );

            obj2[i-3] = object;
        }

        return obj2;
    }

    public void RemovalTask(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this,"cargando..");
        progressDialogAlodiga.show();
        mAuthTask = new UserRemovalTask();
        mAuthTask.execute((Void) null);

    }

    public class UserRemovalTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;

            getbank = (ObjGenericObject) spinnerbank.getSelectedItem();
            getproduct = (ObjTransferMoney) spinnerproducto.getSelectedItem();
            getaccountBank= edtCOD.getText().toString();
            getDescrip= edtdescript.getText().toString();
            getAmountRecharge= edtAmount.getText().toString();

            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String,String > map = new HashMap<String,String>();
                map.put("bankId",getbank.getId());
                map.put("emailUser",Session.getEmail());
                map.put("accountBank",getaccountBank);
                map.put("amountRecharge",getAmountRecharge);
                map.put("productId",getproduct.getId());
                map.put("conceptTransaction",getDescrip);


                response = webService.invokeGetAutoConfigString(map,Constants.WEB_SERVICES_METHOD_NAME_REMOVAL_MANUAL,Constants.ALODIGA);
                responseCode = response.getProperty("codigoRespuesta").toString();
                responseMessage = response.getProperty("mensajeRespuesta").toString();


                if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
                {

                    responsetxt = getString(R.string.web_services_response_00);
                    serviceStatus = true;
                    return serviceStatus;

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
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_AMOUNT_LIMIT))
                {
                    responsetxt = getString(R.string.web_services_response_30);
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_ACCOUNT))
                {
                    responsetxt = getString(R.string.web_services_response_31);
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_CUSTOMER))
                {
                    responsetxt = getString(R.string.web_services_response_32);
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_HAS_NOT_BALANCE))
                {
                    responsetxt = getString(R.string.web_services_response_33);
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO))
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
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                }else{
                    responsetxt = responseMessage;
                    serviceStatus = false;
                }
                //progressDialogAlodiga.dismiss();
            } catch (IllegalArgumentException e)
            {
                responsetxt = getString(R.string.web_services_response_99);
                e.printStackTrace();
                System.err.println(e);
                return false;
            } catch (Exception e)
            {
                responsetxt = getString(R.string.web_services_response_99);
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
                Intent show;
                show = new Intent(getApplicationContext(), Welcome_removal_Activity.class);
                startActivity(show);

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





