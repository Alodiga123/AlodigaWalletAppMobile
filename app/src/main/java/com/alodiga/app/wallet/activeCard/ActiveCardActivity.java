package com.alodiga.app.wallet.activeCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ActiveCardActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    Button back, process;
    EditText card;
    private String responsetxt = "";
    private boolean serviceStatus;
    static ProgressDialogAlodiga progressDialogAlodiga;
    private activeCardTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activecard_step1_layout);
        back=findViewById(R.id.backToLoginBtn);
        process= findViewById(R.id.process);
        card = findViewById(R.id.card);

        //card.setText("zzTQTPW8sjZ1rXOFtcBmIM7+exK1iSVr4sWp1Avyjh6HqBr1Jlr7pWktVpSQAxziAaLbCmZ3P0GuJgMSBFhrOf/KiQq1YEO2MLhXrhRBtEqgPvt/5TE2++K+Dr//OcjFCArBr+MmpadvpIh4qT4zhau87w5IsFDaZkpzVikB7uM=");
        card.setText(Session.getCardSelectActiveDeactive());
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
                finish();
            }
        });

        process.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               entrar();
            }
        });
    }

    private void entrar() {
        String getCard= card.getText().toString();
        if (getCard.equals("") || getCard.length() == 0){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.invalid_all_question));
        }else{
            Session.setCardActive(getCard);

            Intent show;
            show = new Intent(getApplicationContext(), ActiveCardStep2codeActivity.class);
            startActivity(show);
            finish();
        }

        /*progressDialogAlodiga = new ProgressDialogAlodiga(getApplicationContext(), getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new activeCardTask();
        mAuthTask.execute((Void) null);*/



    }


    @Override
    public void onBackPressed() {
        Intent show = new Intent(ActiveCardActivity.this, MainActivity.class);
        startActivity(show);
        finish();
    }



    public class activeCardTask extends AsyncTask<Void, Void, Boolean> {


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


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS_MOVIL, Constants.REGISTRO_UNIFICADO);
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
                show = new Intent(getApplicationContext(), ActiveCardStep2codeActivity.class);
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

