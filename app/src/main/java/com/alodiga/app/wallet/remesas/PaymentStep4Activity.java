package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.utils.Session;

public class PaymentStep4Activity extends AppCompatActivity {
    private static Button process, backToLoginBtn;
    private static TextView reloadcard_source_, amount_to_send, Correspondent, delivery_method, shipping_rate, exchange_rate, Amount_to_deliver, total_to_pay, name, editTextTelephone, location, state, city, codezip, av;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step4_layout);
        process=findViewById(R.id.process);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);

        process.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //en proceso
            }
        });

        reloadcard_source_= findViewById(R.id.reloadcard_source_);
        Correspondent= findViewById(R.id.Correspondent);
        delivery_method= findViewById(R.id.delivery_method);
        shipping_rate= findViewById(R.id.shipping_rate);
        exchange_rate= findViewById(R.id.exchange_rate);
        Amount_to_deliver= findViewById(R.id.Amount_to_deliver);
        total_to_pay= findViewById(R.id.total_to_pay);

        name= findViewById(R.id.name);
        editTextTelephone= findViewById(R.id.editTextTelephone);
        location= findViewById(R.id.location);
        state= findViewById(R.id.state);
        city= findViewById(R.id.city);
        codezip= findViewById(R.id.codezip);
        av= findViewById(R.id.av);

        reloadcard_source_.setText(Session.getPay().getReloadcard_source().getName());
        Correspondent.setText(Session.getPay().getCorrespondent().getName());
        delivery_method.setText(Session.getPay().getDelivery_method().getName());
        shipping_rate.setText(Session.getPay().getShipping_rate());
        exchange_rate.setText(Session.getPay().getExchange_rate());
        Amount_to_deliver.setText(Session.getPay().getActual_amount_to_send());
        total_to_pay.setText(Session.getPay().getActual_amount_to_pay());

        name.setText(Session.getRemittenceDestinatario().getName()+" "+Session.getRemittenceDestinatario().getLastName());
        editTextTelephone.setText(Session.getRemittenceDestinatario().getTelephone());
        location.setText(Session.getRemittenceDestinatario().getLocation().getName());
        state.setText(Session.getRemittenceDestinatario().getState().getName());
        city.setText(Session.getRemittenceDestinatario().getCity().getName());
        codezip.setText(Session.getRemittenceDestinatario().getCodeZip());
        av.setText(Session.getRemittenceDestinatario().getAv());




        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep4Activity.this, PaymentStep3Activity.class);
                startActivity(i);
                finish();
            }
        });


        process.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep4Activity.this, PaymentStep5Activity.class);
                startActivity(i);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(PaymentStep4Activity.this, PaymentStep3Activity.class);
        startActivity(i);
        finish();
    }
}
