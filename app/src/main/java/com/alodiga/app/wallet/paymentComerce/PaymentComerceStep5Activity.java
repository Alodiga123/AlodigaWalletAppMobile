package com.alodiga.app.wallet.paymentComerce;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;

import org.ksoap2.serialization.SoapObject;

import static com.alodiga.app.wallet.duallibrary.paymentComerce.PaymentComerceController.getListProduct;
import static com.alodiga.app.wallet.duallibrary.paymentComerce.PaymentComerceController.paymentComerce;

public class PaymentComerceStep5Activity extends AppCompatActivity {
    private static View view;
    private static TextView amountValue, conceptValue;

    private static TextView txtAccountSourceValue, acountNumberValue, destinationPhoneValue, destinationLastNameValue, destinationNameValue, txtAmountValue, txtConceptValue;

    private static Button btnProcessTransaction, backToLoginBtn;
    SoapObject response;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private String responsetxt = "";
    private boolean serviceStatus;
    private String balancePrepaidCard = "";
    private String balanceAlocoins = "";
    private String balanceAlodiga = "";
    private ProcessOperationTransferenceTask mAuthTask = null;

    public PaymentComerceStep5Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_transfer2);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);
        amountValue = findViewById(R.id.txtAmountValue_2);
        conceptValue = findViewById(R.id.txtConceptValue_2);
        acountNumberValue = findViewById(R.id.txtAccountNumberValue_2);
        destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue_2);
        destinationLastNameValue = findViewById(R.id.txtDestinationLastNameValue_2);
        destinationNameValue = findViewById(R.id.txtDestinationNameValue_2);
        btnProcessTransaction = findViewById(R.id.btnProcessTransaction);
        txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue_2);
        acountNumberValue.setText(Session.getDestinationAccountNumber());
        destinationPhoneValue.setText(Session.getDestinationPhoneValue());
        destinationLastNameValue.setText(Session.getDestinationLastNameValue());
        destinationNameValue.setText(Session.getDestinationNameValue());
        conceptValue.setText(Session.getDestinationConcept());
        amountValue.setText(Session.getGetDestinationAmount());
        txtAccountSourceValue.setText(Session.getMoneySelected().getName());

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        btnProcessTransaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                procesar();
            }
        });
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PaymentComerceStep5Activity.this, PaymentComerceStep4codeActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PaymentComerceStep5Activity.this, PaymentComerceStep4codeActivity.class);
        startActivity(i);
        finish();
    }
    public void procesar() {


        mAuthTask = new ProcessOperationTransferenceTask("1", Session.getEmail(), Session.getMoneySelected().getId(), Session.getGetDestinationAmount(), Session.getDestinationConcept(), "1", Session.getUsuarioDestionId());
        mAuthTask.execute((Void) null);


    }


    public class ProcessOperationTransferenceTask extends AsyncTask<Void, Void, Boolean> {


        private String cryptogramShop, emailUser, productId, amountPayment,
                conceptTransaction, cryptogramUser, idUserDestination;

        public ProcessOperationTransferenceTask(String cryptogramShop_, String emailUser_, String productId_, String amountPayment_, String conceptTransaction_, String cryptogramUser_, String idUserDestination_) {
            this.cryptogramShop = cryptogramShop_; //nulo
            this.emailUser = emailUser_; //email
            this.productId = productId_; // idproductos
            this.amountPayment = amountPayment_; //monto
            this.conceptTransaction = conceptTransaction_; //paymentshop
            this.cryptogramUser = cryptogramUser_; // nulo
            this.idUserDestination = idUserDestination_; //destino
        }

        @Override
        protected Boolean doInBackground(Void... params) {

                boolean availableBalance = true;
                try{
                if (availableBalance) {

                    response = paymentComerce(  cryptogramShop,  emailUser,   productId,  amountPayment,  conceptTransaction,  cryptogramUser,  idUserDestination );
                    String responseCode = response.getProperty("codigoRespuesta").toString();

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
                        responsetxt = getString(R.string.web_services_response_99);
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
            progressDialogAlodiga.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {

                Session.setAlocoinsBalance(balanceAlocoins);
                Session.setHealthCareCoinsBalance(balancePrepaidCard);
                Session.setAlodigaBalance(balanceAlodiga);
                Session.setObjUserHasProducts(getListProduct(response));
                Session.setOperationPaymentComerce(response.getProperty("idTransaction").toString());
                Intent i = new Intent(PaymentComerceStep5Activity.this, PaymentComerceStep6Activity.class);
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
