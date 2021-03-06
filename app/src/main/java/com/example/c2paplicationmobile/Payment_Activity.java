package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Payment_Activity extends AppCompatActivity {

    private Spinner spinnerIdentification, spinnerTypeFind;
    private EditText userEmailIdTransfer, mobileNumberTransfer,getPhoneOrEmail, editTextTelephone;
    private View viewQ;
    private Button signFind;
    private String responsetxt = "";
    private boolean serviceStatus;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private Payment_Activity.FindUserTask mAuthTask = null;
    private Integer caseFind = 0;
    private  String destinationAccountNumber = "";
    private  String destinationPhoneValue = "";
    private  String destinationLastNameValue = "";
    private  String destinationNameValue = "";
    private  String destinationIdValue = "";
    private Integer currencySelected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__es);

        spinnerIdentification = (Spinner)findViewById(R.id.spinnerIdentification);
        spinnerTypeFind = (Spinner)findViewById(R.id.spinnerTypeFind);
        userEmailIdTransfer = (EditText)  findViewById(R.id.userEmailOrPhoneIdTransfer);
        viewQ = (View)  findViewById(R.id.viewQ);
        signFind = (Button)  findViewById(R.id.signFind);



        String[] optionsID = {"Alocoin","Saldo Alodiga", "HealthCareCoin"};
        String[] optionsBank = {" ","Provincial","Mercantil", "Banesco", "Bicentenario", "Banco de Venezuela", "Banco del Tesoro", "Banco Caroní","Banco Sofitasa", "Banpro", "Banco Fondo Común", "Banfoandes", "Banco Occidental de Descuento", "Banco Venezolano de Crédito", "Banco Exterior", "Banco Plaza", "Citibank", "Banplus"};
        String[] optionsTelephone = {" ","0416", "0426", "0412","0414", "0424"};


        //Llena tipos de cuenta
        List<ObjTransferMoney> countries = new ArrayList<ObjTransferMoney>();
        ObjTransferMoney[] objTransferMoney = new ObjTransferMoney[3];
        objTransferMoney[0] = new ObjTransferMoney("0","Saldo Alodiga USD "+ Session.getAlodigaBalance());
        objTransferMoney[1] = new ObjTransferMoney("1","Saldo Alocoins ALC "+ Session.getAlocoinsBalance());
        objTransferMoney[2] = new ObjTransferMoney("2","Saldo Tarjeta Prepagada CC "+Session.getHealthCareCoinsBalance());
        SpinAdapterTransferMoney spinAdapterTransferMoney;
        spinAdapterTransferMoney = new SpinAdapterTransferMoney(this.getApplicationContext(), android.R.layout.simple_spinner_item, objTransferMoney);
        spinnerIdentification.setAdapter(spinAdapterTransferMoney);
        spinnerIdentification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ObjTransferMoney objTransferMoney1;
                objTransferMoney1 = (ObjTransferMoney) spinnerIdentification.getSelectedItem();
                if(objTransferMoney1.getId().equals("0")){
                    currencySelected = 0;
                }else if (objTransferMoney1.getId().equals("1")){
                    currencySelected = 1;
                }else if (objTransferMoney1.getId().equals("2")){
                    currencySelected = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //Llena como quiere busca usuarios


        ObjHowToTranssfer[] objHowToTranssfers = new ObjHowToTranssfer[3];
        objHowToTranssfers[0] = new ObjHowToTranssfer("0","Por email");
        objHowToTranssfers[1] = new ObjHowToTranssfer("1","Por número celular");
        objHowToTranssfers[2] = new ObjHowToTranssfer("2","Por Codigo QR");
        SpinAdapterHowToTransfer spinAdapterHowToTransfer = new SpinAdapterHowToTransfer(this.getApplicationContext(), android.R.layout.simple_spinner_item, objHowToTranssfers);
        spinnerTypeFind.setAdapter(spinAdapterHowToTransfer);



        spinnerTypeFind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ObjHowToTranssfer objHowToTranssfer;
                objHowToTranssfer = (ObjHowToTranssfer) spinnerTypeFind.getSelectedItem();
                if(objHowToTranssfer.getId().equals("0")){
                    userEmailIdTransfer.setHint("Introduce E-mail");
                    userEmailIdTransfer.setInputType(InputType.TYPE_CLASS_TEXT);
                    viewQ.setVisibility(View.VISIBLE);
                    userEmailIdTransfer.setVisibility(View.VISIBLE);
                    signFind.setText("Buscar");
                    userEmailIdTransfer.setText("");
                    caseFind = 0;
                }else if (objHowToTranssfer.getId().equals("1")){
                    userEmailIdTransfer.setInputType(InputType.TYPE_CLASS_NUMBER);
                    userEmailIdTransfer.setHint("Numero Celular");
                    userEmailIdTransfer.setVisibility(View.VISIBLE);
                    userEmailIdTransfer.setText("");
                    caseFind = 1;
                    viewQ.setVisibility(View.VISIBLE);
                    signFind.setText("Buscar");
                }else if (objHowToTranssfer.getId().equals("2")){
                    caseFind = 2;
                    userEmailIdTransfer.setText("");
                    userEmailIdTransfer.setVisibility(View.INVISIBLE);
                    viewQ.setVisibility(View.INVISIBLE);
                    signFind.setText("Scanear QR");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here

            }

        });
        progressDialogAlodiga = new ProgressDialogAlodiga(this, "Cargando..");
         signFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(checkValidation()){

                    if(caseFind.equals(2)){
                        Session.setMoneySelected(currencySelected);
                        Intent i = new Intent(Payment_Activity.this, TransferQrActivity.class);
                        startActivity(i);
                        finish();


                    }else{
                        mAuthTask = new FindUserTask(userEmailIdTransfer.getText().toString());
                        mAuthTask.execute((Void) null);
                    }
                }
            }
        });



    }


    // Check Validation Method
    private boolean checkValidation() {
        // Get all edittext texts
        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(userEmailIdTransfer.getText());
        // Check if all strings are null or not
        if (userEmailIdTransfer.equals("")){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    "Todos los campos son requeridos.");
            return false;
        }
            // Check if email id valid or not
        else if ((caseFind==0)&&(!m.find())){

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    "El email es invalido.");
            return false;
        }

        else if ((caseFind==1)&&(userEmailIdTransfer.length()<=11)){
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    "La longitud del telefono en invalida");
            return false;
        }
        return true;

    }



    public void Process(View View){

        Toast toast1 = Toast.makeText(getApplicationContext(), "Procesado...", Toast.LENGTH_SHORT);
        toast1.show();

    }

    public class FindUserTask extends AsyncTask<Void, Void, Boolean> {
        private final String phoneOrEmail;

        FindUserTask(String element){
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
                HashMap<String,String > map = new HashMap<String,String>();
                map.put("usuarioApi",Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
                String methodName = "";
                switch (caseFind) {

                    case 0:
                        map.put("email",phoneOrEmail);
                        methodName = "getUsuarioporemail";
                        break;

                    case 1:
                        map.put("movil",phoneOrEmail);
                        methodName = "getUsuariopormovil";
                        break;

                    default:

                        break;
                }
                response = webService.invokeGetAutoConfigString(map,methodName);
                responseCode = response.getProperty("codigoRespuesta").toString();
                //Activar las preguntas de seguridad

                if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
                {
                    String res =  response.getProperty("datosRespuesta").toString();
                    destinationAccountNumber = getValueFromResponseJson("numeroCuenta",res) ;
                    destinationPhoneValue = getValueFromResponseJson("movil",res);
                    destinationLastNameValue = getValueFromResponseJson("apellido",res);
                    destinationNameValue = getValueFromResponseJson("nombre",res);
                    destinationIdValue = getValueFromResponseJson("UsuarioID",res);
                    serviceStatus = true;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS))
                {
                    responsetxt = getString(R.string.web_services_response_01);
                    serviceStatus = false;

                } else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA))
                {
                    responsetxt = getString(R.string.web_services_response_03);
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA))
                {
                    responsetxt = getString(R.string.web_services_response_04);
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS))
                {
                    responsetxt = getString(R.string.web_services_response_05);
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO))
                {
                    responsetxt = getString(R.string.web_services_response_06);
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE))
                {
                    responsetxt = getString(R.string.web_services_response_08);
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO))
                {
                    responsetxt = getString(R.string.web_services_response_12);
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO))
                {
                    responsetxt = getString(R.string.web_services_response_95);
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE))
                {
                    responsetxt = getString(R.string.web_services_response_96);
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE))
                {
                    responsetxt = getString(R.string.web_services_response_97);
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES))
                {
                    responsetxt = getString(R.string.web_services_response_98);
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO))
                {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_PHONE_NUMBER))
                {
                    responsetxt = getString(R.string.web_services_response_22);
                    serviceStatus = false;
                }
                //progressDialogAlodiga.dismiss();
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
                System.err.println(e);
                return false;
            } catch (Exception e)
            {
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
                Session.setMoneySelected(currencySelected);
                Session.setDestinationAccountNumber(destinationAccountNumber);
                Session.setDestinationLastNameValue(destinationLastNameValue);
                Session.setDestinationPhoneValue(destinationPhoneValue);
                Session.setDestinationNameValue(destinationNameValue);
                Session.setUsuarioDestionId(destinationIdValue);
                Intent i = new Intent(Payment_Activity.this, Confirmation1_Activity.class);
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


    private static String getValueFromResponseJson(String v, String response){
        return (response.split(v+"=")[1].split(";")[0]);
    }


}


