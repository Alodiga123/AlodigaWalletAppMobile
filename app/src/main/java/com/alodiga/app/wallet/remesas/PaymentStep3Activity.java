package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;

public class PaymentStep3Activity extends AppCompatActivity {
    private static Button process, backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step3_layout);
        process=findViewById(R.id.process);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);

        process.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //en proceso
            }
        });



        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep3Activity.this, PaymentStep2Activity.class);
                startActivity(i);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(PaymentStep3Activity.this, PaymentStep2Activity.class);
        startActivity(i);
        finish();
    }
}
