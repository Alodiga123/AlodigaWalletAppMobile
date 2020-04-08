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
import com.alodiga.app.wallet.adapters.SpinAdapterGeneric;
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoneyRemittence;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.model.ObjTarjetahabiente;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RechargeWithCardStep1Activity extends AppCompatActivity {
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
    private Button signFind, backToLoginBtn;
    private String responsetxt = "";
    private boolean serviceStatus;
    static Spinner month, year;
    static ObjGenericObject[] listSpinner_years;
    static ObjGenericObject[] listSpinner_moth;
    static ObjGenericObject[] listSpinner_Type;
    LinearLayout linearLayout1;
    EditText card, name, ccv;
    static Spinner spinnerType;
    static String getCard, getName, getCcv;
    static ObjGenericObject getSpinnerType,getMonth,getYear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_with_card);

        //linearLayout1= findViewById(R.id.linearLayout1);

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

        listSpinner_Type = new ObjGenericObject[6];
        listSpinner_Type[0]= new ObjGenericObject("VISA","0");
        listSpinner_Type[1]= new ObjGenericObject("MASTERCARD","1");
        listSpinner_Type[2]= new ObjGenericObject("DINNERS","2");
        listSpinner_Type[3]= new ObjGenericObject("DISCOVER","3");
        listSpinner_Type[4]= new ObjGenericObject("JCB","4");
        listSpinner_Type[5]= new ObjGenericObject("AMEX","5");




        if(Session.getIsTarjetahabienteSelect()){



            ccv.setText(Session.getTarjetahabienteSelect().getSecurity_code());
            name.setText(Session.getTarjetahabienteSelect().getCardholder_name());
            card.setText(Session.getTarjetahabienteSelect().getCard_number());
            //spinnerType.

            monthAct= Integer.parseInt(Session.getTarjetahabienteSelect().getExpiration_date_moth()) -1;
            listSpinner_years = getListYears(Integer.parseInt(Session.getTarjetahabienteSelect().getExpiration_date_year()));

            SpinAdapterGeneric spinAdapterType;
            spinAdapterType = new SpinAdapterGeneric(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_Type);
            spinnerType.setAdapter(spinAdapterType);
            spinnerType.setSelection(getListType(Session.getTarjetahabienteSelect().getType_card()));
        }else{
            listSpinner_years = getListYears();

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




        signFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                entrar();

            }
        });

       /* linearLayout1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), RechargeWhithCardContactsActivity.class);
                startActivity(show);
                finish();
            }
        });*/
    }


    private void entrar(){

        final String CVV_DIG = "\\d+";
        getCard= card.getText().toString();
        getName= name.getText().toString();
        getCcv= ccv.getText().toString();


        //ObjGenericObject getMonth= (ObjGenericObject) month.getSelectedItem();
        //ObjGenericObject getYear = (ObjGenericObject) year.getSelectedItem();
        getSpinnerType = (ObjGenericObject) spinnerType.getSelectedItem();


        Pattern digi_cvv = Pattern.compile(CVV_DIG);
        Matcher cvv_dig = digi_cvv.matcher(getCcv);
        Matcher cvv_dig_card = digi_cvv.matcher(getCard);

        if (getCard.equals("") || getCard.length() == 0 ||
                getName.equals("") || getName.length() == 0 ||
                getCcv.equals("") || getCcv.length() == 0 ) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.invalid_all_question));
        }else if(!cvv_dig.lookingAt())
        {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.cvv_numeric));
        }else if(getCcv == null){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.cvv_null));
        }if(getCcv.length() < Constants.LONGITUD_MINIMA_CVV || getCcv.length() > Constants.LONGITUD_MAXIMA_CVV){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.cvv_information));
        }else if(!getSpinnerType.getName().equals("AMEX") &&  getCcv.length() == Constants.LONGITUD_MAXIMA_CVV ) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.cvv_invalic_type));
        }else if(getSpinnerType.getName().equals("AMEX") &&  getCcv.length() == Constants.LONGITUD_MINIMA_CVV ){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.cvv_invalic_type));
        } else if(getCard == null){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.card_invalic));
        } else if(card.length() < Constants.LONGITUD_MINIMA_TARJETA_CREDITO || card.length() > Constants.LONGITUD_MAXIMA_TARJETA_CREDITO){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.card_invalic2));
        }else if(!cvv_dig_card.lookingAt())
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.card_numeric));
        else{
            Session.setIsTarjetahabienteSelect(false);
            getMonth= (ObjGenericObject) month.getSelectedItem();
            getYear = (ObjGenericObject) year.getSelectedItem();

            ObjTarjetahabiente tarjetahabiente= new ObjTarjetahabiente();
            tarjetahabiente.setCard_number(getCard);
            tarjetahabiente.setCardholder_name(getName);
            tarjetahabiente.setSecurity_code(getCcv);
            tarjetahabiente.setType_card(getSpinnerType.getName());
            tarjetahabiente.setExpiration_date_moth(getMonth.getName());
            tarjetahabiente.setExpiration_date_year(getYear.getName());

            Session.setTarjetahabienteSelect(tarjetahabiente);

            Intent show;
            show = new Intent(getApplicationContext(), RechargeWithCardStep2Activity.class);
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