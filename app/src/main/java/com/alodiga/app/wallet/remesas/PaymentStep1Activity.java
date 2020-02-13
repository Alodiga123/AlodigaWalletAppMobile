package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;

public class PaymentStep1Activity extends AppCompatActivity {
    private static Button next, quote;
    private static TableLayout txtTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step1_layout);
        next=findViewById(R.id.next);
        quote= findViewById(R.id.quote);
        txtTableLayout= findViewById(R.id.txtTableLayout);
        txtTableLayout.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);

        quote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                quote.setText(R.string.quote_);
                txtTableLayout.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);



            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep1Activity.this, PaymentStep2Activity.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(PaymentStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
