package com.alodiga.app.wallet.manualRecharge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;

public class ManualRechargeStep2WelcomeActivity extends AppCompatActivity {
    private static Button submit1, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_layout);
        submit1 = findViewById(R.id.finalizar);

        submit1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
                finish();


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ManualRechargeStep2WelcomeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
