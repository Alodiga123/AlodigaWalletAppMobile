package com.example.c2paplicationmobile;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public  class ActivityTransactionExecuted extends AppCompatActivity implements OnItemSelectedListener{


    private String idTransaction = "";
    private String productCurrencyId = "";
    private String paymentTypeId = "";
    private String transactionType = "";
    private String creationDate = "";
    private String amountTransaction = "";
    private String taxTransaction = "";
    private String destinationUserId = "";
    private String responsetxt = "";
    private boolean serviceStatus;
    private String stringResponse = "";
    private String destinationUser = "";

    static ProgressDialogAlodiga progressDialogAlodiga;
    static ArrayList<NewsItem> image_details;
    static TextView textViewEmpty;
    static ListView lv1;

    private boolean ActivityTransactionExcecuteIsCounting = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_executed);
        lv1 = (ListView) findViewById(R.id.custom_list);
        textViewEmpty = (TextView) findViewById(R.id.textViewEmpty);
        progressDialogAlodiga = new ProgressDialogAlodiga(this,"cargando..");
        ActivityTransactionExcecuteIsCounting = true;
        TransferOfFunds transferOfFunds = new TransferOfFunds(this);
        transferOfFunds.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public static class TransferOfFunds  extends AsyncTask<Void, Integer, Boolean>
    {
        private boolean serviceStatus;
        private boolean isEmpty = false;
        private boolean isGeneralError = false;
        private String responceReceived;
        private Context context;
        private String stringResponse;
        private String responsetxt;

        public TransferOfFunds(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;
            try {
                String responseCode;
                String datosRespuesta = "";


                HashMap<String,String > map = new HashMap<String,String>();
                //map.put("usuarioApi",Constants.WEB_SERVICES_USUARIOWS);
                //map.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
                map.put("userId",Session.getUserId());
                map.put("maxResult",Constants.MAX_RESULT_BY_TRANSACTION_OPERATION.toString());

                response = webService.invokeGetAutoConfigString(map,Constants.WEB_SERVICES_METHOD_NAME_GET_TRANSACTION_LIST,Constants.ALODIGA);
                stringResponse = response.toString();
                responseCode = response.getProperty("codigoRespuesta").toString();
                datosRespuesta = response.getProperty("datosRespuesta").toString();

                if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
                {
                    responsetxt = "";
                    serviceStatus = true;
                    return serviceStatus;

                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS))
                {
                    responsetxt = "Datos Invalidos";
                    serviceStatus = false;

                } else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA))
                {
                    responsetxt = "Contraseña Expirada";
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA))
                {
                    responsetxt = "La IP no es de confianza";
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS))
                {
                    responsetxt = "Las credenciales son invalidas";
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO))
                {
                    responsetxt = "Usuario bloqueado";
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE))
                {
                    responsetxt = "";
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO))
                {
                    responsetxt = "";
                    serviceStatus = false;
                }
                else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO))
                {
                    responsetxt = "";
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE))
                {
                    responsetxt = "";
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE))
                {
                    responsetxt = "";
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES))
                {
                    responsetxt = "";
                    serviceStatus = false;
                }else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO))
                {
                    responsetxt = "";
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
        protected void onProgressUpdate(Integer... values)
        {
            int progreso = values[0].intValue();
            progressDialogAlodiga.setProgress(progreso);
        }

        @Override
        protected void onPreExecute()
        {
            progressDialogAlodiga.show();
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            ArrayList<ObjTransaction> transactions = new ArrayList<ObjTransaction>();

            transactions = getParseResponse(stringResponse);
            ArrayList<NewsItem> image_details = new ArrayList<NewsItem>();
            for(ObjTransaction t : transactions){

                NewsItem item = new NewsItem();
                item.setDate(t.getCreateionDate());
                item.setHeadline(t.getAmount());
                item.setReporterName(t.getTax());
                item.setObject(t);
                item.setNegative(false);
                image_details.add(item);

            }
                textViewEmpty.setVisibility(View.INVISIBLE);
                lv1.setAdapter(new CustomListAdapter(this.context, image_details));
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Object o = lv1.getItemAtPosition(position);
                        NewsItem newsData = (NewsItem) o;
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle(Html.fromHtml("Detalle Transaccion"));
                        alertDialog.setMessage((newsData.isNegative()?"Saliente":"Entrante")
                                +"\n"
                                +"Transaccion: "+((ObjTransaction)newsData.getObject()).getTransactionId()
                                +"\n"
                                +"Monto: " + ((ObjTransaction)newsData.getObject()).getAmount()
                                +"\n"
                                +(newsData.isNegative()?"Al numero: ":"Desde el numero: ")+((ObjTransaction)newsData.getObject()).getProductName()
                                +"\n"
                                +"Tipo de Transaccion: " + ((ObjTransaction)newsData.getObject()).getTransactionType()
                                +"\n"
                                +"Fecha Transaccion: " + ((ObjTransaction)newsData.getObject()).getCreateionDate()
                                +"\n"
                                +"Destinatatio: " + ((ObjTransaction)newsData.getObject()).getUserDestination()
                                +"\n"
                                +"Impuesto: " + ((ObjTransaction)newsData.getObject()).getTax()
                                +"\n"
                                +"Comision: "+((ObjTransaction)newsData.getObject()).getCommision()
                                +"\n");
/****************/

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Enviar a:");
                        sendIntent.setType("text/plain");
                        //startActivity(sendIntent);

/**************/
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });
                progressDialogAlodiga.dismiss();


        }

        private ArrayList<ObjTransaction> getParseResponse (String response) {

            ArrayList<ObjTransaction> transactions = new ArrayList<ObjTransaction>();
            for (int i = 1; i < getLenghtFromResponseJson(response); i++) {

                ObjTransaction objTransaction = new ObjTransaction();
                objTransaction.setAmount(response.split("amount=")[i].split(";")[0]);
                objTransaction.setTransactionType(response.split("transactionType=")[i].split(";")[0]);
                objTransaction.setCreateionDate(response.split("creationDate=")[i].split(";")[0]);
                objTransaction.setTax(response.split("totalTax=")[i].split(";")[0]);

                transactions.add(objTransaction);
            }
            return  transactions;
        }


        private static int getLenghtFromResponseJson(String v){
            return (v.split("transactions=anyType").length);
        }

        private ArrayList<NewsItem> getListData(String[] var) {
            ArrayList<NewsItem> results = new ArrayList<NewsItem>();
            for(int i=0;i<var.length;i++){
                String [] register = var[i].split("/");
                NewsItem newsData = new NewsItem();
                if(register[0].equals("-")){
                    newsData.setNegative(true);
                    newsData.setReporterName("-"+register[4]);
                }else{
                    newsData.setNegative(false);
                    newsData.setReporterName(register[4]);
                }
                newsData.setHeadline(register[3]+"/"+register[5]);
                newsData.setDate(register[2]);
                results.add(newsData);
            }
            return results;
        }
    }





}

