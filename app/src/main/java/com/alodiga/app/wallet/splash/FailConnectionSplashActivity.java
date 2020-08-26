package com.alodiga.app.wallet.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;

public class FailConnectionSplashActivity extends AppCompatActivity {
    private Button button, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_connection);
        cancel= findViewById(R.id.cancel);
        button = findViewById(R.id.btn_restart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FailConnectionSplashActivity.this, SplashActivity.class);
                startActivity(i);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FailConnectionSplashActivity.this, SplashActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
