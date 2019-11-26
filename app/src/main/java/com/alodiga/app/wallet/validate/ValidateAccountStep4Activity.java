package com.alodiga.app.wallet.validate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;


public class ValidateAccountStep4Activity extends AppCompatActivity {

    private Button back,next;

    private EditText edtstate,edtcity , edtcode, edtAv;
    private String getedtstate,getedtcity , getedtcode, getedtAv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_step4_layout);
        next= findViewById(R.id.next);
        back= findViewById(R.id.backToLoginBtn);
        edtstate= findViewById(R.id.edtstate);
        edtcity= findViewById(R.id.edtcity);
        edtcode= findViewById(R.id.edtcode);
        edtAv= findViewById(R.id.edtAv);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //galleryIntent();
                Intent i = new Intent(ValidateAccountStep4Activity.this, ValidateAccountStep2Activity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ValidateAccountStep4Activity.this, ValidateAccountStep2Activity.class);
        startActivity(i);
        finish();
    }

    public void validate(){
        getedtstate= edtstate.getText().toString();
        getedtcity= edtcity.getText().toString();
        getedtcode= edtcode.getText().toString();
        getedtAv= edtAv.getText().toString();

        if(getedtstate.equals("") || getedtstate.length() == 0
                || getedtcity.equals("") || getedtcity.length() == 0
                ||getedtcode.equals("") || getedtcode.length() == 0
                ||getedtAv.equals("") || getedtAv.length() == 0 ){

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.invalid_all_question));

        }else{

            Intent i = new Intent(ValidateAccountStep4Activity.this, ValidateAccountStep5Activity.class);
            startActivity(i);
            finish();
        }

    }

}
