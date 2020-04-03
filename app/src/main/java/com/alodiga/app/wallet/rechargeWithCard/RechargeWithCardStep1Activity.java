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
import com.alodiga.app.wallet.adapters.SpinAdapterGeneric;
import com.alodiga.app.wallet.main.MainActivity;
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


public class RechargeWithCardStep1Activity extends AppCompatActivity {
    static SoapObject response;
    static ObjGenericObject[] listSpinner_pais = new ObjGenericObject[0];
    static ObjTransferMoney[] listSpinner_producto = new ObjTransferMoney[0];
    static ObjGenericObject[] listSpinner_banco = new ObjGenericObject[0];
    static ProgressDialogAlodiga progressDialogAlodiga;
    static int indexYears;
    static int indexType;
    private static FragmentManager fragmentManager;
    private static String stringResponse = "";
    String datosRespuesta = "";
    UserRemovalTask mAuthTask;
    ObjGenericObject getbank;
    ObjTransferMoney getproduct;
    String getNumberOperation, getTrans, getAmountRecharge;
    private Button signFind, backToLoginBtn;
    private String responsetxt = "";
    private boolean serviceStatus;
    static DatePicker DatePicker;
    Spinner month, year;
    static ObjGenericObject[] listSpinner_years;
    static ObjGenericObject[] listSpinner_moth;
    static ObjGenericObject[] listSpinner_Type;
    LinearLayout linearLayout1;
    EditText card, name, ccv, edtAmount;
    static Spinner spinnerType,  spinnerproducto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_with_card);




        linearLayout1= findViewById(R.id.linearLayout1);

        edtAmount = findViewById(R.id.edtAmount);
        signFind = findViewById(R.id.signFind);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);
        card = findViewById(R.id.card);
        name = findViewById(R.id.name);
        ccv = findViewById(R.id.ccv);

        month= findViewById(R.id.month);
        year= findViewById(R.id.year);
        spinnerType= findViewById(R.id.spinnerType);

        Calendar calendar = Calendar.getInstance();
        int monthAct= calendar.get(Calendar.MONTH);

        if(Session.getIsTarjetahabienteSelect()){
            ccv.setText(Session.getTarjetahabienteSelect().getSecurity_code());
            name.setText(Session.getTarjetahabienteSelect().getCardholder_name());
            card.setText(Session.getTarjetahabienteSelect().getCard_number());
            //spinnerType.

            monthAct= Integer.parseInt(Session.getTarjetahabienteSelect().getExpiration_date_moth()) -1;
            listSpinner_years = getListYears(Integer.parseInt(Session.getTarjetahabienteSelect().getExpiration_date_year()));

          ;

            SpinAdapterGeneric spinAdapterType;
            spinAdapterType = new SpinAdapterGeneric(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_Type);
            spinnerType.setAdapter(spinAdapterType);
            spinnerType.setSelection( getListType(Session.getTarjetahabienteSelect().getType_card()));


        }else{
            listSpinner_years = getListYears();


            listSpinner_Type = new ObjGenericObject[3];
            for (int i=0;i<3;i++){
                listSpinner_Type[i]= new ObjGenericObject("mastercar"+"-"+i,String.valueOf(i));
            }

            SpinAdapterGeneric spinAdapterType;
            spinAdapterType = new SpinAdapterGeneric(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_Type);
            spinnerType.setAdapter(spinAdapterType);

        }


        listSpinner_moth = getListMonth();
        SpinAdapterGeneric spinAdapterMoth;
        spinAdapterMoth = new SpinAdapterGeneric(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_moth);
        month.setAdapter(spinAdapterMoth);
        month.setSelection(monthAct);

        SpinAdapterGeneric spinAdapterYears;
        spinAdapterYears = new SpinAdapterGeneric(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_years);
        year.setAdapter(spinAdapterYears);
        year.setSelection(indexYears);










        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Session.setIsTarjetahabienteSelect(false);
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
                finish();
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
                Session.setIsTarjetahabienteSelect(false);
                Intent show;
                show = new Intent(getApplicationContext(), RechargeWithCardStep2Activity.class);
                startActivity(show);
                finish();


            }
        });

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), RechargeWhithCardContactsActivity.class);
                startActivity(show);
                finish();
            }
        });
    }

    private ObjGenericObject[] getListYears() {
        Calendar calendar = Calendar.getInstance();


        int yearAct= calendar.get(Calendar.YEAR);
        int yearIni= yearAct-Constants.YEARMINUS;
        int yearFin = yearAct+Constants.YEARPLUS;
        int total= (yearFin- yearIni);
        ObjGenericObject[] ListYear = new ObjGenericObject[total+1];
        int aux=0;

        for(int i = yearIni; i <= yearFin; i++)
        {
           ListYear[aux]= new ObjGenericObject(String.valueOf(i),String.valueOf(aux));
           if(yearAct==i){
            indexYears=aux;
           }

           aux++;
        }

        return ListYear;
    }

    private ObjGenericObject[] getListYears(int year) {
        Calendar calendar = Calendar.getInstance();



        int yearAct= calendar.get(Calendar.YEAR);
        int yearIni= yearAct-Constants.YEARMINUS;
        int yearFin = yearAct+Constants.YEARPLUS;


        if(year>=yearFin){
            int plus = year - yearAct;
            yearFin= yearFin+Constants.YEARPLUS+plus;
        }

        if(year<=yearIni){
            int minus= yearIni-year;
            yearIni= yearIni-(minus+Constants.YEARMINUS);
        }

        int total= (yearFin- yearIni);
        ObjGenericObject[] ListYear = new ObjGenericObject[total+1];
        int aux=0;

        for(int i = yearIni; i <= yearFin; i++)
        {
            ListYear[aux]= new ObjGenericObject(String.valueOf(i),String.valueOf(aux));
            if(year==i){
                indexYears=aux;
            }

            aux++;
        }
        return ListYear;
    }

    private int getListType(String type) {

        for(int i = 0; i < listSpinner_Type.length; i++)
        {

            if(listSpinner_Type[i].getName().equals(type)){
                indexType=i;
            }

        }
        return indexType;
    }


    private ObjGenericObject[] getListMonth() {
        Calendar calendar = Calendar.getInstance();
        ObjGenericObject[] ListMoth = new ObjGenericObject[12];
        int aux=0;

        for(int i = 1; i <= 12; i++)
        {
            ListMoth[aux]= new ObjGenericObject(String.valueOf(i),String.valueOf(aux));
            aux++;
        }

        return ListMoth;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Session.setIsTarjetahabienteSelect(false);
        Intent pasIntent = getIntent();
        Intent i = new Intent(RechargeWithCardStep1Activity.this, MainActivity.class);
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

    public class UserRemovalTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;

           /* getbank = (ObjGenericObject) spinnerbank.getSelectedItem();
            getNumberOperation = edtCOD.getText().toString();
            getAmountRecharge = edtAmount.getText().toString();
            getproduct = (ObjTransferMoney) spinnerproducto.getSelectedItem();
            getTrans = edttrans.getText().toString();*/


            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("bankId", getbank.getId());
                map.put("emailUser", Session.getEmail());
                map.put("referenceNumberOperation", getNumberOperation);
                map.put("amountRecharge", getAmountRecharge);
                map.put("productId", getproduct.getId());
                map.put("conceptTransaction", getTrans);

                /*
                 @WebParam(name = "bankId") Long bankId,
                @WebParam(name = "emailUser") String emailUser,
                @WebParam(name = "referenceNumberOperation") String referenceNumberOperation,
                @WebParam(name = "amountRecharge") Float amountRecharge,
                @WebParam(name = "productId") Long productId,
                @WebParam(name = "conceptTransaction") String conceptTransaction) {
                return operations.ManualRecharge(bankId, emailUser, referenceNumberOperation, amountRecharge,
                 */

                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_RECHARGE, Constants.ALODIGA);
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
            mAuthTask = null;
            //showProgress(false);
            if (success) {
                Intent show;
                show = new Intent(getApplicationContext(), RechargeWithCardStep.class);
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