package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by anyeli on 27/09/17.
 */


public class ActivityFailConnection extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_connection);
        Button button = (Button) findViewById(R.id.btn_restart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityFailConnection.this, ActivitySplash.class);
                startActivity(i);
                finish();
            }
        });
    }
}
