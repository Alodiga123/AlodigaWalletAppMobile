package com.alodiga.app.wallet.rechargeMyCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterHowToTransfer;
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoney;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjHowToTranssfer;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.transference.TransferenceStep1Activity;
import com.alodiga.app.wallet.transference.TransferenceStep2QrActivity;
import com.alodiga.app.wallet.transference.TransferenceStep3Activity;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class RechargeMyCardStep1Activity extends AppCompatActivity {

    private Spinner spinnerIdentificationRecharge;
    private EditText txtAmountValue;
    private View viewQ;
    private Button signFind, backToLoginBtn;
    private String responsetxt = "";
    private boolean serviceStatus;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private RechargeMyCardStep1Activity.FindUserTask mAuthTask = null;
    private Integer caseFind = 0;
    private String destinationAccountNumber = "";
    private String destinationPhoneValue = "";
    private String destinationLastNameValue = "";
    private String destinationNameValue = "";
    private String destinationIdValue = "";
    private ObjUserHasProduct currencySelected;
    private ArrayList<ObjUserHasProduct> list_product;

    private static String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_my_card__es);

        spinnerIdentificationRecharge = findViewById(R.id.spinnerIdentificationRecharge);
        txtAmountValue = findViewById(R.id.txtAmountValueRecharge);

        signFind = findViewById(R.id.signFind);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);


        /*String[] optionsID = {"Alocoin","Saldo Alodiga", "HealthCareCoin"};
        String[] optionsBank = {" ","Provincial","Mercantil", "Banesco", "Bicentenario", "Banco de Venezuela", "Banco del Tesoro", "Banco Caroní","Banco Sofitasa", "Banpro", "Banco Fondo Común", "Banfoandes", "Banco Occidental de Descuento", "Banco Venezolano de Crédito", "Banco Exterior", "Banco Plaza", "Citibank", "Banplus"};
        String[] optionsTelephone = {" ","0416", "0426", "0412","0414", "0424"};*/

        list_product = Session.getObjUserHasProducts();
        final ObjTransferMoney[] objTransferMoney = new ObjTransferMoney[list_product.size()];
        for (int i = 0; i < list_product.size(); i++) {
            objTransferMoney[i] = new ObjTransferMoney(list_product.get(i).getId(), list_product.get(i).getName().trim() + " " + list_product.get(i).getSymbol().trim() + " - " + list_product.get(i).getCurrentBalance(), list_product.get(i).getCurrentBalance());
        }
        //Llena tipos de cuenta
        //List<ObjTransferMoney> countries = new ArrayList<ObjTransferMoney>();
        /*objTransferMoney[0] = new ObjTransferMoney("0","Saldo Alodiga USD "+ Session.getAlodigaBalance());
        objTransferMoney[1] = new ObjTransferMoney("1","Saldo Alocoins ALC "+ Session.getAlocoinsBalance());
        objTransferMoney[2] = new ObjTransferMoney("2","Saldo Tarjeta Prepagada CC "+Session.getHealthCareCoinsBalance());*/
        SpinAdapterTransferMoney spinAdapterTransferMoney;
        spinAdapterTransferMoney = new SpinAdapterTransferMoney(this.getApplicationContext(), android.R.layout.simple_spinner_item, objTransferMoney);
        spinnerIdentificationRecharge.setAdapter(spinAdapterTransferMoney);
        spinnerIdentificationRecharge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ObjTransferMoney objTransferMoney1;
                objTransferMoney1 = (ObjTransferMoney) spinnerIdentificationRecharge.getSelectedItem();
                currencySelected = new ObjUserHasProduct(objTransferMoney1.getId(), objTransferMoney1.getName(), objTransferMoney1.getCurrency());
                Session.setMoneySelected(currencySelected);
                /*if(objTransferMoney1.getId().equals("0")){
                    currencySelected = 0;
                }else if (objTransferMoney1.getId().equals("1")){
                    currencySelected = 1;
                }else if (objTransferMoney1.getId().equals("2")){
                    currencySelected = 2;
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        txtAmountValue.addTextChangedListener(new TextWatcher() {
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

                    txtAmountValue.setText(cashAmountBuilder.toString());
                    // keeps the cursor always to the right
                    Selection.setSelection(txtAmountValue.getText(), cashAmountBuilder.toString().length());

                }

            }
        });


        signFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Session.setGetDestinationAmount(txtAmountValue.getText().toString());
                Intent i = new Intent(RechargeMyCardStep1Activity.this, RechargeMyCardStep2codeActivity.class);
                startActivity(i);
                finish();
            }
        });





        progressDialogAlodiga = new ProgressDialogAlodiga(this, "Cargando..");

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RechargeMyCardStep1Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RechargeMyCardStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    // Check Validation Method


    public class FindUserTask extends AsyncTask<Void, Void, Boolean> {
        private final String phoneOrEmail;

        FindUserTask(String element) {
            phoneOrEmail = element;
        }


        @Override
        protected void onPreExecute() {
            progressDialogAlodiga.show();
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;
            try {
                String responseCode;
                String responseMessage = "";
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                String methodName = "";
                switch (caseFind) {

                    case 0:
                        map.put("email", phoneOrEmail);
                        methodName = "getUsuarioporemail";
                        break;

                    case 1:
                        map.put("movil", phoneOrEmail);
                        methodName = "getUsuariopormovil";
                        break;

                    default:

                        break;
                }
                response = WebService.invokeGetAutoConfigString(map, methodName, Constants.REGISTRO_UNIFICADO);
                responseCode = response.getProperty("codigoRespuesta").toString();
                //Activar las preguntas de seguridad

                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
                    String res = response.getProperty("datosRespuesta").toString();
                    destinationAccountNumber = getValueFromResponseJson("numeroCuenta", res);
                    destinationPhoneValue = getValueFromResponseJson("movil", res);
                    destinationLastNameValue = getValueFromResponseJson("apellido", res);
                    destinationNameValue = getValueFromResponseJson("nombre", res);
                    destinationIdValue = getValueFromResponseJson("UsuarioID", res);
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
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_PHONE_NUMBER)) {
                    responsetxt = getString(R.string.web_services_response_22);
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

            if (success) {

                //llama activities

                //llama activities
                Session.setMoneySelected(currencySelected);
                Session.setDestinationAccountNumber(destinationAccountNumber);
                Session.setDestinationLastNameValue(destinationLastNameValue);
                Session.setDestinationPhoneValue(destinationPhoneValue);
                Session.setDestinationNameValue(destinationNameValue);
                Session.setUsuarioDestionId(destinationIdValue);
                Intent i = new Intent(RechargeMyCardStep1Activity.this, TransferenceStep3Activity.class);
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


