package com.alodiga.app.wallet.transference;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.AlodigaCryptographyUtils;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;
import com.google.zxing.Result;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class TransferenceStep2QrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private String destinationAccountNumber = "";
    private String destinationPhoneValue = "";
    private String destinationLastNameValue = "";
    private String destinationNameValue = "";
    private String selectedMoney = "";


    private String destinationIdValue = "";


    private Integer caseFind = 0;
    private String responsetxt = "";
    private boolean serviceStatus;
    private TransferenceStep2QrActivity.FindUserTask mAuthTask = null;

    private static String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        progressDialogAlodiga = new ProgressDialogAlodiga(this, "Cargando..");
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();

        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getApplicationContext(), "Escaneo Exitoso", Toast.LENGTH_LONG).show();
        mScannerView.stopCamera();


        //   Intent i = new Intent(PagarActivity.this, CustomerConfirmActivity.class);
        //  startActivity(i);
        // finish();
        // Toast.makeText( getApplicationContext(), "Escaneo Exitoso",Toast.LENGTH_LONG).show();
        Log.i("QRCode", rawResult.getText());
        //String text="";
        AlodigaCryptographyUtils obj = new AlodigaCryptographyUtils();
        try {
            String text = AlodigaCryptographyUtils.decrypt(rawResult.getText().trim(), Constants.KEY_ENCRIPT_DESENCRIPT_QR);
            Log.i("QRCode", rawResult.getText());
            mScannerView.resumeCameraPreview(this);


            String emailFind = text.split(";")[0];
            if (emailFind.equals(Session.getEmail())) {
                Intent i = new Intent(TransferenceStep2QrActivity.this, TransferenceStep1Activity.class);
                startActivity(i);
                finish();
                //Toast.makeText( getApplicationContext(), getString(R.string.app_operation_not_permited),Toast.LENGTH_LONG).show();
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        getString(R.string.app_operation_not_permited));
            } else {
                mAuthTask = new TransferenceStep2QrActivity.FindUserTask(emailFind);
                mAuthTask.execute((Void) null);
            }

        } catch (Exception e) {
            //Toast.makeText( getApplicationContext(), getString(R.string.app_error_general),Toast.LENGTH_LONG).show();
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.app_error_general));
            Intent i = new Intent(TransferenceStep2QrActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            e.printStackTrace();
        }


    }

    public class FindUserTask extends AsyncTask<Void, Void, Boolean> {
        private final String phoneOrEmail;

        FindUserTask(String element) {
            phoneOrEmail = element;
        }

        @Override
        protected void onPreExecute() {
            progressDialogAlodiga.show();
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
                map.put("email", phoneOrEmail);
                String methodName = "getUsuarioporemail";

                response = WebService.invokeGetAutoConfigString(map, methodName, Constants.REGISTRO_UNIFICADO);
                responseCode = response.getProperty("codigoRespuesta").toString();
                //Activar las preguntas de seguridad

                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
                    String res = response.getProperty("datosRespuesta").toString();
                    destinationAccountNumber = getValueFromResponseJson("numeroCuenta", res);
                    destinationPhoneValue = getValueFromResponseJson("movil", res);
                    destinationLastNameValue = getValueFromResponseJson("apellido", res);
                    destinationNameValue = getValueFromResponseJson("nombre", res);
                    destinationIdValue = getValueFromResponseJson("UsuarioID", res);
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
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_PHONE_NUMBER)) {
                    responsetxt = getString(R.string.web_services_response_22);
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

            if (success) {
                //llama activities
                //llama activities
                selectedMoney = Session.getMoneySelected().getId();
                Session.setDestinationAccountNumber(destinationAccountNumber);
                Session.setDestinationLastNameValue(destinationLastNameValue);
                Session.setDestinationPhoneValue(destinationPhoneValue);
                Session.setDestinationNameValue(destinationNameValue);
                Session.setUsuarioDestionId(destinationIdValue);
                Intent i = new Intent(TransferenceStep2QrActivity.this, TransferenceStep3Activity.class);
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











