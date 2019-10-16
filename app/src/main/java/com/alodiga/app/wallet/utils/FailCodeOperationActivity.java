package com.alodiga.app.wallet.utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;

public class FailCodeOperationActivity extends AppCompatActivity {
    private TextView  step1_next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fail_code_operation_layout);
        step1_next_button= findViewById(R.id.step1_next_button);
        step1_next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    finish();
                    Intent i = new Intent(FailCodeOperationActivity.this, MainActivity.class);
                    startActivity(i);
            }
        });
    }
}
