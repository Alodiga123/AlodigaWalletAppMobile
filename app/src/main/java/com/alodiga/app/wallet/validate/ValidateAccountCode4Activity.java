package com.alodiga.app.wallet.validate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;


public class ValidateAccountCode4Activity extends AppCompatActivity {

    private Button back, next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_code4_layout);
        next= findViewById(R.id.next);
        back=findViewById(R.id.backToLoginBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ValidateAccountCode4Activity.this, MainActivity.class);
                startActivity(i);
                finish();            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ValidateAccountCode4Activity.this, ValidateAccountActivity.class);
                startActivity(i);
                finish();            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ValidateAccountCode4Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
