package com.alodiga.app.wallet.password;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ChangePasswordStep1Activity extends AppCompatActivity {
    private ProgressBar progressBar;
    EditText edtNewPassword, edtCurrentPassword, edtConfirmPassword;
    private int forwardIndicator;
    String currentPassword, newPassword, confirmPassword;
    private static int messageForToast;
    Button change;
    private String responsetxt = "";
    private boolean serviceStatus;
    SoapObject response;
    static ProgressDialogAlodiga progressDialogAlodiga;
    ProcessTask mAuthTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_step1_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBarPassword);
        change= findViewById(R.id.change);
        edtNewPassword = (EditText) findViewById(R.id.editTextNewPassword);

        edtNewPassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                forwardIndicator= Utils.progressBar(edtNewPassword.getText().toString());
                progressBar.setProgress(forwardIndicator);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });


        change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                evchangePassword();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ChangePasswordStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


    public void evchangePassword()
    {
        edtNewPassword  = (EditText) findViewById(R.id.editTextNewPassword);
        //edtCurrentPassword  = (EditText) findViewById(R.id.editTextCurrentPassword);
        edtConfirmPassword  = (EditText) findViewById(R.id.editTextConfirmPassword);
       // currentPassword = edtCurrentPassword.getText().toString();
        newPassword     = edtNewPassword.getText().toString();
        confirmPassword = edtConfirmPassword.getText().toString();

        if(/*!currentPassword.trim().equals("") && */!newPassword.trim().equals("") && !confirmPassword.trim().equals(""))
        {
            messageForToast= Utils.validatePassword(newPassword, confirmPassword);

            if(messageForToast != 0)
            {
               // Utils.createToast(getApplicationContext(), messageForToast);
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        getString(messageForToast));
            }else
            {

               /* new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        "Todo bien");
                Intent intent = getIntent();
                Intent newFormsi2 = new Intent(getApplicationContext(), ChangePasswordStep2Activity.class);
                startActivity(newFormsi2);*/
                entrar(Utils.aloDesencript(newPassword.trim()));
            }
        }else
        {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.invalid_all_question));
           // Utils.createToast(getApplicationContext(), R.string.required_fields);
        }
    }

    public void entrar(String key) {

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new ProcessTask(key);
        mAuthTask.execute((Void) null);

    }
    public class ProcessTask extends AsyncTask<Void, Void, Boolean> {


        private String key;

        public ProcessTask(String key) {
            this.key = key;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();

            try {

                boolean availableBalance = true;
                String responseCode;
                String responseMessage = "";


                if (availableBalance) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                    map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                    map.put("usuarioId", Session.getUserId());
                    map.put("credencial", key);

                    response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_CHANGE_PASSWORD, Constants.REGISTRO_UNIFICADO);
                    responseCode = response.getProperty("codigoRespuesta").toString();
                    responseMessage = response.getProperty("mensajeRespuesta").toString();

                    if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

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
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_NULOS)) {
                        responsetxt = getString(R.string.web_services_response_11);
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
                        responsetxt = responseMessage;
                        serviceStatus = false;
                    }
                } else {
                    responsetxt = getString(R.string.insuficient_balance);
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
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {


                Intent i = new Intent(ChangePasswordStep1Activity.this, ChangePasswordStep2Activity.class);
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
