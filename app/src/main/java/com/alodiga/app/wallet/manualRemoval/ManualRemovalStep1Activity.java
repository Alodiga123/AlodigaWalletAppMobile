package com.alodiga.app.wallet.manualRemoval;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterAccountBank;
import com.alodiga.app.wallet.adapters.SpinAdapterBank;
import com.alodiga.app.wallet.adapters.SpinAdapterPais;
import com.alodiga.app.wallet.adapters.SpinAdapterProduct;
import com.alodiga.app.wallet.bank.AddBankActivity;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.manualRecharge.ManualRechargeStep1Activity;
import com.alodiga.app.wallet.model.ObjAccountBank;
import com.alodiga.app.wallet.model.ObjAccountBankComplex;
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ManualRemovalStep1Activity extends AppCompatActivity {
    static SoapObject response;

    static ObjTransferMoney[] listSpinner_producto = new ObjTransferMoney[0];
    static ObjAccountBankComplex[] listSpinner_accountBank = new ObjAccountBankComplex[0];
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static FragmentManager fragmentManager;
    private static String stringResponse = "";
    String datosRespuesta = "";
    UserRemovalTask mAuthTask;
    ObjAccountBankComplex getbankAccount;
    ObjTransferMoney getproduct;
    String getaccountBank, getDescrip, getAmountRecharge;
    private LinearLayout infoAccountLayout;
    Spinner  spinnerAccountBank, spinnerproducto;
    private EditText edtAmount, edtdescript;
    private TextView accountNumberLabel,accountNumberLabelValue,accountDescriptionLabelValue,accountDescriptionLabel,accountAbaCode,accountAbaCodeValue;
    private Button signFind, backToLoginBtn;
    private String responsetxt = "";
    private boolean serviceStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_removal_layout);
        spinnerAccountBank = findViewById(R.id.spinnerAccountBank);
        spinnerproducto = findViewById(R.id.spinnerproducto);
        edtAmount = findViewById(R.id.edtAmount);
        signFind = findViewById(R.id.signFind);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        edtdescript = findViewById(R.id.edtdescript);
        infoAccountLayout = findViewById(R.id.infoAccountLayout);
        accountNumberLabelValue = findViewById(R.id.accountNumberLabelValue);
        accountDescriptionLabelValue = findViewById(R.id.accountDescriptionLabelValue);
        accountAbaCodeValue = findViewById(R.id.accountAbaCodeValue);
        //Descomentar
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        //Spinner Cuenta Bancaria
        new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_ACCOUNT_BANK, Constants.ALODIGA);
                    stringResponse = response.toString();
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);
                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_accountBank = getListAccountBank(response);
                                SpinAdapterAccountBank spinAdapterAccountBank;
                                spinAdapterAccountBank = new SpinAdapterAccountBank(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_accountBank);
                                spinnerAccountBank.setAdapter(spinAdapterAccountBank);
                            }
                        });
                    } else {

                         if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NOT_ACCOUNT_BANK_ASOCIATE)) {

                             Bundle bundle = new Bundle();
                             bundle.putString("value", responsetxt);
                             startActivity(new Intent(getApplicationContext(), AddBankActivity.class).putExtras(bundle));
                             finish();
                         }

                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                responsetxt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        //Spinner Producto
        spinnerAccountBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                infoAccountLayout.setVisibility(View.VISIBLE);
                spinnerproducto.setEnabled(true);
                final ObjAccountBankComplex accountBank = (ObjAccountBankComplex) spinnerAccountBank.getSelectedItem();
                accountNumberLabelValue.setText(accountBank.getAccountNumber());
                accountDescriptionLabelValue.setText(accountBank.getDescription());
                accountAbaCodeValue.setText(accountBank.getAbaCode());
                //Toast.makeText(getApplicationContext(),"id" + bank.getId() ,Toast.LENGTH_SHORT).show();
              new Thread(new Runnable() {
                    public void run() {
                        try {
                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("bankId", accountBank.getBankId());
                            map.put("userId", Session.getUserId().trim());
                            response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PRODUCT, Constants.ALODIGA);
                            stringResponse = response.toString();
                            responseCode = response.getProperty("codigoRespuesta").toString();
                            datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                            serviceAnswer(responseCode);
                            if (serviceStatus) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listSpinner_producto = getListProduct(response);
                                        SpinAdapterProduct spinAdapterProduct;
                                        spinAdapterProduct = new SpinAdapterProduct(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_producto);
                                        spinnerproducto.setAdapter(spinAdapterProduct);
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


        signFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getmonto = edtAmount.getText().toString();
                String getdescrip = edtdescript.getText().toString();
                getproduct = (ObjTransferMoney) spinnerproducto.getSelectedItem();

                if (getmonto.equals("") || getmonto.length() == 0) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.amount_info_invalid));
                } else if (Float.parseFloat(getproduct.getCurrency().trim()) < Float.parseFloat(getmonto.trim())) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.insuficient_balance));
                } else if (getdescrip.equals("") || getdescrip.length() == 0) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.recharge_descrip_invalid));
                } else {
                    RemovalTask();
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
        Intent i = new Intent(ManualRemovalStep1Activity.this, MainActivity.class);
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


    protected ObjAccountBankComplex[] getListAccountBank(SoapObject response) {
        ObjAccountBankComplex[] obj2 = new ObjAccountBankComplex[response.getPropertyCount() - 3];
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            String id = obj.getProperty("id").toString();
            String accountNumber = obj.getProperty("accountNumber").toString();;
            String bankName = ((SoapObject)obj.getProperty("bankId")).getProperty("name").toString();
            String description = ((SoapObject) obj.getProperty("accountTypeBankId")).getProperty("description").toString();
            String abaCode = ((SoapObject) obj.getProperty("bankId")).getProperty("abaCode").toString();
            String bankId = ((SoapObject) obj.getProperty("bankId")).getProperty("id").toString();
            ObjAccountBankComplex object = new ObjAccountBankComplex(id, bankName,accountNumber,description,abaCode,bankId);
            obj2[i - 3] = object;
        }
        return obj2;
    }


    protected ObjTransferMoney[] getListProduct(SoapObject response) {

        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];

        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " " + obj.getProperty("symbol").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString());

            obj2[i - 3] = object;
        }

        return obj2;
    }

    public void RemovalTask() {
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new UserRemovalTask();
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

        }  else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA)) {
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
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NOT_ACCOUNT_BANK_ASOCIATE)) {
            responsetxt = getString(R.string.web_services_response_304);
            serviceStatus = false;
//////////////////////////////7transaccional Adicional change (//////////////////////////////////////////////////////////////////////////
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_TRANSACTION_AMOUNT_LIMIT_DIALY)) {
            responsetxt = getString(R.string.web_services_response_34);
            serviceStatus = false;
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_TRANSACTION_QUANTITY_LIMIT_DIALY)) {
            responsetxt = getString(R.string.web_services_response_37);
            serviceStatus = false;
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_TRANSACTION_AMOUNT_LIMIT_MONTHLY)) {
            serviceStatus = false;
            responsetxt = getString(R.string.web_services_response_35);
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_TRANSACTION_AMOUNT_LIMIT_YEARLY)) {
            responsetxt = getString(R.string.web_services_response_36);
            serviceStatus = false;
        }  else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_TRANSACTION_QUANTITY_LIMIT_MONTHLY)) {
            responsetxt = getString(R.string.web_services_response_38);
            serviceStatus = false;
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_TRANSACTION_QUANTITY_LIMIT_YEARLY)) {
            responsetxt = getString(R.string.web_services_response_39);
            serviceStatus = false;
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_DISABLED_TRANSACTION)) {
                responsetxt = getString(R.string.web_services_response_41);
                serviceStatus = false;
//////////////////////////////7transaccional(//////////////////////////////////////////////////////////////////////////

            } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_DISABLED_TRANSACTION)) {

            serviceStatus = false;










        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
            responsetxt = getString(R.string.web_services_response_99);
            serviceStatus = false;
        }

    }

    public class UserRemovalTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;

            getbankAccount = (ObjAccountBankComplex) spinnerAccountBank.getSelectedItem();
            getproduct = (ObjTransferMoney) spinnerproducto.getSelectedItem();

            getDescrip = edtdescript.getText().toString();
            getAmountRecharge = edtAmount.getText().toString();

            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("bankId", getbankAccount.getId());
                map.put("emailUser", Session.getEmail());
                map.put("accountBank", getaccountBank);
                map.put("amountWithdrawal", getAmountRecharge);
                map.put("productId", getproduct.getId());
                map.put("conceptTransaction", getDescrip);
                map.put("documentTypeId", "8");
                map.put("originApplicationId", "1");
                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_REMOVAL_MANUAL, Constants.ALODIGA);
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
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);
            if (success) {
                Intent show;
                show = new Intent(getApplicationContext(), ManualRemovalStep2WelcomeActivity.class);
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
            mAuthTask = null;
        }
    }

}





