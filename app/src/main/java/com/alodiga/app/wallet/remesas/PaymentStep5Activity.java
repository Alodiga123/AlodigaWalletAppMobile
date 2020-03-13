package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.Session;

import java.sql.Timestamp;
import java.util.Date;

public class PaymentStep5Activity extends AppCompatActivity {
    private static Button finish, btnShareInformation;
    private static TextView status,reloadcard_source_, amount_to_send, Correspondent, delivery_method, shipping_rate, exchange_rate, Amount_to_deliver, total_to_pay, name, editTextTelephone, location, state, city, codezip, av, nameRem, editTextTelephoneRem, idOP, txtDateTimeValue_3,  emailuser,lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step5_layout);
        finish=findViewById(R.id.btnProcessFinish);
        btnShareInformation=findViewById(R.id.btnShareInformation);
        status=findViewById(R.id.status);

        idOP= findViewById(R.id.idOP);
        txtDateTimeValue_3=findViewById(R.id.txtDateTimeValue_3);

        reloadcard_source_= findViewById(R.id.reloadcard_source_);
        Correspondent= findViewById(R.id.Correspondent);
        delivery_method= findViewById(R.id.delivery_method);
        shipping_rate= findViewById(R.id.shipping_rate);
        exchange_rate= findViewById(R.id.exchange_rate);
        Amount_to_deliver= findViewById(R.id.Amount_to_deliver);
        total_to_pay= findViewById(R.id.total_to_pay);

        name= findViewById(R.id.name);
        emailuser= findViewById(R.id.emailuser);
        lastName= findViewById(R.id.lastName);
        editTextTelephone= findViewById(R.id.editTextTelephone);
        location= findViewById(R.id.location);
        state= findViewById(R.id.state);
        city= findViewById(R.id.city);
        codezip= findViewById(R.id.codezip);
        av= findViewById(R.id.av);

        nameRem= findViewById(R.id.nameRem);
        editTextTelephoneRem= findViewById(R.id.editTextTelephoneRem);

        //id
        idOP.setText(Session.getProcessRemittence().getId());
        txtDateTimeValue_3.setText(new Timestamp(new Date().getTime()).toGMTString());
        status.setText(Session.getProcessRemittence().getStatus());

        //informacion pago
        reloadcard_source_.setText(Session.getPay().getReloadcard_source().getName().split("-")[0]);
        Correspondent.setText(Session.getPay().getCorrespondent().getName());
        delivery_method.setText(Session.getPay().getDelivery_method().getName());
        shipping_rate.setText(Session.getPay().getShipping_rate());
        exchange_rate.setText(Session.getPay().getExchange_rate());
        Amount_to_deliver.setText(Session.getPay().getActual_amount_to_send());
        total_to_pay.setText(Session.getPay().getActual_amount_to_pay());

        //Remitente
        nameRem.setText(Session.getUsername());
        editTextTelephoneRem.setText(Session.getPhoneNumber());

        //Destinatario
        name.setText(Session.getRemittenceDestinatario().getName()+" "+Session.getRemittenceDestinatario().getSecondname());
        emailuser.setText(Session.getRemittenceDestinatario().getEmail());
        lastName.setText(Session.getRemittenceDestinatario().getLastName() + " "+ Session.getRemittenceDestinatario().getSecondSurmane());
        editTextTelephone.setText(Session.getRemittenceDestinatario().getTelephone());
        location.setText(Session.getRemittenceDestinatario().getLocation().getName());
        state.setText(Session.getRemittenceDestinatario().getState().getName());
        city.setText(Session.getRemittenceDestinatario().getCity().getName());
        codezip.setText(Session.getRemittenceDestinatario().getCodeZip());
        av.setText(Session.getRemittenceDestinatario().getAv());





        btnShareInformation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.confirmation_title_successfull_Alodiga)+
                        "\n" + getString(R.string.destination_operatión_resum)+" - "+getString(R.string.remesas_title)+
                        "\n" + "**********"+
                        "\n" + getString(R.string.destination_transaction_id)+": "+ idOP.getText().toString() +
                        "\n" + getString(R.string.status_remittence)+": " + status.getText().toString() +
                        "\n" + getString(R.string.destination_date_time)+": " + new Timestamp(new Date().getTime()).toGMTString() +
                        "\n" + "**********"+
                        "\n" + getString(R.string.datasender)+" "+
                        "\n" + getString(R.string.reloadcard_source_)+": " + reloadcard_source_.getText().toString() +
                        "\n" + getString(R.string.Correspondent)+": " + Correspondent.getText().toString() +
                        "\n" + getString(R.string.delivery_method)+": " + delivery_method.getText().toString()  +
                        "\n" + getString(R.string.shipping_rate)+": " + shipping_rate.getText().toString() +
                        "\n" + getString(R.string.exchange_rate)+": " + exchange_rate.getText().toString() +
                        "\n" + getString(R.string.Amount_to_deliver)+": " + Amount_to_deliver.getText().toString() +
                        "\n" + getString(R.string.Actual_amount_to_pay)+": " + total_to_pay.getText().toString() +
                        "\n" + "**********"+
                        "\n" + getString(R.string.addresseeRem)+" " +
                        "\n" + getString(R.string.name)+": " + nameRem.getText().toString() +
                        "\n" + getString(R.string.editTextTelephone)+" " + editTextTelephoneRem.getText().toString() +
                        "\n" + "**********"+
                        "\n" + getString(R.string.addressee)+" " +
                        "\n" + getString(R.string.name)+": " + name.getText().toString() +
                        "\n" + getString(R.string.lastName)+": " + lastName.getText().toString() +
                        "\n" + getString(R.string.editTextTelephone)+": " + editTextTelephone.getText().toString() +
                        "\n" + getString(R.string.email_)+": " + emailuser.getText().toString() +
                        "\n" + getString(R.string.location)+": " + location.getText().toString() +
                        "\n" + getString(R.string.kyc_text_step4_state_)+": " + state.getText().toString() +
                        "\n" + getString(R.string.kyc_text_step4_city_)+": " + city.getText().toString() +
                        "\n" + getString(R.string.kyc_text_step4_codezip)+": " + codezip.getText().toString() +
                        "\n" + getString(R.string.kyc_text_step4_av)+": " + av.getText().toString() );
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep5Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(PaymentStep5Activity.this, PaymentStep3Activity.class);
        startActivity(i);
        finish();
    }
}
