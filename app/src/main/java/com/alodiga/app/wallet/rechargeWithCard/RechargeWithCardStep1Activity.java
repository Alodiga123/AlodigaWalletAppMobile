package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterCardType;
import com.alodiga.app.wallet.adapters.SpinAdapterGeneric;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.duallibrary.model.ObjCreditCardTypeId;
import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.model.ObjPaymentInfo;
import com.alodiga.app.wallet.duallibrary.model.ObjTarjetahabiente;
import com.alodiga.app.wallet.duallibrary.model.ObjTransferMoney;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RechargeWithCardStep1Activity extends AppCompatActivity {
    static SoapObject response;
    static ProgressDialogAlodiga progressDialogAlodiga;
    static int indexYears;
    static int indexType;
    private Button signFind, backToLoginBtn;
    private String responsetxt = "";
    private boolean serviceStatus;
    static Spinner month, year;
    static ObjGenericObject[] listSpinner_years;
    static ObjGenericObject[] listSpinner_moth;
    static ObjCreditCardTypeId[] listSpinner_Type;
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
    }


    private void entrar() {

        final String CVV_DIG = "\\d+";
        getCard= card.getText().toString();
        getName= name.getText().toString();
        getCcv= ccv.getText().toString();

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
            getMonth= (ObjGenericObject) month.getSelectedItem();
            getYear = (ObjGenericObject) year.getSelectedItem();

            ObjTarjetahabiente tarjetahabiente = new ObjTarjetahabiente();
            ObjPaymentInfo card= new ObjPaymentInfo();
            card.setCreditCardNumber(getCard);
            card.setCreditCardNumberEnmas(card.getCreditCardNumber().substring(0,4) + "********" + card.getCreditCardNumber().substring(card.getCreditCardNumber().length()-4));
            card.setCreditCardName(getName);
            card.setCreditCardCVV(getCcv);
            card.setMonth(getMonth.getName());
            card.setYear(getYear.getName());

            ObjCreditCardTypeId cardType = new ObjCreditCardTypeId();
            cardType.setName(getSpinnerType.getName());
            cardType.setId(getSpinnerType.getId());
            card.setCreditCardTypeId(cardType);

            tarjetahabiente.setCardInfo(card);

            tarjetahabiente.setSave(checkBox.isChecked());

            int resID = getResources().getIdentifier(card.getCreditCardTypeId().getName().replace(" ","_").toLowerCase() , "drawable", getPackageName());
            card.setImageCard(resID);

            Session.setTarjetahabienteSelect(tarjetahabiente);

            Intent show;
            show = new Intent(getApplicationContext(), RechargeWithCardStep2Activity.class);
            startActivity(show);
            finish();
        }
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


    private ObjGenericObject[] getListMonth() {
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
        Intent i = new Intent(RechargeWithCardStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();
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

        int aux= 0;
        for (int i = 3; i < response.getPropertyCount(); i++) {

            SoapObject obj = (SoapObject) response.getProperty(i);
            ObjCreditCardTypeId object = new ObjCreditCardTypeId(obj.getProperty("enabled").toString(), obj.getProperty("id").toString(),obj.getProperty("lengh").toString(), obj.getProperty("name").toString());
            obj2[aux] = object;
            aux++;
        }

        return obj2;
    }
}