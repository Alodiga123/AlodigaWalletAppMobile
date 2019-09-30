package com.example.c2paplicationmobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Manual_Removal_Activity extends AppCompatActivity {

    private Spinner spinnerbank;
    private static String stringResponse = "";
    private String responsetxt = "";
    private boolean serviceStatus;
    String datosRespuesta = "";
    SoapObject response;

    //SoapObject response;

    static ProgressDialogAlodiga progressDialogAlodiga;
    private ObjGenericObject object_select,object2_select ,object3_select;

    private UserGetbankTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_removal_layout);
        progressDialogAlodiga = new ProgressDialogAlodiga(this,"cargando..");
        progressDialogAlodiga.show();

        initViews();

        /*//List<ObjGenericObject> bank = new ArrayList<ObjGenericObject>();
        ObjGenericObject[] objBank = new ObjGenericObject[3];
        objBank[0] = new ObjGenericObject("Banco","0");
        objBank[1] = new ObjGenericObject("Estados Unidos","1");
        objBank[2] = new ObjGenericObject("Venezuela","2");
        objBank[2] = new ObjGenericObject("Peru","3");

        SpinAdapterBank SpinAdapterBank;
        SpinAdapterBank = new SpinAdapterBank(this, android.R.layout.simple_spinner_item, objBank);
        spinnerbank.setAdapter(SpinAdapterBank);*/


    }


    // Initialize the views
    private void initViews() {
        mAuthTask = new UserGetbankTask();
        mAuthTask.execute((Void) null);
        spinnerbank= (Spinner)  findViewById(R.id.spinnerbank);
    }



    public class UserGetbankTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();
            //SoapObject response;
            try {
                String responseCode;



                HashMap<String,String > map = new HashMap<String,String>();
                /*map.put("usuarioApi",Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
                map.put("IdIdioma","4");*/

                response = webService.invokeGetAutoConfigString(map,Constants.WEB_SERVICES_METHOD_NAME_GET_BANK,Constants.ALODIGA);
                stringResponse = response.toString();
                responseCode = response.getProperty("codigoRespuesta").toString();
                datosRespuesta = response.getProperty("mensajeRespuesta").toString();


                if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
                {
                    responsetxt = getString(R.string.web_services_response_00);
                    serviceStatus = true;
                    return serviceStatus;

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
            //showProgress(false);
            if (success) {

                ObjBank[] objBank = getListBank(response);
                SpinAdapterBank SpinAdapterBank;
                SpinAdapterBank = new SpinAdapterBank(getApplicationContext(), android.R.layout.simple_spinner_item, objBank);
                spinnerbank.setAdapter(SpinAdapterBank);

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


    protected ObjBank[] getListBank (SoapObject banks){

    //List<ObjGenericObject> Listbank = new ArrayList();
    ArrayList<ObjGenericObject> List1 = new ArrayList<>();
        ObjBank[] obj2 = new ObjBank[banks.getPropertyCount()-3];

        for(int i=3; i<banks.getPropertyCount(); i++)
    {
        SoapObject obj = (SoapObject) banks.getProperty(i);
        String propiedad = banks.getProperty(i).toString();
        ObjBank bank= new ObjBank(obj.getProperty("id").toString(),obj.getProperty("name").toString());
        //Listbank.add(bank);
        obj2[i-3] = bank;
    }

    return obj2;
}

}

