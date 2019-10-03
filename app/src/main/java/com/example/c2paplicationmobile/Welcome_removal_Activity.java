package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome_removal_Activity extends AppCompatActivity {
    private static TextView submit, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_manual_removal_layout);
        submit = (TextView) findViewById(R.id.step1_next_button);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);

            }
        });


    }
}
