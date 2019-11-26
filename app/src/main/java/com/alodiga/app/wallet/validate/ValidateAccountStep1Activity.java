package com.alodiga.app.wallet.validate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.Session;

import java.io.InputStream;


public class ValidateAccountStep1Activity extends Activity {

    private Button back,next;
    private ImageView imgCamare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_step1_layout);
        next= findViewById(R.id.next);
        back= findViewById(R.id.backToLoginBtn);
        imgCamare = findViewById(R.id.imgCamare);

        //imageView2= (ImageView) findViewById(R.id.imageView2);
        Bitmap prueba= Session.getSelectedImage();
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
