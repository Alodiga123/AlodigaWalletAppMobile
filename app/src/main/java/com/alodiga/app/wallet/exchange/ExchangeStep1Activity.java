package com.alodiga.app.wallet.exchange;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterProduct;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.duallibrary.model.ObjExchange;
import com.alodiga.app.wallet.duallibrary.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ExchangeStep1Activity extends AppCompatActivity {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static String stringResponse = "";
    SoapObject response;
    String datosRespuesta = "";
    private String responsetxt = "";
    private boolean serviceStatus;
    Spinner spinnerProduct1, spinnerProduct2;
    static ObjTransferMoney[] listSpinner_product1 = new ObjTransferMoney[0];
    static ObjTransferMoney[] listSpinner_product2;
    EditText  edtAmount;
    Button next, backToLoginBtn;
    CheckBox checkBox;
    private ProcessPreviewExange mAuthTask = null;
    ObjExchange exchange_aux= new ObjExchange();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_step1_layout);
        spinnerProduct1= findViewById(R.id.spinnerProduct1);
        spinnerProduct2= findViewById(R.id.spinnerProduct2);
        edtAmount= findViewById(R.id.edtAmount);
        next= findViewById(R.id.next);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);
        checkBox= findViewById(R.id.checkBox);
        //Spinner Languaje
        new Thread(new Runnable() {
            SoapObject response2;

            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response2 = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PRODUCT_EXCHANGE, Constants.ALODIGA);
                    stringResponse = response2.toString();
                    responseCode = response2.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response2.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_product1 = getListProduct(response2);
                                SpinAdapterProduct spinAdapterProduct1;
                                spinAdapterProduct1 = new SpinAdapterProduct(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_product1);
                                spinnerProduct1.setAdapter(spinAdapterProduct1);
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



        spinnerProduct1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ObjTransferMoney product1Selected = (ObjTransferMoney) spinnerProduct1.getSelectedItem();
                listSpinner_product2=new ObjTransferMoney[listSpinner_product1.length-1];
                int aux=0;
                for (int j = 0; j < listSpinner_product1.length; j++) {
                    if(listSpinner_product1[j].getId()!=product1Selected.getId()){
                        listSpinner_product2[aux] =listSpinner_product1[j];
                        aux=aux+1;
                    }
                }

                SpinAdapterProduct spinAdapterProduct2;
                spinAdapterProduct2 = new SpinAdapterProduct(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_product2);
                spinnerProduct2.setAdapter(spinAdapterProduct2);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //Seteo de decimales al campo de monto
        edtAmount.addTextChangedListener(new TextWatcher() {
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

                    edtAmount.setText(cashAmountBuilder.toString());
                    // keeps the cursor always to the right
                    Selection.setSelection(edtAmount.getText(), cashAmountBuilder.toString().length());

                }

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                ObjTransferMoney product1Selected = (ObjTransferMoney) spinnerProduct1.getSelectedItem();
                ObjTransferMoney product2Selected = (ObjTransferMoney) spinnerProduct2.getSelectedItem();
                String edtAmount_text=  edtAmount.getText().toString();
                boolean isChecked = checkBox.isChecked();

                exchange_aux.setExange_productSource(product1Selected);
                exchange_aux.setExange_productDestination(product2Selected);
                exchange_aux.setExange_includedAmount((isChecked) ? "1" : "0");
                exchange_aux.setAmountExchange(edtAmount_text);

                if (edtAmount_text.length() == 0 || edtAmount_text.equals("")) {


                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.amount_info_invalid));
                } else {
                    entrar(edtAmount_text);
                }

            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(ExchangeStep1Activity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });
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

    protected ObjTransferMoney[] getListProduct(SoapObject response) {

        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];

        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString(),obj.getProperty("symbol").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }

    public void entrar(String amountExchange) {

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new ProcessPreviewExange(amountExchange);
        mAuthTask.execute((Void) null);

    }
    public class ProcessPreviewExange extends AsyncTask<Void, Void, Boolean> {


        private String amountExchange;

        public ProcessPreviewExange(String amountExchange) {
            this.amountExchange = amountExchange;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();

            try {

                boolean availableBalance = true;
                String responseCode;
                String responseMessage = "";
/*@WebParam(name = "emailUser") String emailUser,
        @WebParam(name = "productSourceId") Long productSourceId,
        @WebParam(name = "productDestinationId") Long productDestinationId,
        @WebParam(name = "amountExchange") Float amountExchange,
        @WebParam(name = "includedAmount") int includedAmount) {*/

                if (availableBalance) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("emailUser", Session.getEmail());
                    map.put("productSourceId", exchange_aux.getExange_productSource().getId());
                    map.put("productDestinationId",exchange_aux.getExange_productDestination().getId());
                    map.put("amountExchange", amountExchange);
                    map.put("includedAmount", exchange_aux.getExange_includedAmount());


                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PREVIEW_EXCHANGE, Constants.ALODIGA);
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
                /*<amountCommission>0.25</amountCommission>
                <valueCommission>2.5</valueCommission>
                <totalDebit>9.75</totalDebit>
                <amountConversion>20.0</amountConversion>
                <exchangeRateProductSource>1.0</exchangeRateProductSource>
                <exchangeRateProductDestination>0.5</exchangeRateProductDestination>*/
                exchange_aux.setExange_amountCommission(response.getProperty("amountCommission").toString());
                exchange_aux.setExange_valueCommission(response.getProperty("valueCommission").toString());
                exchange_aux.setExange_totalDebit(response.getProperty("totalDebit").toString());
                exchange_aux.setExange_amountConversion(response.getProperty("amountConversion").toString());
                exchange_aux.setExange_exchangeRateProductSource(response.getProperty("exchangeRateProductSource").toString());
                exchange_aux.setExange_exchangeRateProductDestination(response.getProperty("exchangeRateProductDestination").toString());
                exchange_aux.setExange_isPercentCommision(response.getProperty("isPercentCommision").toString());
                Session.setExchange(exchange_aux);


                Intent i = new Intent(ExchangeStep1Activity.this, ExchangeStep2Activity.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ExchangeStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
