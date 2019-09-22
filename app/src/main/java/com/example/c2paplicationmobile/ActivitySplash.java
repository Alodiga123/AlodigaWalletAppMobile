package com.example.c2paplicationmobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;


public class ActivitySplash extends AppCompatActivity {
    Dialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        timer();
    }


    protected void timer() {
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                //nothing
            }
            public void onFinish() {
                Intent i = new Intent(ActivitySplash.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }
}