package com.alodiga.app.wallet.bank;

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
import android.widget.Toast;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterAccountType;
import com.alodiga.app.wallet.adapters.SpinAdapterBank;
import com.alodiga.app.wallet.adapters.SpinAdapterPais;
import com.alodiga.app.wallet.adapters.SpinAdapterProduct;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.manualRecharge.ManualRechargeStep2WelcomeActivity;
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


public class AddBankActivity extends AppCompatActivity {
    static SoapObject response;
    static SoapObject response2;
    static ObjGenericObject[] listSpinner_pais = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_banco = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_AccounType = new ObjGenericObject[0];
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static FragmentManager fragmentManager;
    private static String stringResponse = "";
    String datosRespuesta = "";
    UserAddBankTask userAddBankTask;
    ObjGenericObject getbank;
    ObjGenericObject getAccountType;
    String getAccountNumber;
    ObjTransferMoney getproduct;
    String getNumberOperation, getTrans, getAmountRecharge;
    Spinner spinner_pais, spinnerbank,spinner_accountype;
    private EditText edtCOD;
    private Button addBankButton, backToLoginBtn;

    private String responsetxt = "";
    private boolean serviceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();


        try {
            if(!(this.getIntent().getExtras().getString("value")=="")){
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        getIntent().getExtras().getString("value"));
            }
        } catch (NullPointerException e) {
            //"No viene nada"
        }

        setContentView(R.layout.activity_add_bank);
        spinner_pais = findViewById(R.id.spinner_pais);
        spinner_accountype = findViewById(R.id.spinner_accountype);
        spinnerbank = findViewById(R.id.spinnerbank);
        addBankButton = findViewById(R.id.addBankButton);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);
        edtCOD = findViewById(R.id.edtCOD);
        spinnerbank.setEnabled(false);


        //Spinner Country
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
                                listSpinner_pais = getListGeneric(response);
                                SpinAdapterPais spinAdapterPais;
                                spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_pais);
                                spinner_pais.setAdapter(spinAdapterPais);
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


        //Spinner Bank
        spinner_pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerbank.setEnabled(true);
                spinnerbank.setAdapter(null);
                final ObjGenericObject pais = (ObjGenericObject) spinner_pais.getSelectedItem();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("countryId", pais.getId());
                            response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_BANK, Constants.ALODIGA);
                            stringResponse = response.toString();
                            responseCode = response.getProperty("codigoRespuesta").toString();
                            datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                            serviceAnswer(responseCode);
                            if (serviceStatus) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listSpinner_banco = getListGeneric(response);
                                        SpinAdapterBank spinAdapterBank;
                                        spinAdapterBank = new SpinAdapterBank(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_banco);
                                        spinnerbank.setAdapter(spinAdapterBank);
                                    }
                                });


                            } else {
                                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                        responsetxt);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialogAlodiga.dismiss();


                    }
                }).start();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });




        new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();

                    response2 = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_ACCOUNT_TYPE_BANK, Constants.ALODIGA);
                    stringResponse = response2.toString();
                    responseCode = response2.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response2.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);
                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_AccounType = getListGenericAccountType(response2);
                                SpinAdapterAccountType spinAdapterAccountType;
                                spinAdapterAccountType = new SpinAdapterAccountType(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_AccounType);
                                spinner_accountype.setAdapter(spinAdapterAccountType);
                            }
                        });
                    } else {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                responsetxt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialogAlodiga.dismiss();
            }
        }).start();



        //Validar campos
        addBankButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getcuenta = edtCOD.getText().toString();
              if (getcuenta.equals("") || getcuenta.length() == 0) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.recharge_id_invalid));
                }  else {
                  UserAddBankTask();
                }
            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(AddBankActivity.this, MainActivity.class);
        startActivity(i);
        finish();
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

    protected ObjGenericObject[] getListGenericAccountType(SoapObject response) {
        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 3];
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjGenericObject object = new ObjGenericObject(obj.getProperty("description").toString(), obj.getProperty("id").toString());
            obj2[i - 3] = object;
        }
        return obj2;
    }



    protected ObjTransferMoney[] getListProduct(SoapObject response) {

        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];

        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }


    public void UserAddBankTask() {
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        userAddBankTask = new UserAddBankTask();
        userAddBankTask.execute((Void) null);

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

    public class UserAddBankTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;
            getbank = (ObjGenericObject) spinnerbank.getSelectedItem();
            getAccountType = (ObjGenericObject) spinner_accountype.getSelectedItem();
            getNumberOperation = edtCOD.getText().toString();
            try {
                String responseCode;
                String responseMessage = "";
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("bankId", getbank.getId());
                map.put("unifiedRegistryId",Session.getUserId());
                map.put("accountNumber",getNumberOperation);
                map.put("accountTypeBankId",getAccountType.getId());
                /*
                 @WebParam(name = "bankId") Long bankId,
                @WebParam(name = "emailUser") String emailUser,
                @WebParam(name = "referenceNumberOperation") String referenceNumberOperation,
                @WebParam(name = "amountRecharge") Float amountRecharge,
                @WebParam(name = "productId") Long productId,
                @WebParam(name = "conceptTransaction") String conceptTransaction) {
                return operations.ManualRecharge(bankId, emailUser, referenceNumberOperation, amountRecharge,
                 */
                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SAVE_ACCOUNT_BANK_USER, Constants.ALODIGA);
                responseCode = response.getProperty("codigoRespuesta").toString();
                responseMessage = response.getProperty("mensajeRespuesta").toString();
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
            userAddBankTask = null;
            //showProgress(false);
            if (success) {
                Intent show;
                show = new Intent(getApplicationContext(), AddBankSuccessfullActivity.class);
                startActivity(show);
                finish();

            } else {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
            }
            progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            userAddBankTask = null;
        }
    }
}