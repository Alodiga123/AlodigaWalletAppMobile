package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class fail_code_Activity extends AppCompatActivity {
    private TextView  step1_next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fail_code_operation_layout);
        step1_next_button= findViewById(R.id.step1_next_button);
        step1_next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent i = new Intent(fail_code_Activity.this, MainActivity.class);
                    startActivity(i);
            }
        });
    }
}
