package com.alodiga.app.wallet.withdrawMyCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class WithdrawMyCardStep3Activity extends AppCompatActivity {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static EditText amountValue, conceptValue;
    private static TextView txtConceptValue_2, txtAmountValue_2,txtAccountSourceValue_2;
    private static Button btnProcessTransaction, backToLoginBtn;
    private String responsetxt = "";
    private String balancePrepaidCard = "";
    private String balanceAlocoins = "";
    private String balanceAlodiga = "";
    private boolean serviceStatus;
    SoapObject response;
    private ProcessOperationRechargeWalletTask mAuthTask = null;



    public WithdrawMyCardStep3Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_my_card_step_3);

        txtAmountValue_2 = findViewById(R.id.txtAmountValue_2);
        txtConceptValue_2 = findViewById(R.id.txtConceptValue_2);
        txtAccountSourceValue_2 = findViewById(R.id.txtAccountSourceValue_2);
        btnProcessTransaction = findViewById(R.id.btnProcessTransaction);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);

        txtAmountValue_2.setText(Session.getGetDestinationAmount() +" $");
        txtConceptValue_2.setText(getResources().getString(R.string.withdraw_card_title_single));
        txtAccountSourceValue_2.setText(getResources().getString(R.string.Prepaid_card));
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        btnProcessTransaction.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mAuthTask = new WithdrawMyCardStep3Activity.ProcessOperationRechargeWalletTask("1", Session.getGetDestinationAmount(),Session.getEmail());
                mAuthTask.execute((Void) null);
            }
        });


        backToLoginBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                        Intent i = new Intent(WithdrawMyCardStep3Activity.this, WithdrawMyCardStep2codeActivity.class);
                        startActivity(i);
                        finish();

            }
        });
    }


    protected ArrayList<ObjUserHasProduct> getListProduct(SoapObject response) {
        //ObjUserHasProduct[] obj2_aux= new ObjUserHasProduct[response.getPropertyCount()-3];
        //ObjUserHasProduct[] obj2 = new ObjUserHasProduct[response.getPropertyCount()-3];
        ArrayList<ObjUserHasProduct> obj2 = new ArrayList<>();
        for (int i = 5; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            String id = obj.getProperty("id").toString();
            String name = obj.getProperty("name").toString();
            String currentBalance = obj.getProperty("currentBalance").toString();
            String symbol = obj.getProperty("symbol").toString();
            String isPayTopUp = obj.getProperty("isPayTopUp").toString();
            String isUsePrepaidCard = obj.getProperty("isUsePrepaidCard").toString();
            ObjUserHasProduct object = new ObjUserHasProduct(id, name,currentBalance,symbol,isPayTopUp,isUsePrepaidCard);
            if (object.getName().equals("Tarjeta Prepagada") || object.getName().equals("Prepaid Card") ){
                Session.setAffiliatedCard(Boolean.parseBoolean(Session.getPrepayCardAsociate()));
                object.setNumberCard(Session.getNumberCard());
            }else{
                object.setNumberCard(Session.getAccountNumber());
            }
            obj2.add(object);
            //obj2[i-3] = object;
        }

        return obj2;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(WithdrawMyCardStep3Activity.this, WithdrawMyCardStep2codeActivity.class);
        startActivity(i);
        finish();
    }

    public class ProcessOperationRechargeWalletTask extends AsyncTask<Void, Void, Boolean> {
        private String  emailUser, productId, amountPayment;
        public ProcessOperationRechargeWalletTask(String productId_,String amountPayment_,String emailUser_) {
            this.amountPayment = amountPayment_; //nulo
            this.emailUser = emailUser_; //email
            this.productId = productId_; // idproductos
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
                map.put("email", emailUser);
                map.put("amountRecharge", amountPayment);
                map.put("productId", productId);

                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_DISPERTION, Constants.ALODIGA);
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
                }else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NON_EXISTENT_CARD)) {
                    responsetxt = getString(R.string.web_services_response_100);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXPIRATION_DATE_DIFFERS)) {
                    responsetxt = getString(R.string.web_services_response_102);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXPIRED_CARD)) {
                    responsetxt = getString(R.string.web_services_response_103);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_LOCKED_CARD)) {
                    responsetxt = getString(R.string.web_services_response_104);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_BLOCKED_ACCOUNT)) {
                    responsetxt = getString(R.string.web_services_response_105);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INVALID_ACCOUNT)) {
                    responsetxt = getString(R.string.web_services_response_106);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INSUFFICIENT_BALANCE)) {
                    responsetxt = getString(R.string.web_services_response_107);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INSUFFICIENT_LIMIT)) {
                    responsetxt = getString(R.string.web_services_response_108);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDIT_LIMIT_0)) {
                    responsetxt = getString(R.string.web_services_response_109);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDIT_LIMIT_0_OF_THE_DESTINATION_ACCOUNT)) {
                    responsetxt = getString(R.string.web_services_response_110);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_PROCESSING_THE_TRANSACTION)) {
                    responsetxt = getString(R.string.web_services_response_111);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INVALID_TRANSACTION)) {
                    responsetxt = getString(R.string.web_services_response_112);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATION_THE_TERMINAL)) {
                    responsetxt = getString(R.string.web_services_response_113);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DESTINATION_CARD_LOCKED)) {
                    responsetxt = getString(R.string.web_services_response_114);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DESTINATION_ACCOUNT_LOCKED)) {
                    responsetxt = getString(R.string.web_services_response_115);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INVALID_DESTINATION_CARD)) {
                    responsetxt = getString(R.string.web_services_response_116);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INVALID_DESTINATION_ACCOUNT)) {
                    responsetxt = getString(R.string.web_services_response_117);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_AMOUNT_MUST_BE_POSITIVE_AND_THE_AMOUNT_IS_REPORTED)) {
                    responsetxt = getString(R.string.web_services_response_118);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_AMOUNT_MUST_BE_NEGATIVE_AND_THE_AMOUNT_IS_REPORTED)) {
                    responsetxt = getString(R.string.web_services_response_119);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INVALID_TRANSACTION_DATE)) {
                    responsetxt = getString(R.string.web_services_response_120);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INVALID_TRANSACTION_TIME)) {
                    responsetxt = getString(R.string.web_services_response_121);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_SOURCE_OR_DESTINATION_ACCOUNT_IS_NOT_COMPATIBLE_WITH_THIS_OPERATION_NN)) {
                    responsetxt = getString(R.string.web_services_response_122);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_SOURCE_OR_DESTINATION_ACCOUNT_IS_NOT_COMPATIBLE_WITH_THIS_OPERATION_SN)) {
                    responsetxt = getString(R.string.web_services_response_123);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.SOURCE_OR_DESTINATION_ACCOUNT_IS_NOT_COMPATIBLE_WITH_THIS_OPERATION_NS)) {
                    responsetxt = getString(R.string.web_services_response_124);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRASACTION_BETWEEN_ACCOUNTS_NOT_ALLOWED)) {
                    responsetxt = getString(R.string.web_services_response_125);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRADE_VALIDATON_ERROR)) {
                    responsetxt = getString(R.string.web_services_response_126);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DESTINATION_CARD_DOES_NOT_SUPPORT_TRANSACTION)) {
                    responsetxt = getString(R.string.web_services_response_127);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_OPERATION_NOT_ENABLED_FOR_THE_DESTINATION_CARD)) {
                    responsetxt = getString(R.string.web_services_response_128);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_BIN_NOT_ALLOWED)) {
                    responsetxt = getString(R.string.web_services_response_129);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_STOCK_CARD)) {
                    responsetxt = getString(R.string.web_services_response_130);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_ACCOUNT_EXCEEDS_THE_MONTHLY_LIMIT)) {
                    responsetxt = getString(R.string.web_services_response_131);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_PAN_FIELD_IS_MANDATORY)) {
                    responsetxt = getString(R.string.web_services_response_132);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_AMOUNT_TO_BE_RECHARGE_IS_INCORRECT)) {
                    responsetxt = getString(R.string.web_services_response_133);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_THE_AMOUNT_MUST_BE_GREATER_THAN_0)) {
                    responsetxt = getString(R.string.web_services_response_134);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_ANSWERED)) {
                    responsetxt = getString(R.string.web_services_response_135);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATING_PIN)) {
                    responsetxt = getString(R.string.web_services_response_137);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATING_CVC1)) {
                    responsetxt = getString(R.string.web_services_response_138);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATING_CVC2)) {
                    responsetxt = getString(R.string.web_services_response_139);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PIN_CHANGE_ERROR)) {
                    responsetxt = getString(R.string.web_services_response_140);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATING_THE_ITEM)) {
                    responsetxt = getString(R.string.web_services_response_141);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_INVALID_AMOUNT)) {
                    responsetxt = getString(R.string.web_services_response_142);
                    serviceStatus = false;

                }

                else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
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
            Session.setOperationTransference(response.getProperty("idTransaction").toString());
            Intent i = new Intent(WithdrawMyCardStep3Activity.this, withdrawMyCardStep4Activity.class);
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
