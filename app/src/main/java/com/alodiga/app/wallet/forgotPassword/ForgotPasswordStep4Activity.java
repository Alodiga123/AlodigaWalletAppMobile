package com.alodiga.app.wallet.forgotPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import com.alodiga.app.R;
import com.alodiga.app.wallet.login.LoginActivity;


public class ForgotPasswordStep4Activity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    private Button next;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword_step4_succesfull_layout);
         next = (Button) findViewById(R.id.step1_next_button);
        fragmentManager = getSupportFragmentManager();


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ForgotPasswordStep4Activity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

}
