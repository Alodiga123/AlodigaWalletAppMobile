package com.alodiga.app.wallet.validate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ValidateAccountStep8Activity extends AppCompatActivity {

    private Button finalize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_code8_layout);
        finalize= findViewById(R.id.finalize);

        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ValidateAccountStep8Activity.this, MainActivity.class);
                startActivity(i);
                finish();            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ValidateAccountStep8Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
