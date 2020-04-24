package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterPais;
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoneyRemittence;
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.HashMap;


public class RechargeWithCardStep2Activity extends AppCompatActivity {
    static SoapObject response;
    static ObjTransferMoney[] listSpinner_producto = new ObjTransferMoney[0];
    static ProgressDialogAlodiga progressDialogAlodiga;
    static int indexYears;
    static int indexType;
    private static FragmentManager fragmentManager;
    private static String stringResponse = "";
    String datosRespuesta = "";
    ObjGenericObject getbank;
    ObjTransferMoney getproduct;
    String getNumberOperation, getTrans, getAmountRecharge;
    private Button signFind, backToLoginBtn;
    private String responsetxt = "";
    private boolean serviceStatus;
    static DatePicker DatePicker;
    static Spinner month, year;
    static ObjGenericObject[] listSpinner_years;
    static ObjGenericObject[] listSpinner_moth;
    static ObjGenericObject[] listSpinner_Type;
    LinearLayout linearLayout1;
    EditText card, name, ccv, edtAmount;
    static Spinner spinnerType,  spinnerproducto;
    static String getEdtAmount;
    static ObjTransferMoney getSpinnerproducto;
    private SoapObject response_;
    getproducts mAuthTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_with_card_2);

        spinnerproducto= findViewById(R.id.spinnerproducto);

        edtAmount = findViewById(R.id.edtAmount);
        signFind = findViewById(R.id.signFind);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);

        cargar();


        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Session.getIsConstantsEmpty()){

                    Intent show;
                    show = new Intent(getApplicationContext(), RechargeWithCardStep1Activity.class);
                    startActivity(show);
                    finish();


                }else{

                    Intent show;
                    show = new Intent(getApplicationContext(),RechargeWhithCardContactsActivity.class);
                    startActivity(show);
                    finish();

                }

            }
        });


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
                entrar();

            }
        });

    }


    private void entrar(){

        getEdtAmount= edtAmount.getText().toString();

        getSpinnerproducto = (ObjTransferMoney) spinnerproducto.getSelectedItem();

      if (getEdtAmount.equals("") || getEdtAmount.length() == 0) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.invalid_all_question));
        }else if(Float.valueOf(getEdtAmount) == 0 ){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.web_services_response_134));
        /*}else if (Float.valueOf(getEdtAmount) > Float.valueOf(getSpinnerproducto.getCurrency() )){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.web_services_response_33));*/
        } else{

            Session.getTarjetahabienteSelect().setAmount(getEdtAmount);
            Session.getTarjetahabienteSelect().setProduct(getSpinnerproducto);

            Intent show;
            show = new Intent(getApplicationContext(), RechargeWithCardStep3CodeActivity.class);
            startActivity(show);
            finish();
        }
    }


    protected ObjTransferMoney[] getListProduct1(SoapObject response) {
        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];
        int aux= 0;
        for (int i = 3; i < response.getPropertyCount(); i++) {
                SoapObject obj = (SoapObject) response.getProperty(i);
                ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString());
            obj2[aux] = object;
            aux++;
        }

        return obj2;
    }
   

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (Session.getIsConstantsEmpty()){
            Intent show;
            show = new Intent(getApplicationContext(), RechargeWithCardStep1Activity.class);
            startActivity(show);
            finish();
        }else{
            Intent show;
            show = new Intent(getApplicationContext(),RechargeWhithCardContactsActivity.class);
            startActivity(show);
            finish();
        }

    }

    public void cargar(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new getproducts();
        mAuthTask.execute((Void) null);
    }


    public class getproducts extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();

            try {
                String responseCode;

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUserId());
                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETPRODUCTS_RECHARGE_PAYMENT_BY_USERID, Constants.ALODIGA);
                responseCode = response.getProperty("codigoRespuesta").toString();


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
                }  else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_CARD)) {
                    responsetxt = getString(R.string.web_services_response_29);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DOES_NOT_HAVE_AN_ASSOCIATED_COMPANION_CARD)) {
                    responsetxt = getString(R.string.web_services_response_30_);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CARD_NUMBER_EXISTS)) {
                    responsetxt = getString(R.string.web_services_response_50);
                    serviceStatus = true;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NOT_ALLOWED_TO_CHANGE_STATE)) {
                    responsetxt = getString(R.string.web_services_response_51);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_AUTHENTICALLY_IMPOSSIBLE)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_UNABLE_TO_ACCESS_DATA)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THERE_ARE_NO_RECORDS_FOR_THE_REQUESTED_SEARCH)) {
                    responsetxt = getString(R.string.web_services_response_58);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_NUMBER_OF_ORDERS_ALLOWED_IS_EXCEEDED)) {
                    responsetxt = getString(R.string.web_services_response_60);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO)) {
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
            mAuthTask = null;
            //showProgress(false);
            if (success) {

                listSpinner_producto = getListProduct1(response);
                SpinAdapterTransferMoneyRemittence spinAdapterProduct;
                spinAdapterProduct = new SpinAdapterTransferMoneyRemittence(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_producto);
                spinnerproducto.setAdapter(spinAdapterProduct);

                progressDialogAlodiga.dismiss();

            } else {

                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
                Intent i = new Intent(getApplication(), RechargeWhithCardContactsActivity.class);
                startActivity(i);
                finish();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}