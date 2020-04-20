package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
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
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoneyRemittence;
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_with_card_2);



        spinnerproducto= findViewById(R.id.spinnerproducto);

        edtAmount = findViewById(R.id.edtAmount);
        signFind = findViewById(R.id.signFind);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);


        listSpinner_producto = getListProduct1(response);
        SpinAdapterTransferMoneyRemittence spinAdapterProduct;
        spinAdapterProduct = new SpinAdapterTransferMoneyRemittence(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_producto);
        spinnerproducto.setAdapter(spinAdapterProduct);


        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Session.getIsTarjetahabienteSelect()){
                    Intent show;
                    show = new Intent(getApplicationContext(), RechargeWhithCardContactsActivity.class);
                    startActivity(show);
                    finish();
                }else{
                    Intent show;
                    show = new Intent(getApplicationContext(), RechargeWithCardStep1Activity.class);
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
        }else if (Float.valueOf(getEdtAmount) > Float.valueOf(getSpinnerproducto.getCurrency() )){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.web_services_response_33));
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
//        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];
        ObjTransferMoney[] obj2 = new ObjTransferMoney[3];

        //for (int i = 3; i < response.getPropertyCount(); i++) {
            for (int i = 0; i < 3; i++) {

                //SoapObject obj = (SoapObject) response.getProperty(i);
                //ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString());
                ObjTransferMoney object = new ObjTransferMoney("id-"+i, "name-2000-"+i, "2000");
            obj2[i] = object;
        }

        return obj2;
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
        /*Intent pasIntent = getIntent();
        Intent i = new Intent(RechargeWithCardStep2Activity.this, RechargeWithCardStep1Activity.class);
        startActivity(i);
        finish();*/

        if (Session.getIsTarjetahabienteSelect()){
            Intent show;
            show = new Intent(getApplicationContext(), RechargeWhithCardContactsActivity.class);
            startActivity(show);
            finish();
        }else{
            Intent show;
            show = new Intent(getApplicationContext(), RechargeWithCardStep1Activity.class);
            startActivity(show);
            finish();
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


}