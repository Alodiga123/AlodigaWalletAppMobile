package com.alodiga.app.wallet.listRemittence;


import android.app.AlertDialog;
import android.app.Dialog;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.AdapterCustomList;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.duallibrary.model.ObjNewsItem;
import com.alodiga.app.wallet.duallibrary.model.ObjTransaction;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ListRemittenceActivity extends AppCompatActivity implements OnItemSelectedListener {


    static ProgressDialogAlodiga progressDialogAlodiga;
    static ArrayList<ObjNewsItem> image_details;
    static TextView textViewEmpty;
    static ListView lv1;
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
    private boolean ActivityTransactionExcecuteIsCounting = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_remittence_layout);
        lv1 = findViewById(R.id.custom_list);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
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


    public class TransferOfFunds extends AsyncTask<Void, Integer, Boolean> {
        private boolean serviceStatus;
        private boolean isEmpty = false;
        private boolean isGeneralError = false;
        private String responceReceived;
        private Context context;
        private String stringResponse;
        private String responsetxt;
        private String showAlert;

        public TransferOfFunds(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;
            try {
                String responseCode;
                String datosRespuesta = "";


                HashMap<String, String> map = new HashMap<String, String>();
                //map.put("usuarioApi",Constants.WEB_SERVICES_USUARIOWS);
                //map.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
                map.put("userId", Session.getUserId());
                map.put("maxResult", Constants.MAX_RESULT_BY_TRANSACTION_OPERATION.toString());

                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_TRANSACTION_LIST, Constants.ALODIGA);
                stringResponse = response.toString();
                responseCode = response.getProperty("codigoRespuesta").toString();
                // datosRespuesta = response.getProperty("transactions").toString();

                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
                    responsetxt = "";
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
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_TRANSACTIONS)) {
                    responsetxt = getString(R.string.web_services_response_24);
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
            } catch (NullPointerException e) {
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
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();
            progressDialogAlodiga.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
//            progressDialogAlodiga.show();
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            if (result) {
                ArrayList<ObjTransaction> transactions = new ArrayList<ObjTransaction>();

                transactions = getParseResponse(stringResponse);
                ArrayList<ObjNewsItem> image_details = new ArrayList<ObjNewsItem>();
                for (ObjTransaction t : transactions) {


                    ObjNewsItem item = new ObjNewsItem();
                    item.setDate(t.getAmount());

                    String tt = "";

                    if (t.getTransactionType().equals("1")) {
                        t.setTransactionType("Recarga de Producto");
                        item.setNegative(true);
                    } else if (t.getTransactionType().equals("2")) {
                        t.setTransactionType("Pago en Comercio");
                        item.setNegative(false);
                    } else if (t.getTransactionType().equals("3")) {
                        t.setTransactionType("Transferencia de producto");
                        item.setNegative(false);
                    } else if (t.getTransactionType().equals("4")) {
                        t.setTransactionType("Intercambio de producto");
                        item.setNegative(false);
                    } else if (t.getTransactionType().equals("5")) {
                        t.setTransactionType("Retiro Manual");
                        item.setNegative(false);
                    } else if (t.getTransactionType().equals("6")) {
                        t.setTransactionType("Recarga Manual");
                        item.setNegative(true);
                    } else if (t.getTransactionType().equals("7")) {
                        t.setTransactionType("TopUp");
                        item.setNegative(true);
                    }
                    item.setHeadline(t.getTransactionType());
                    item.setReporterName(t.getUserDestination() + "/");
                    item.setObject(t);
                    image_details.add(item);

                }
                textViewEmpty.setVisibility(View.INVISIBLE);
                lv1.setAdapter(new AdapterCustomList(this.context, image_details));
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Object o = lv1.getItemAtPosition(position);
                        ObjNewsItem newsData = (ObjNewsItem) o;


                        String transactionType = "";


                        AlertDialog alertDialog = new AlertDialog.Builder(context,R.style.yourDialog).create();
                        alertDialog.setTitle(Html.fromHtml("Detalle Transaccion"));
                        showAlert = "Monto: " + ((ObjTransaction) newsData.getObject()).getAmount()
                                + "\n" + "Numero de Transacción: " + ((ObjTransaction) newsData.getObject()).getTransactionId()
                                + "\n"
                                + "Tipo de Transacción: " + ((ObjTransaction) newsData.getObject()).getTransactionType()
                                + "\n"
                                + "Fecha Transacción: " + ((ObjTransaction) newsData.getObject()).getCreateionDate()
                                + "\n"
                                + "Destino: " + ((ObjTransaction) newsData.getObject()).getUserDestination()
                                + "\n";
                        alertDialog.setMessage(showAlert);
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "COMPARTIR", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //updateProduct();
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, showAlert);
                                startActivity(Intent.createChooser(intent, "Share with"));


                            }
                        });
                        alertDialog.show();

                    }
                });
                progressDialogAlodiga.dismiss();


            } else {
                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);
                //Toast.makeText(context, responsetxt, Toast.LENGTH_LONG).show();
                Intent i = new Intent(this.context, MainActivity.class);
                startActivity(i);
                finish();
            }
        }

        private ArrayList<ObjTransaction> getParseResponse(String response) {

            ArrayList<ObjTransaction> transactions = new ArrayList<ObjTransaction>();
            for (int i = 1; i < getLenghtFromResponseJson(response); i++) {

                ObjTransaction objTransaction = new ObjTransaction();
                objTransaction.setAmount(response.split("amount=")[i].split(";")[0]);
                objTransaction.setTransactionType(response.split("transactionType=")[i].split(";")[0]);
                objTransaction.setCreateionDate(response.split("creationDate=")[i].split(";")[0]);
                objTransaction.setUserDestination(response.split("destinationUser=")[i].split(";")[0]);
                objTransaction.setConcept(response.split("concept=")[i].split(";")[0]);
                objTransaction.setTransactionId(response.split("id=")[i].split(";")[0]);
                // objTransaction.setTax(response.split("totalTax=")[i].split(";")[0]);
                transactions.add(objTransaction);
            }
            return transactions;
        }


        private int getLenghtFromResponseJson(String v) {
            return (v.split("transactions=anyType").length);
        }

        private ArrayList<ObjNewsItem> getListData(String[] var) {
            ArrayList<ObjNewsItem> results = new ArrayList<ObjNewsItem>();
            for (int i = 0; i < var.length; i++) {
                String[] register = var[i].split("/");
                ObjNewsItem newsData = new ObjNewsItem();
                if (register[0].equals("-")) {
                    newsData.setNegative(true);
                    newsData.setReporterName("-" + register[4]);
                } else {
                    newsData.setNegative(false);
                    newsData.setReporterName(register[4]);
                }
                newsData.setHeadline(register[3]);
                newsData.setDate(register[2]);
                results.add(newsData);
            }
            return results;
        }
    }
}

