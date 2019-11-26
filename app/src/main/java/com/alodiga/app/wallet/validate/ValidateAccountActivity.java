package com.alodiga.app.wallet.validate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class ValidateAccountActivity extends Activity {

    private Button take_photogaraphy,attach;

    private  int RESULT_LOAD_IMG = 0;
    private int REQUEST_CAMERA = 0;
    String imgDecodableString;
    private String userChoosenTask;
    private View View;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_layout);
        take_photogaraphy= findViewById(R.id.take_photogaraphy);
        attach= findViewById(R.id.attach);

        take_photogaraphy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraIntent();
            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 galleryIntent();
            }
        });

    }

    //Metodo de la camara
    private void cameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Nos da la captura de la camara
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    private void galleryIntent(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                Session.setSelectedImage(selectedImage);

                Intent show = new Intent(ValidateAccountActivity.this, ValidateAccountStep1Activity.class);
                startActivity(show);
                finish();


            } else {

                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.kyc_text_image));

            }
        } catch (Exception e) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.web_services_response_99));

        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //addPhoto.setImageBitmap(imageBitmap);
            if (data.getExtras()!= null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Session.setSelectedImage(imageBitmap);
            } else {
                Uri selectedimg = data.getData();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg);
                    int width = mBitmap.getWidth();
                    int height = mBitmap.getHeight();
                    float scaleWidth = ((float) 300) / width;
                    float scaleHeight = ((float) 400) / height;
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    Bitmap bit = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
                    Session.setSelectedImage(bit);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Intent show = new Intent(ValidateAccountActivity.this, ValidateAccountStep1Activity.class);
            startActivity(show);
            finish();

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ValidateAccountActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }



}
