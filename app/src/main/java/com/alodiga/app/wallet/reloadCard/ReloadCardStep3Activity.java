package com.alodiga.app.wallet.reloadCard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;

public class ReloadCardStep3Activity extends AppCompatActivity {
    private static TextView amountValue, conceptValue;
    private static TextView txtTransactionId_3, txtAccountSourceValue, txtDateTimeValue_3, acountNumberValue, destinationPhoneValue, destinationLastNameValue, destinationNameValue, txtAmountValue, txtConceptValue;
    private static Button btnProcessFinisTransference;
    private static Button btnShareInformation;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private String responsetxt = "";
    private boolean serviceStatus;


    public ReloadCardStep3Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reloadcard_step3_layout);

        /*//Destino
        acountNumberValue = findViewById(R.id.txtAccountNumberValue_3);
        //Monto
        amountValue = findViewById(R.id.txtAmountValue_3);
        //concepto
        conceptValue = findViewById(R.id.txtConceptValue_3);
        //Origen
        txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue_3);
        //fecha
        txtDateTimeValue_3 = findViewById(R.id.txtDateTimeValue_3);
        //transaccion
        txtTransactionId_3 = findViewById(R.id.txtTransactionId_3);

        btnProcessFinisTransference = findViewById(R.id.btnProcessFinisTransference);
        btnShareInformation = findViewById(R.id.btnShareInformationTransference);

        acountNumberValue.setText(Session.getDestinationAccountNumber());
        amountValue.setText(Session.getGetDestinationAmount() +" $");
        conceptValue.setText(Session.getDestinationConcept());
        txtAccountSourceValue.setText(Session.getMoneySelected().getName().split("-")[0]);
        txtDateTimeValue_3.setText(new Timestamp(new Date().getTime()).toGMTString());
        txtTransactionId_3.setText(Session.getOperationTransference());



        btnShareInformation.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.confirmation_title_successfull_Alodiga)+
                        "\n" + getString(R.string.destination_source)+" " + Session.getMoneySelected().getName().split("-")[0] +
                        "\n" + getString(R.string.destination_cuenta)+" " + Session.getDestinationAccountNumber() +
                        "\n" + getString(R.string.destination_amount)+" " + Session.getGetDestinationAmount() + " $" +
                        "\n" + getString(R.string.destination_concept)+" " + Session.getDestinationConcept() +
                        "\n" + getString(R.string.destination_date_time)+" " + new Timestamp(new Date().getTime()).toGMTString() +
                        "\n" + getString(R.string.number_trans)+" " + Session.getOperationTransference());
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
            }
        });

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        btnProcessFinisTransference.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent i = new Intent(ReloadCardStep3Activity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();
            }
        });

*/
    }



    @Override
    public void onBackPressed() {
        Intent show = new Intent(ReloadCardStep3Activity.this, MainActivity.class);
        finish();
        startActivity(show);

    }
}
