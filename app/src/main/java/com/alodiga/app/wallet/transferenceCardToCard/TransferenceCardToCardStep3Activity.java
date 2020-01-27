package com.alodiga.app.wallet.transferenceCardToCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.activeCard.ActiveCardStep3Activity;
import com.alodiga.app.wallet.transference.TransferenceStep1Activity;
import com.alodiga.app.wallet.transference.TransferenceStep4codeActivity;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class TransferenceCardToCardStep3Activity extends AppCompatActivity {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static EditText amountValue, conceptValue;
    private static TextView txtAccountSourceValue,txtAccountdesValue, acountNumberValue, destinationPhoneValue, destinationLastNameValue, destinationNameValue;
    private static Button btnProcessConfirmation1, backToLoginBtn;
    private String responsetxt = "";
    private boolean serviceStatus;
    private getCardTask mAuthTask;
    SoapObject response;



    public TransferenceCardToCardStep3Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transferencecardtocardstep3_layout);

        txtAccountdesValue= findViewById(R.id.txtAccountdesValue);
        amountValue = findViewById(R.id.txtAmountValue);
        conceptValue = findViewById(R.id.txtConceptValue);
        btnProcessConfirmation1 = findViewById(R.id.btnProcessConfirmation1);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);


        destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue);
        destinationPhoneValue.setText(Session.getDestinationPhoneValue());

        destinationNameValue = findViewById(R.id.txtDestinationNameValue);
        destinationNameValue.setText(Session.getDestinationNameValue());

        destinationLastNameValue = findViewById(R.id.txtDestinationLastNameValue);
        destinationLastNameValue.setText(Session.getDestinationLastNameValue());

        destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue);
        destinationPhoneValue.setText(Session.getDestinationPhoneValue());

        txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue);
        txtAccountSourceValue.setText(Session.getTranferenceCardToCardEncrip());

        setCard();

        btnProcessConfirmation1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (checkValidation()) {
                    Session.setDestinationConcept(conceptValue.getText().toString());
                    Session.setGetDestinationAmount(amountValue.getText().toString());

                    if ( Float.parseFloat(amountValue.getText().toString().trim())<=0) {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.amount_req));
                    } else {
                        Intent i = new Intent(TransferenceCardToCardStep3Activity.this, TransferenceCardToCardStep4codeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        backToLoginBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                        Intent i = new Intent(TransferenceCardToCardStep3Activity.this, TransferenceCardToCardStep1Activity.class);
                        startActivity(i);
                        finish();

            }
        });


        amountValue.addTextChangedListener(new TextWatcher() {
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

                    amountValue.setText(cashAmountBuilder.toString());
                    // keeps the cursor always to the right
                    Selection.setSelection(amountValue.getText(), cashAmountBuilder.toString().length());

                }

            }
        });
    }

    public void setCard(){
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new getCardTask();
        mAuthTask.execute((Void) null);
    }


    private boolean checkValidation() {
        // Check if all strings are null or not
        if (conceptValue.getText().toString().equals("")) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.concept_req));

            return false;
        }
        if (amountValue.getText().toString().equals("")) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.amount_info_invalid));
            return false;
        }
        if (Float.valueOf(amountValue.getText().toString()) <= 0) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.amount_req));
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(TransferenceCardToCardStep3Activity.this, TransferenceCardToCardStep1Activity.class);
        startActivity(i);
        finish();
    }


    public class getCardTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();

            try {
                String responseCode;
                String responseMessage = "";

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUsuarioDestionId());


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_TRANSFERENCE_GET_CARD, Constants.ALODIGA);
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
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_LOADING_CARDS)) {
                        responsetxt = getString(R.string.web_services_response_28);
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
                String numberCard= response.getProperty("numberCard").toString();
                Session.setTranferenceCardToCardEncripDest(numberCard.substring(0,4) + "*********" + numberCard.substring(numberCard.length()-4,numberCard.length()));
                Session.setTranferenceCardToCardDest(numberCard);
                txtAccountdesValue.setText(Session.getTranferenceCardToCardEncripDest());


            } else {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);

                Intent i = new Intent(TransferenceCardToCardStep3Activity.this, TransferenceCardToCardStep1Activity.class);
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



}
