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
import com.alodiga.app.wallet.model.ObjGenericObject;
import com.alodiga.app.wallet.model.ObjRemittencePerson;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        /*userEmailIdTransfer=findViewById(R.id.userEmailIdTransfer);

        name= findViewById(R.id.name);
        secondname= findViewById(R.id.secondname);
        lastName= findViewById(R.id.lastName);
        secondSurmane= findViewById(R.id.secondSurmane);*/
        //editTextTelephone= findViewById(R.id.editTextTelephone);
        edtstate=findViewById(R.id.edtstate);
        edtcity=findViewById(R.id.edtcity);
        edtcode=findViewById(R.id.edtcode);
        edtAv=findViewById(R.id.edtAv);
        location= findViewById(R.id.location);


        info_ciudad= findViewById(R.id.info_ciudad);
        info_estado  = findViewById(R.id.info_estado) ;


       /* edtstate=findViewById(R.id.edtstate);
        edtcity=findViewById(R.id.edtcity);
        edtcode=findViewById(R.id.edtcode);
        edtAv=findViewById(R.id.edtAv);*/

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

      /*  //Spinner Pais
        new Thread(new Runnable() {
            public void run() {
                try {

                    listSpinner_pais = new ObjGenericObject[0];
                    listSpinner_estado = new ObjGenericObject[0];
                    listSpinner_ciudad = new ObjGenericObject[0];
                    edtstate.setVisibility(View.INVISIBLE);
                    edtcity.setVisibility(View.INVISIBLE);
                    spinner_estado.setVisibility(View.INVISIBLE);
                    spinner_ciudad.setVisibility(View.INVISIBLE);

                    String responseCode = null;
                    WebService webService = new WebService();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId", Session.getUserId());
                    response_pais = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES_, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);
                    stringResponse = response_pais.toString();
                    responseCode = response_pais.getProperty("code").toString();
                    serviceAnswer(responseCode);

                    if (serviceStatus) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listSpinner_pais = getListGeneric(response_pais);
                                SpinAdapterPais spinAdapterPais;
                                spinAdapterPais = new SpinAdapterPais(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner_pais);
                                spinner_pais.setAdapter(spinAdapterPais);

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
        }).start();*/


        //Spinner Bank
       /* spinner_pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_estado.setEnabled(true);
                spinner_ciudad.setAdapter(null);

                listSpinner_estado = new ObjGenericObject[0];
                listSpinner_ciudad = new ObjGenericObject[0];
                edtstate.setVisibility(View.INVISIBLE);
                edtcity.setVisibility(View.INVISIBLE);
                spinner_estado.setVisibility(View.INVISIBLE);
                spinner_ciudad.setVisibility(View.INVISIBLE);
                //spinnerproducto.setAdapter(null);

                final ObjGenericObject pais = (ObjGenericObject) spinner_pais.getSelectedItem();
                //Toast.makeText(getApplicationContext(),"id" + pais.getId() ,Toast.LENGTH_SHORT).show();*/

               new Thread(new Runnable() {
                    public void run() {
                        try {

                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("countryCode", Session.getPay().getDestination_country().getId());
                            response_estado = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETSTATESBYCOUNTRY, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);
                            responseCode = response_estado.getProperty("code").toString();

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

                                        listSpinner_estado = getListGeneric(response_estado);
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
           /* }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });*/


        //Spinner Bank
        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_ciudad.setEnabled(true);

                listSpinner_ciudad = new ObjGenericObject[0];
                edtcity.setVisibility(View.INVISIBLE);
                spinner_ciudad.setVisibility(View.INVISIBLE);

                final ObjGenericObject estado = (ObjGenericObject) spinner_estado.getSelectedItem();
                //Toast.makeText(getApplicationContext(),"id" + pais.getId() ,Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    public void run() {
                        try {

                            String responseCode = null;
                            WebService webService = new WebService();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("stateCode", estado.getId());
                            response_ciudad = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETCITIESBYSTATE, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);
                            stringResponse = response_ciudad.toString();
                            responseCode = response_ciudad.getProperty("code").toString();
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

                                        listSpinner_ciudad = getListGeneric(response_ciudad);
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

              /*  if(Session.getRemettencesDireccionId().equals(Constants.REMITTENCE_ID)){
                    Intent pasIntent = getIntent();
                    Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep2Activity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep1Activity.class);
                    startActivity(i);
                    finish();
                }*/

                Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep3Activity.class);
                startActivity(i);
                finish();
            }
        });


       /* name.setText("adira");
        secondname.setText("yadira");
        lastName.setText("quintero");
        secondSurmane.setText("aaasaadira");
        editTextTelephone.setText("04142223322");*/
        //edtstate.setText("estado");
        //edtcity.setText("ciudad");
        //edtcode.setText("0000");
        //userEmailIdTransfer.setText("adira@jjmmm.com");
        //edtAv.setText("av");

    }

    protected ObjGenericObject[] getListGeneric(SoapObject response) {

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 2];

        for (int i = 2; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjGenericObject object = new ObjGenericObject(obj.getProperty("name").toString(), obj.getProperty("id").toString());

            obj2[i - 2] = object;
        }

        return obj2;
    }

    public void serviceAnswer(String responseCode) {
        if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO_) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
            responsetxt = getString(R.string.web_services_response_00);
            serviceStatus = true;
            //return serviceStatus;

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

        //Pattern p = Pattern.compile(Utils.regEx);
        //Matcher m = p.matcher(userEmailIdTransfer.getText());

        //String getname= name.getText().toString();
        //String getsecondname = secondname.getText().toString();
        //String getlastName= lastName.getText().toString();
        //String getmidleName= secondSurmane.getText().toString();
        //String geteditTextTelephone= editTextTelephone.getText().toString();
        String getedtstate= edtstate.getText().toString();
        String getedtcity= edtcity.getText().toString();
        String getedtcode= edtcode.getText().toString();
        String getedtAv= edtAv.getText().toString();
        //String getuserEmailIdTransfer= userEmailIdTransfer.getText().toString();


        if( //getname.equals("") || getname.length() == 0
                //|| getlastName.equals("") || getlastName.length() == 0
                //|| geteditTextTelephone.equals("") || geteditTextTelephone.length() == 0
                 ((spinner_ciudad.getVisibility()== View.INVISIBLE) && (getedtcity.equals("") || getedtcity.length() == 0))
                || ((spinner_estado.getVisibility() == View.INVISIBLE) && (getedtstate.equals("") || getedtstate.length() == 0 || getedtcity.equals("") || getedtcity.length() == 0))
                || (getedtcode.equals("") || getedtcode.length() == 0
                ||getedtAv.equals("") || getedtAv.length() == 0)){
                //|| getuserEmailIdTransfer.equals("") || getuserEmailIdTransfer.length() == 0
                //|| getsecondname.equals("") || getsecondname.length() == 0
                //|| getmidleName.equals("") || getmidleName.length() == 0)){

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.invalid_all_question));

        }/*else if ((caseFind == 0) && (!m.find())) {

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.email_invalid));
        }*/else{

            //ObjRemittencePerson remittenceDestinatario= new ObjRemittencePerson();
            //remittenceDestinatario.setName(getname);
            //remittenceDestinatario.setSecondname(getsecondname);
            //remittenceDestinatario.setLastName(getlastName);
            //remittenceDestinatario.setSecondSurmane(getmidleName);
            //remittenceDestinatario.setEmail(getuserEmailIdTransfer);
            //remittenceDestinatario.setTelephone(geteditTextTelephone);
            //remittenceDestinatario.setLocation(Session.getPay().getDestination_country());

            if(spinner_estado.getVisibility()== View.VISIBLE) {
                Session.getRemittenceDestinatario().setState((ObjGenericObject) spinner_estado.getSelectedItem());
                //remittenceDestinatario.setState((ObjGenericObject) spinner_estado.getSelectedItem());
            }else{
                Session.getRemittenceDestinatario().setState(new ObjGenericObject(getedtstate,""));
                //remittenceDestinatario.setState(new ObjGenericObject(getedtstate,""));
            }

            if(spinner_ciudad.getVisibility()== View.VISIBLE) {
                Session.getRemittenceDestinatario().setCity((ObjGenericObject) spinner_ciudad.getSelectedItem());
                //remittenceDestinatario.setCity((ObjGenericObject) spinner_ciudad.getSelectedItem());
            }else{
                Session.getRemittenceDestinatario().setCity(new ObjGenericObject(getedtcity,""));
                //remittenceDestinatario.setCity(new ObjGenericObject(getedtcity,""));
            }

            Session.getRemittenceDestinatario().setCodeZip(getedtcode);
            //remittenceDestinatario.setCodeZip(getedtcode);
            Session.getRemittenceDestinatario().setAv(getedtAv);
            //remittenceDestinatario.setAv(getedtAv);

            //Session.setRemittenceDestinatario(remittenceDestinatario);

            Intent pasIntent = getIntent();
            Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep4Activity.class);
            startActivity(i);
            finish();

        }




     /*   if( getname.equals("") || getname.length() == 0
                || getlastName.equals("") || getlastName.length() == 0
                || geteditTextTelephone.equals("") || geteditTextTelephone.length() == 0
                || getedtstate.equals("") || getedtstate.length() == 0
                || getedtcity.equals("") || getedtcity.length() == 0
                ||getedtcode.equals("") || getedtcode.length() == 0
                ||getedtAv.equals("") || getedtAv.length() == 0 ){

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.invalid_all_question));

        }else{

            Intent pasIntent = getIntent();
            Intent i = new Intent(PaymentStep3Activity.this, PaymentStep4Activity.class);
            startActivity(i);
            finish();

        }*/

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if(Session.getRemettencesDireccionId().equals(Constants.REMITTENCE_ID)){
            Intent pasIntent = getIntent();
            Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep2Activity.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep1Activity.class);
            startActivity(i);
            finish();
        }*/

        Intent i = new Intent(PaymentStep3_2Activity.this, PaymentStep3Activity.class);
        startActivity(i);
        finish();
    }



}
