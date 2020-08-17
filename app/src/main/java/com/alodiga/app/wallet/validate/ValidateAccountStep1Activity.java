package com.alodiga.app.wallet.validate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.utils.BipmapUtils;


public class ValidateAccountStep1Activity extends AppCompatActivity {

    private Button back,next;
    private ImageView imgCamare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_step1_layout);
        next= findViewById(R.id.next);
        back= findViewById(R.id.backToLoginBtn);
        imgCamare = findViewById(R.id.imgCamare);

        Bitmap prueba= BipmapUtils.getSelectedImage();
        imgCamare.setImageBitmap(prueba);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ValidateAccountStep1Activity.this, ValidateAccountStep2Activity.class);
                startActivity(i);
                finish();            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //galleryIntent();
                Intent i = new Intent(ValidateAccountStep1Activity.this, ValidateAccountActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ValidateAccountStep1Activity.this, ValidateAccountActivity.class);
        startActivity(i);
        finish();
    }

}
