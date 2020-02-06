package com.alodiga.app.wallet.activateDesativateCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.alodiga.app.R;
import com.alodiga.app.wallet.activeCard.ActiveCardStep2codeActivity;
import com.alodiga.app.wallet.activeCard.ActiveCardStep3Activity;
import com.alodiga.app.wallet.deactivateCard.DeactiveCardStep2codeActivity;
import com.alodiga.app.wallet.deactivateCard.DeactiveCardStep3Activity;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.kobjects.util.Util;
import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;


public class ActivateDeactivateCardStep1Activity extends AppCompatActivity {

    private static Spinner spinnerProduct;
    private Button step1_next_button, backToLoginBtn;
    private String responsetxt = "";
    private boolean serviceStatus;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private SoapObject response, response_, response_1;
    UserGetProcessDeActive mAuthTask;
    UserGetProcessActive mAuthTask_A;
    UserGetProcessStatus mAuthTask_S;
    boolean isChec = false;

    Switch simpleSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activecard_deactivate_step1_layout);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        simpleSwitch = findViewById(R.id.simpleSwitch);
        //Session.setCardSelectActiveDeactive("zzTQTPW8sjZ1rXOFtcBmIM7+exK1iSVr4sWp1Avyjh6HqBr1Jlr7pWktVpSQAxziAaLbCmZ3P0GuJgMSBFhrOf/KiQq1YEO2MLhXrhRBtEqgPvt/5TE2++K+Dr//OcjFCArBr+MmpadvpIh4qT4zhau87w5IsFDaZkpzVikB7uM=");

        Status();

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                if(isChec){
                    if(isChecked){
                        Activar();
                    }else{
                        Desactivar();
                    }
                }

            }
        });


        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
            }
        });

    }

   /*@Override
    protected void onStart() {

        super.onStart();
        Status();
        //isChec= true;
    }*/

   /* @Override
    protected void onResume() {
        super.onResume();
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                if(!isChec){
                    isChecked=true;
                }
               if(isChecked){
                    Activar();
                }else{
                    Desactivar();
                }
                //}else{

                //}
                //isChec= false;



            }
        });
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ActivateDeactivateCardStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void Status(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask_S = new UserGetProcessStatus();
        mAuthTask_S.execute((Void) null);
    }


    public void Activar(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask_A = new UserGetProcessActive();
        mAuthTask_A.execute((Void) null);
    }

    public void Desactivar(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new UserGetProcessDeActive();
        mAuthTask.execute((Void) null);
    }



    public class UserGetProcessDeActive extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();

            try {
                String responseCode;
                String responseMessage = "";


                Calendar cal = Calendar.getInstance();
                TimeZone tz = cal.getTimeZone();

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUserId());
                map.put("card",Session.getCardSelectActiveDeactive());
                //Utils.aloEncrpter(Session.getCardSelectActiveDeactive());
                map.put("timeZone", tz.getID());
                map.put("status", Constants.ACTIVE_STATUS_DEACTIVE);

                response_ = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_DEACTIVE_PROCESS, Constants.ALODIGA);
                responseCode = response_.getProperty("codigoRespuesta").toString();
                responseMessage = response_.getProperty("mensajeRespuesta").toString();


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
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CARD_NUMBER_EXISTS)) {
                    responsetxt = getString(R.string.web_services_response_50);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CARD_NUMBER_EXISTS)) {
                    responsetxt = getString(R.string.web_services_response_50);
                    serviceStatus = true;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NOT_ALLOWED_TO_CHANGE_STATE)) {
                    responsetxt = getString(R.string.web_services_response_51);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_UNABLE_TO_ACCESS_DATA)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_AUTHENTICALLY_IMPOSSIBLE)) {
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
                //Session.setOperationExchange(response_.getProperty("idTransaction").toString());
                //Session.setObjUserHasProducts(getListProductGeneric(response_));


                Intent i = new Intent(ActivateDeactivateCardStep1Activity.this, DeactiveCardStep3Activity.class);
                startActivity(i);
                finish();



            } else {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
                Intent i = new Intent(ActivateDeactivateCardStep1Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public class UserGetProcessActive extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();

            try {
                String responseCode;
                String responseMessage = "";


                Calendar cal = Calendar.getInstance();
                TimeZone tz = cal.getTimeZone();

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUserId());
                map.put("card", Utils.aloDesencript(Session.getCardSelectActiveDeactive().trim()));
                map.put("timeZone", tz.getID());
                map.put("status", Constants.ACTIVE_STATUS_ACTIVE);



                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_ACTIVE_PROCESS, Constants.ALODIGA);
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
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CARD_NUMBER_EXISTS)) {
                    responsetxt = getString(R.string.web_services_response_50);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NOT_ALLOWED_TO_CHANGE_STATE)) {
                    responsetxt = getString(R.string.web_services_response_51);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_UNABLE_TO_ACCESS_DATA)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_AUTHENTICALLY_IMPOSSIBLE)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THERE_ARE_NO_RECORDS_FOR_THE_REQUESTED_SEARCH)) {
                    responsetxt = getString(R.string.web_services_response_58);
                    serviceStatus = false;
                }  else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_NUMBER_OF_ORDERS_ALLOWED_IS_EXCEEDED)) {
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
            mAuthTask_A = null;
            //showProgress(false);
            if (success) {
                //Session.setOperationExchange(response_.getProperty("idTransaction").toString());
                //Session.setObjUserHasProducts(getListProductGeneric(response_));


                Intent i = new Intent(ActivateDeactivateCardStep1Activity.this, ActiveCardStep3Activity.class);
                startActivity(i);
                finish();



            } else {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
                Intent i = new Intent(ActivateDeactivateCardStep1Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask_A = null;
        }
    }


    public class UserGetProcessStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();

            try {
                String responseCode;
                String responseMessage = "";


                Calendar cal = Calendar.getInstance();
                TimeZone tz = cal.getTimeZone();

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUserId());
                map.put("card", Utils.aloDesencript(Session.getCardSelectActiveDeactive().trim()));
                map.put("timeZone", tz.getID());

                response_1 = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_ACTIVE_DEACTIVE_STATUS, Constants.ALODIGA);
                responseCode = response_1.getProperty("codigoRespuesta").toString();


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
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CARD_NUMBER_EXISTS)) {
                    responsetxt = getString(R.string.web_services_response_50);
                    serviceStatus = true;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NOT_ALLOWED_TO_CHANGE_STATE)) {
                    responsetxt = getString(R.string.web_services_response_51);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_UNABLE_TO_ACCESS_DATA)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_AUTHENTICALLY_IMPOSSIBLE)) {
                    responsetxt = getString(R.string.web_services_response_54);
                    serviceStatus = false;
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THERE_ARE_NO_RECORDS_FOR_THE_REQUESTED_SEARCH)) {
                    responsetxt = getString(R.string.web_services_response_58);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_NUMBER_OF_ORDERS_ALLOWED_IS_EXCEEDED)) {
                    responsetxt = getString(R.string.web_services_response_60);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THERE_ARE_NO_RECORDS_FOR_THE_REQUESTED_SEARCH)) {
                    responsetxt = getString(R.string.web_services_response_58);
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
            mAuthTask_S = null;
            //showProgress(false);
            if (success) {
                SoapObject res_ = (SoapObject) response_1.getProperty(3);

                String stateCode = res_.getProperty("stateCode").toString().trim();

                if(stateCode.equals(Constants.ACTIVE_STATUS_ACTIVE)){
                    Session.setIsactivateCard(true);
                    simpleSwitch.setChecked(true);
                    isChec=true;
                }
                else if(stateCode.equals(Constants.ACTIVE_STATUS_DEACTIVE)){
                    Session.setIsactivateCard(false);
                    simpleSwitch.setChecked(false);
                    isChec= true;
                }

            } else {

                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);

                Intent i = new Intent(ActivateDeactivateCardStep1Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

            progressDialogAlodiga.dismiss();
        }


        @Override
        protected void onCancelled() {
            mAuthTask_S = null;
        }
    }
}
