package com.alodiga.app.wallet.companionCards;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.FailCodeOperationActivity;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class CompanionCardsStep3codeActivity extends AppCompatActivity {
    static int cout = 1;
    static int cout_aux = 3;
    UserGetCodeTask mAuthTask;
    private TextView backToLoginBtn, step1_next_button, tvintentos;
    private EditText edtMobileCode;
    private String responsetxt = "";
    private boolean serviceStatus;
    private ProgressDialogAlodiga progressDialogAlodiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companioncardstep3_code_layout);
        step1_next_button = findViewById(R.id.step1_next_button);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        edtMobileCode = findViewById(R.id.edtMobileCode);
        tvintentos = findViewById(R.id.tvintentos);

        step1_next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String getCode = edtMobileCode.getText().toString();

                if (getCode.equals("") || getCode.length() == 0 || getCode.length() != 4) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.pin_text));
                } else if (cout >= 3) {
                    Intent i = new Intent(CompanionCardsStep3codeActivity.this, FailCodeOperationActivity.class);
                    finish();
                    startActivity(i);
                } else {
                    progressDialogAlodiga = new ProgressDialogAlodiga(getApplicationContext(), getString(R.string.loading));
                    mAuthTask = new UserGetCodeTask(Utils.aloDesencript(getCode));
                    mAuthTask.execute((Void) null);
                }

            }
        });


        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CompanionCardsStep3codeActivity.this, CompanionCardsStep2Activity.class);
                finish();
                startActivity(i);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CompanionCardsStep3codeActivity.this, CompanionCardsStep2Activity.class);
        finish();
        startActivity(i);
    }


    public class UserGetCodeTask extends AsyncTask<Void, Void, Boolean> {

        private final String clave;


        public UserGetCodeTask(String clave) {
            this.clave = clave;
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
                map.put("usuarioId", Session.getUserId());
                map.put("pin", clave);


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_VALID_CODE, Constants.REGISTRO_UNIFICADO);
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

                if (cout <= 3) {
                    Session.setCodeOperation(edtMobileCode.getText().toString());
                    Intent i = new Intent(CompanionCardsStep3codeActivity.this, CompanionCardsStep4Activity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(CompanionCardsStep3codeActivity.this, FailCodeOperationActivity.class);
                    startActivity(i);
                    finish();
                }


            } else {
                edtMobileCode.setText("");
                cout_aux = cout_aux - 1;
                tvintentos.setText(getString(R.string.info_fail_code) + cout_aux);
                cout = cout + 1;

                if (cout_aux == 0) {
                    Intent i = new Intent(CompanionCardsStep3codeActivity.this, FailCodeOperationActivity.class);
                    startActivity(i);
                    finish();
                }
            }
            progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}
