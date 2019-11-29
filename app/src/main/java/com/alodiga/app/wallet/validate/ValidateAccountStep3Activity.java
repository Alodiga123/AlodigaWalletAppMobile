package com.alodiga.app.wallet.validate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.utils.Session;


public class ValidateAccountStep3Activity extends Activity {

    private Button back,next;
    private ImageView imgCamare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_step3_layout);
        next= findViewById(R.id.next);
        back= findViewById(R.id.backToLoginBtn);
        imgCamare = findViewById(R.id.imgCamare);

        //imageView2= (ImageView) findViewById(R.id.imageView2);
        Bitmap prueba_= Session.getSelectedImageSelfie();
        imgCamare.setImageBitmap(prueba_);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ValidateAccountStep3Activity.this, ValidateAccountStep4Activity.class);
                startActivity(i);
                finish();            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //galleryIntent();
                Intent i = new Intent(ValidateAccountStep3Activity.this, ValidateAccountStep2Activity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ValidateAccountStep3Activity.this, ValidateAccountStep2Activity.class);
        startActivity(i);
        finish();
    }

}
