package com.alodiga.app.wallet.paymentComerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjCountry;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class PaymentComerceStep6Activity extends AppCompatActivity {
    private static View view;
    private static TextView amountValue, conceptValue;


    private static TextView txtTransactionId_3, txtAccountSourceValue, txtDateTimeValue_3, acountNumberValue, destinationPhoneValue, destinationLastNameValue, destinationNameValue, txtAmountValue, txtConceptValue;

    private static TextView login;
    private static Button btnProcessFinisTransference;
    private static Button btnShareInformation;
    private static CheckBox terms_conditions;
    private static Spinner spinnerCountry;
    ArrayList<ObjUserHasProduct> userHasProducts = new ArrayList<ObjUserHasProduct>();
    private ProgressDialogAlodiga progressDialogAlodiga;
    private ObjCountry objCountry;
    private String responsetxt = "";
    private boolean serviceStatus;
    private String getAmountValue = "";
    private String getconceptValue = "";
    private String getTxtAccountNumberValue = "";
    private String getTxtDestinationPhoneValue = "";
    private String getTxtDestinationLastNameValue = "";
    private String getTxtDestinationNameValue = "";
    private Integer caseFindMoneyType = 0;


    public PaymentComerceStep6Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_transfer3_succesfull);


        amountValue = findViewById(R.id.txtAmountValue_3);
        conceptValue = findViewById(R.id.txtConceptValue_3);
        acountNumberValue = findViewById(R.id.txtAccountNumberValue_3);
        destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue_3);
        destinationLastNameValue = findViewById(R.id.txtDestinationLastNameValue_3);
        destinationNameValue = findViewById(R.id.txtDestinationNameValue_3);
        btnProcessFinisTransference = findViewById(R.id.btnProcessFinisTransference);
        btnShareInformation = findViewById(R.id.btnShareInformation);
        txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue_3);
        txtTransactionId_3 = findViewById(R.id.txtTransactionId_3);
        txtDateTimeValue_3 = findViewById(R.id.txtDateTimeValue_3);
        acountNumberValue.setText(Session.getDestinationAccountNumber());
        destinationPhoneValue.setText(Session.getDestinationPhoneValue());
        destinationLastNameValue.setText(Session.getDestinationLastNameValue());
        destinationNameValue.setText(Session.getDestinationNameValue());
        conceptValue.setText(Session.getDestinationConcept());
        amountValue.setText(Session.getGetDestinationAmount());
        txtTransactionId_3.setText(Session.getOperationPaymentComerce());
        txtDateTimeValue_3.setText(new Timestamp(new Date().getTime()).toGMTString());
        txtAccountSourceValue.setText(Session.getMoneySelected().getName().split("-")[0]);


        btnShareInformation.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.confirmation_title_successfull_Alodiga)+
                        "\n" + getString(R.string.destination_name)+" "+ Session.getDestinationNameValue() + " " + Session.getDestinationLastNameValue() +
                        "\n" + getString(R.string.destination_phoe)+" " + Session.getDestinationPhoneValue() +
                        "\n" + getString(R.string.destination_cuenta)+" " + Session.getDestinationAccountNumber() +
                        "\n" + getString(R.string.destination_amount)+" " + Session.getGetDestinationAmount() +
                        "\n" + getString(R.string.destination_concept)+" " + Session.getDestinationConcept() +
                        "\n" + getString(R.string.destination_source)+" " + Session.getMoneySelected().getName().split("-")[0] +
                        "\n" + getString(R.string.destination_date_time)+" " + new Timestamp(new Date().getTime()).toGMTString() +
                        "\n" + getString(R.string.number_trans)+" " + Session.getOperationPaymentComerce());
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
            }
        });


        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        btnProcessFinisTransference.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent i = new Intent(PaymentComerceStep6Activity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PaymentComerceStep6Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private ArrayList<ObjUserHasProduct> getElementsProduct(String elementGet, String response) {
        ArrayList<ObjUserHasProduct> objUserHasProducts = new ArrayList<ObjUserHasProduct>();
        String elementgetId = "id=";
        String elementGetName = "nombreProducto=";
        String elementGetCurrentBalance = "saldoActual=";
        String elementGetSymbol = "simbolo=";
        String litaProd = "respuestaListadoProductos=";

        for (int i = 1; i < getLenghtFromResponseJson(litaProd, response); i++) {
            ObjUserHasProduct objUserHasProduct = new ObjUserHasProduct(response.split(elementgetId)[i].split(";")[0], response.split(elementGetName)[i].split(";")[0], response.split(elementGetCurrentBalance)[i].split(";")[0], response.split(elementGetSymbol)[i].split(";")[0]);
            objUserHasProducts.add(objUserHasProduct);
        }

        return objUserHasProducts;
    }

    private String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }

    private Integer getLenghtFromResponseJson(String v, String response) {
        return (response.split(v).length);
    }
}
