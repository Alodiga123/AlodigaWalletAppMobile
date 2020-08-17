package com.alodiga.app.wallet.topup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterProductTopup;
import com.alodiga.app.wallet.adapters.SpinAdapterTransferMoney;
import com.alodiga.app.wallet.duallibrary.model.ObjTopUpInfos;
import com.alodiga.app.wallet.duallibrary.model.ObjTransferMoney;
import com.alodiga.app.wallet.duallibrary.topup.TopupController;
import com.alodiga.app.wallet.duallibrary.utils.CommonController;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;

import org.ksoap2.serialization.SoapObject;


public class TopupStep2Activity extends AppCompatActivity {

    static SoapObject response;
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static Spinner spinnerProduct, spinnerProductPago;
    String datosRespuesta = "";
    TextView range, product, operator;
    EditText number;
    ObjTopUpInfos objTopUpInfosSelect;
    private String responsetxt = "";
    private boolean serviceStatus;
    private ProcessTopup2 mAuthTask = null;
    private Button next, backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup_step2_layout);
        next = findViewById(R.id.next);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        number = findViewById(R.id.number);
        product = findViewById(R.id.product);
        range = findViewById(R.id.range);
        operator = findViewById(R.id.operator);
        spinnerProduct = findViewById(R.id.spinnerProduct);
        spinnerProductPago = findViewById(R.id.spinnerProductPago);
        product.setVisibility(View.INVISIBLE);
        spinnerProduct.setVisibility(View.INVISIBLE);
        range.setVisibility(View.INVISIBLE);
        number.setVisibility(View.INVISIBLE);


        //Spinner producto a debitar
        new Thread(new Runnable() {
            public void run() {

                try{
                    response = TopupController.getProductDeb();
                    String responseCode = response.getProperty("codigoRespuesta").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjTransferMoney[] listSpinner_product = TopupController.getListProduct(response);
                                SpinAdapterTransferMoney spinAdapterProduct;
                                spinAdapterProduct = new SpinAdapterTransferMoney(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_product);
                                spinnerProductPago.setAdapter(spinAdapterProduct);
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
        }).start();


        if (Session.getTypeTopup().equals(Constants.TOPUP_DF)) {
            operator.setText(Session.getDatosDenominacionFijaTopup()[0].getOpertador() + " " + Session.getNumberDestinationTopup());

            product.setVisibility(View.VISIBLE);
            spinnerProduct.setVisibility(View.VISIBLE);

            SpinAdapterProductTopup spinAdapterTopup;
            spinAdapterTopup = new SpinAdapterProductTopup(getApplicationContext(), android.R.layout.simple_spinner_item, Session.getDatosDenominacionFijaTopup());
            spinnerProduct.setAdapter(spinAdapterTopup);

        }


        if (Session.getTypeTopup().equals(Constants.TOPUP_OR)) {
            Session.setOperatorTopup(Session.getObjIsOpenRangeTopup().getOpertador());
            range.setVisibility(View.VISIBLE);
            number.setVisibility(View.VISIBLE);
            operator.setText(Session.getObjIsOpenRangeTopup().getOpertador() + " " + Session.getNumberDestinationTopup());
            String range_info = getString(R.string.product_spinner) + " " + Session.getObjIsOpenRangeTopup().getMinimumAmount()
                    + getString(R.string.dolar_a) + " " + Session.getObjIsOpenRangeTopup().getMaximumAmount() + getString(R.string.dolar) + " " +
                    getString(R.string.increment) + " " + Session.getObjIsOpenRangeTopup().getIncrement() + getString(R.string.dolar) + ":";

            range.setText(range_info);
            //Seteo de decimales al campo de monto
            number.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().matches("^\\ (\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                        StringBuilder getDecimal = CommonController.setDecimal(s);
                        number.setText(getDecimal.toString());
                        Selection.setSelection(number.getText(), getDecimal.toString().length());
                    }

                }
            });

        }


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ObjTransferMoney productselect = (ObjTransferMoney) spinnerProductPago.getSelectedItem();
                Float monto = Float.parseFloat(productselect.getCurrency());

                if (Session.getTypeTopup().equals(Constants.TOPUP_OR)) {
                    float number_value = Float.parseFloat(number.getText().toString());
                    String number_st = number.getText().toString();
                    float max = Float.parseFloat(Session.getObjIsOpenRangeTopup().getMaximumAmount());
                    float min = Float.parseFloat(Session.getObjIsOpenRangeTopup().getMinimumAmount());

                    if (number_st.length() == 0 || number_st.equals("")) {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.amount_info_invalid));
                    } else if (number_value > monto) {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.web_services_response_33));
                    } else if (number_value > max || number_value < min) {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.amount_req));
                    } else {
                        Session.setDestinationAmountTopup(number_st);
                        entrar(true, productselect.getId());

                    }
                }

                if (Session.getTypeTopup().equals(Constants.TOPUP_DF)) {
                    objTopUpInfosSelect = (ObjTopUpInfos) spinnerProduct.getSelectedItem();
                    float monto_df = Float.parseFloat(objTopUpInfosSelect.getDenomination().trim());

                    if (monto_df > monto) {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.web_services_response_33));
                    } else {

                        Session.setDestinationAmountTopup(objTopUpInfosSelect.getDenomination());
                        Session.setOperatorTopup(objTopUpInfosSelect.getOpertador());
                        entrar(false, productselect.getId());
                    }

                }
            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), TopupStep1Activity.class);
                startActivity(show);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent show;
        show = new Intent(getApplicationContext(), TopupStep1Activity.class);
        startActivity(show);
        finish();
    }
    public void entrar(boolean isOR, String productID) {

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new ProcessTopup2(objTopUpInfosSelect, productID, isOR);
        mAuthTask.execute((Void) null);

    }

    public void serviceAnswer(String responseCode) {
        if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
            responsetxt = getString(R.string.web_services_response_00);
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
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DESTINATION_MSISDN_OUT_OF_RANGE)) {
            responsetxt = getString(R.string.web_services_response_101);
            serviceStatus = false;
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DESTINATION_NOT_PREPAID)) {
            responsetxt = getString(R.string.web_services_response_204);
            serviceStatus = false;
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_TRANSACTION_TOP_UP)) {
            responsetxt = getString(R.string.web_services_response_205);
            serviceStatus = false;
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DENOMINATION_NOT_AVAILABLE)) {
            responsetxt = getString(R.string.web_services_response_301);
            serviceStatus = false;
        } else {
            responsetxt = getString(R.string.web_services_response_99);
            serviceStatus = false;
        }

    }

    public class ProcessTopup2 extends AsyncTask<Void, Void, Boolean> {


        private ObjTopUpInfos objTopUpInfosS;
        private String productid;
        private boolean isOR;

        public ProcessTopup2(ObjTopUpInfos objTopUpInfosS, String productid, boolean isOR) {
            this.objTopUpInfosS = objTopUpInfosS;
            this.productid = productid;
            this.isOR = isOR;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

                boolean availableBalance = true;
                String responseCode;

                try{
                if (availableBalance) {

                    response = TopupController.saveTopup(isOR,productid,objTopUpInfosS);
                    responseCode = response.getProperty("codigoRespuesta").toString();

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
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DESTINATION_MSISDN_OUT_OF_RANGE)) {
                        responsetxt = getString(R.string.web_services_response_101);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DESTINATION_NOT_PREPAID)) {
                        responsetxt = getString(R.string.web_services_response_204);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_TRANSACTION_TOP_UP)) {
                        responsetxt = getString(R.string.web_services_response_205);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DENOMINATION_NOT_AVAILABLE)) {
                        responsetxt = getString(R.string.web_services_response_301);
                        serviceStatus = false;
                    } else {
                        responsetxt = getString(R.string.web_services_response_99);
                        serviceStatus = false;
                    }
                } else {
                    responsetxt = getString(R.string.insuficient_balance);
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
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {

                Session.setOperationTopup(response.getProperty("idTransaction").toString());
                Session.setObjUserHasProducts(TopupController.getListProductGeneric(response));

                Intent show;
                show = new Intent(getApplicationContext(), TopupStep3Activity.class);
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
