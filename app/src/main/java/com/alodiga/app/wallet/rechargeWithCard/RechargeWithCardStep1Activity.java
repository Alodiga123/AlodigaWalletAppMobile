package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterCardType;
import com.alodiga.app.wallet.adapters.SpinAdapterGeneric;
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoneyRemittence;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjCreditCardTypeId;
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.model.ObjPaymentInfo;
import com.alodiga.app.wallet.model.ObjTarjetahabiente;
import com.alodiga.app.wallet.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    static ObjCreditCardTypeId[] listSpinner_Type;
    LinearLayout linearLayout1;
    EditText card, name, ccv;
    static Spinner spinnerType;
    static String getCard, getName, getCcv;
    static ObjGenericObject getMonth,getYear;
    static ObjCreditCardTypeId getSpinnerType;
    CheckBox checkBox;
    getTypeCards mAuthTask;
    int monthAct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_with_card);

        Session.setIsConstantsEmpty(true);

        //linearLayout1= findViewById(R.id.linearLayout1);

        signFind = findViewById(R.id.signFind);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);
        card = findViewById(R.id.card);
        name = findViewById(R.id.name);
        ccv = findViewById(R.id.ccv);

        checkBox= findViewById(R.id.checkBox);

        month= findViewById(R.id.month);
        year= findViewById(R.id.year);
        spinnerType= findViewById(R.id.spinnerType);

        Calendar calendar = Calendar.getInstance();
        monthAct= calendar.get(Calendar.MONTH);
        cargar();

       /* listSpinner_Type = new ObjGenericObject[6];
        listSpinner_Type[0]= new ObjGenericObject("VISA","0");
        listSpinner_Type[1]= new ObjGenericObject("MASTERCARD","1");
        listSpinner_Type[2]= new ObjGenericObject("DINNERS","2");
        listSpinner_Type[3]= new ObjGenericObject("DISCOVER","3");
        listSpinner_Type[4]= new ObjGenericObject("JCB","4");
        listSpinner_Type[5]= new ObjGenericObject("AMEX","5");*/


      /*  new Thread(new Runnable() {
            public void run() {
                try {
                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
                    map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);


                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GET_CREDIT_CARD_TYPE, Constants.ALODIGA);
                    stringResponse = response.toString();
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    datosRespuesta = response.getProperty("mensajeRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                              /*  if(Session.getIsTarjetahabienteSelect()){


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

                                }*/





/*
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
        }).start();*/






        /*if(Session.getIsTarjetahabienteSelect()){



            ccv.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardCVV());
            name.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
            card.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumber());
            //spinnerType.

            monthAct= Session.getTarjetahabienteSelect().getCardInfo().getCreditCardDate().getMonth() -1;
            listSpinner_years = getListYears(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardDate().getYear());

            SpinAdapterGeneric spinAdapterType;
            spinAdapterType = new SpinAdapterGeneric(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_Type);
            spinnerType.setAdapter(spinAdapterType);
            spinnerType.setSelection(getListType(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardTypeId().getName()));
        }else{
            listSpinner_years = getListYears();

            SpinAdapterGeneric spinAdapterType;
            spinAdapterType = new SpinAdapterGeneric(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_Type);
            spinnerType.setAdapter(spinAdapterType);

        }*/


        listSpinner_years = getListYears();


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
                //Session.setIsTarjetahabienteSelect(false);
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
                finish();
            }
        });




        signFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    entrar();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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


    private void entrar() throws ParseException {

        final String CVV_DIG = "\\d+";
        getCard= card.getText().toString();
        getName= name.getText().toString();
        getCcv= ccv.getText().toString();


        //ObjGenericObject getMonth= (ObjGenericObject) month.getSelectedItem();
        //ObjGenericObject getYear = (ObjGenericObject) year.getSelectedItem();
        getSpinnerType = (ObjCreditCardTypeId) spinnerType.getSelectedItem();


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
        }else if(getCcv.length()!=Integer.parseInt(getSpinnerType.getLenghCVV()) ){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.cvv_information));
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
            //Session.setIsTarjetahabienteSelect(false);
            getMonth= (ObjGenericObject) month.getSelectedItem();
            getYear = (ObjGenericObject) year.getSelectedItem();


            ObjTarjetahabiente tarjetahabiente = new ObjTarjetahabiente();
            ObjPaymentInfo card= new ObjPaymentInfo();
            card.setCreditCardNumber(getCard);
            card.setCreditCardName(getName);
            card.setCreditCardCVV(getCcv);
            card.setMonth(getMonth.getName());
            card.setYear(getYear.getName());

            ObjCreditCardTypeId cardType = new ObjCreditCardTypeId();
            cardType.setName(getSpinnerType.getName());
            cardType.setId(getSpinnerType.getId());
            card.setCreditCardTypeId(cardType);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Date ccdate = format.parse(getYear.getName() + "-" + getMonth.getName());
            card.setCreditCardDate(ccdate);

            String fecha= card.getCreditCardDate().toString();

            tarjetahabiente.setCardInfo(card);

            tarjetahabiente.setSave(checkBox.isChecked());

           /* ObjTarjetahabiente tarjetahabiente= new ObjTarjetahabiente();
            tarjetahabiente.setCard_number(getCard);
            tarjetahabiente.setCardholder_name(getName);
            tarjetahabiente.setSecurity_code(getCcv);
            tarjetahabiente.setType_card(getSpinnerType.getName());
            tarjetahabiente.setExpiration_date_moth(getMonth.getName());
            tarjetahabiente.setExpiration_date_year(getYear.getName());
            tarjetahabiente.setSave(checkBox.isChecked());*/

            Session.setTarjetahabienteSelect(tarjetahabiente);


            Intent show;
            show = new Intent(getApplicationContext(), RechargeWithCardStep2Activity.class);
            startActivity(show);
            finish();
        }
    }

    String obtenerFechaFormateada(Date fecha, String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(fecha);
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
        //Session.setIsTarjetahabienteSelect(false);
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


    public void cargar(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new getTypeCards();
        mAuthTask.execute((Void) null);
    }


    public class getTypeCards extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();

            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GET_CREDIT_CARD_TYPE, Constants.ALODIGA);
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

                listSpinner_Type= getListCardType(response);

                    SpinAdapterCardType spinAdapterType;
                    spinAdapterType = new SpinAdapterCardType(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_Type);
                    spinnerType.setAdapter(spinAdapterType);

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


    protected ObjCreditCardTypeId[] getListCardType(SoapObject response) {
        ObjCreditCardTypeId[] obj2 = new ObjCreditCardTypeId[response.getPropertyCount() - 3];
        //ObjTransferMoney[] obj2 = new ObjTransferMoney[3];

        int aux= 0;
        for (int i = 3; i < response.getPropertyCount(); i++) {
            //for (int i = 0; i < 3; i++) {

            SoapObject obj = (SoapObject) response.getProperty(i);
            ObjCreditCardTypeId object = new ObjCreditCardTypeId(obj.getProperty("enabled").toString(), obj.getProperty("id").toString(),obj.getProperty("lengh").toString(), obj.getProperty("name").toString());
            //ObjTransferMoney object = new ObjTransferMoney("id-"+i, "name-2000-"+i, "2000");
            obj2[aux] = object;
            aux++;
        }

        return obj2;
    }
}