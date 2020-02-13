package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;

public class PaymentStep2Activity extends AppCompatActivity {
    private static Button next, backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step2_layout);
        next=findViewById(R.id.next);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep2Activity.this, PaymentStep3Activity.class);
                startActivity(i);
                finish();
            }
        });



        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep2Activity.this, PaymentStep1Activity.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(PaymentStep2Activity.this, PaymentStep1Activity.class);
        startActivity(i);
        finish();
    }
}
