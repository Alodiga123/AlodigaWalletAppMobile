package com.alodiga.app.wallet.splash;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.R;


public class SplashActivity extends AppCompatActivity {
    Dialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() == null
                || !conMgr.getActiveNetworkInfo().isAvailable()
                || !conMgr.getActiveNetworkInfo().isConnected()) {
            Intent i = new Intent(SplashActivity.this, FailConnectionSplashActivity.class);
            startActivity(i);
            finish();
        } else
            timer();
        //timer();
    }


    protected void timer() {
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                //nothing
            }
            public void onFinish() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }
}