package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterBank;
import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.remesas.PaymentController;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.utils.CustomToast;

import org.ksoap2.serialization.SoapObject;

import static com.alodiga.app.wallet.duallibrary.remesas.PaymentController.getCiuddad1;
import static com.alodiga.app.wallet.duallibrary.remesas.PaymentController.getState;

public class PaymentStep3_2Activity extends AppCompatActivity {
    private static Button next, backToLoginBtn;
    private static EditText name, lastName, editTextTelephone, edtstate, edtcity, edtcode, edtAv, userEmailIdTransfer, secondname,secondSurmane;

    private static TextView info_estado, info_ciudad, location;
    private static Spinner spinner_pais, spinner_estado,spinner_ciudad ;
    private String responsetxt = "";
    private boolean serviceStatus;
    private static String stringResponse = "";
    String datosRespuesta = "";
    static SoapObject response_pais,response_estado,response_ciudad;
    static ObjGenericObject[] listSpinner_pais = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_estado = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_ciudad = new ObjGenericObject[0];
    private Integer caseFind = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step3_2_layout);
        next=findViewById(R.id.next);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);

        edtstate=findViewById(R.id.edtstate);
        edtcity=findViewById(R.id.edtcity);
        edtcode=findViewById(R.id.edtcode);
        edtAv=findViewById(R.id.edtAv);
        location= findViewById(R.id.location);


        info_ciudad= findViewById(R.id.info_ciudad);
        info_estado  = findViewById(R.id.info_estado) ;

        spinner_pais= findViewById(R.id.spinner_pais);
        spinner_estado= findViewById(R.id.spinner_estado);
        spinner_ciudad= findViewById(R.id.spinner_ciudad);

        edtstate.setVisibility(View.INVISIBLE);
        edtcity.setVisibility(View.INVISIBLE);
        spinner_estado.setVisibility(View.INVISIBLE);
        spinner_ciudad.setVisibility(View.INVISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate();
            }
        });



        location.setText(Session.getPay().getDestination_country().getName());

               new Thread(new Runnable() {
                    public void run() {
                        try {

                            response_estado = getState();
                            String responseCode = response_estado.getProperty("code").toString();

                            serviceAnswer(responseCode);

                            if (serviceStatus) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        info_estado.setVisibility(View.VISIBLE);
                                        info_estado.setText(R.string.estado_info);
                                        spinner_estado.setVisibility(View.VISIBLE);
                                        edtstate.setVisibility(View.INVISIBLE);
                                        edtcity.setVisibility(View.INVISIBLE);

                                        listSpinner_estado = PaymentController.getListGeneric(response_estado);
                                        SpinAdapterBank spinAdapterBank;
                                        spinAdapterBank = new SpinAdapterBank(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_estado);
                                        spinner_estado.setAdapter(spinAdapterBank);
                                    }
                                });

                            } else {
                                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_RECORDS_NOT_FOUND)) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            info_estado.setVisibility(View.VISIBLE);
                                            edtstate.setVisibility(View.VISIBLE);
                                            info_ciudad.setText(R.string.kyc_text_step4_city);
                                            info_ciudad.setVisibility(View.VISIBLE);
                                            edtcity.setVisibility(View.VISIBLE);
                                        }
                                    });

                                }else{
                                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                            responsetxt);
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


        //Spinner Bank
        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_ciudad.setEnabled(true);

                listSpinner_ciudad = new ObjGenericObject[0];
                edtcity.setVisibility(View.INVISIBLE);
                spinner_ciudad.setVisibility(View.INVISIBLE);

                final ObjGenericObject estado = (ObjGenericObject) spinner_estado.getSelectedItem();

                new Thread(new Runnable() {
                    public void run() {
                        try {

                            response_ciudad = getCiuddad1( estado);
                            stringResponse = response_ciudad.toString();
                            String responseCode = response_ciudad.getProperty("code").toString();
                            serviceAnswer(responseCode);

                            if (serviceStatus) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        info_ciudad.setText(R.string.ciudad_info);
                                        info_ciudad.setVisibility(View.VISIBLE);
                                        edtstate.setVisibility(View.INVISIBLE);
                                        edtcity.setVisibility(View.INVISIBLE);
                                        spinner_ciudad.setVisibility(View.VISIBLE);

                                        listSpinner_ciudad = PaymentController.getListGeneric(response_ciudad);
                                        SpinAdapterBank spinAdapterBank;
                                        spinAdapterBank = new SpinAdapterBank(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_ciudad);
                                        spinner_ciudad.setAdapter(spinAdapterBank);
                                    }
                                });

                            } else {
                                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_RECORDS_NOT_FOUND)) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            edtstate.setVisibility(View.INVISIBLE);
                                            edtstate.setVisibility(View.INVISIBLE);
                                            info_ciudad.setVisibility(View.VISIBLE);
                                            info_ciudad.setText(R.string.kyc_text_step4_city);
                                            edtcity.setVisibility(View.VISIBLE);
                                        }
                                    });

                                }else{
                                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                            responsetxt);
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep3Activity.class);
                startActivity(i);
                finish();
            }
        });


    }

    public void serviceAnswer(String responseCode) {
        if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO_) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
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
        } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_RECORDS_NOT_FOUND)) {
            responsetxt = getString(R.string.web_services_response_28_Remittence);
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
        }

    }


    public void validate(){

        String getedtstate= edtstate.getText().toString();
        String getedtcity= edtcity.getText().toString();
        String getedtcode= edtcode.getText().toString();
        String getedtAv= edtAv.getText().toString();


        if( ((spinner_ciudad.getVisibility()== View.INVISIBLE) && (getedtcity.equals("") || getedtcity.length() == 0))
                || ((spinner_estado.getVisibility() == View.INVISIBLE) && (getedtstate.equals("") || getedtstate.length() == 0 || getedtcity.equals("") || getedtcity.length() == 0))
                || (getedtcode.equals("") || getedtcode.length() == 0
                ||getedtAv.equals("") || getedtAv.length() == 0)){

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.invalid_all_question));

        }else{


            if(spinner_estado.getVisibility()== View.VISIBLE) {
                Session.getRemittenceDestinatario().setState((ObjGenericObject) spinner_estado.getSelectedItem());
            }else{
                Session.getRemittenceDestinatario().setState(new ObjGenericObject(getedtstate,""));
            }

            if(spinner_ciudad.getVisibility()== View.VISIBLE) {
                Session.getRemittenceDestinatario().setCity((ObjGenericObject) spinner_ciudad.getSelectedItem());
            }else{
                Session.getRemittenceDestinatario().setCity(new ObjGenericObject(getedtcity,""));
            }

            Session.getRemittenceDestinatario().setCodeZip(getedtcode);
            Session.getRemittenceDestinatario().setAv(getedtAv);

            Intent pasIntent = getIntent();
            Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep4Activity.class);
            startActivity(i);
            finish();

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep3Activity.class);
        startActivity(i);
        finish();
    }



}
