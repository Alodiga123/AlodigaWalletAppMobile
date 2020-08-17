package com.alodiga.app.wallet.deactivateCard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;


public class DeactiveCardStep3Activity extends AppCompatActivity {

    private static Spinner spinnerProduct;
    private Button step1_next_button, backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deactivecard_step3_layout);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);



        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DeactiveCardStep3Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
