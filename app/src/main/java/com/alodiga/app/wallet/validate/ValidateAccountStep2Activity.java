package com.alodiga.app.wallet.validate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;

import java.io.IOException;
import java.io.InputStream;


public class ValidateAccountStep2Activity extends AppCompatActivity {

    private Button take_photogaraphy,attach;

    private  int RESULT_LOAD_IMG = 0;
    private int REQUEST_CAMERA = 0;
    String imgDecodableString;
    private String userChoosenTask;
    private View View;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_step2_layout);
        take_photogaraphy= findViewById(R.id.take_photogaraphy);
        attach= findViewById(R.id.attach);

        take_photogaraphy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkExternalStoragePermission(true);
            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ValidateAccountStep2Activity.this, ValidateAccountActivity.class);
                startActivity(i);
                finish();
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

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Session.setSelectedImageSelfie(selectedImage);

                Bitmap prueba= Session.getSelectedImageSelfie();

                //imgDecodableString = encodeImage(selectedImage);
                //ImageView imgView = (ImageView) findViewById(R.id.imgCreateBit);

                //imgView.setImageBitmap(selectedImage);
                Intent show = new Intent(ValidateAccountStep2Activity.this, ValidateAccountStep3Activity.class);
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
            Session.setSelectedImageSelfie(imageBitmap);
        } else {
            Uri selectedimg = data.getData();
            try {
                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg);
                final Uri imageUri = data.getData();
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(imageUri.toString()));
                int width = mBitmap.getWidth();
                int height = mBitmap.getHeight();
                float scaleWidth = ((float) 300) / width;
                float scaleHeight = ((float) 400) / height;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                Bitmap bit_ = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
                Session.setSelectedImageSelfie(bit_);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent show = new Intent(ValidateAccountStep2Activity.this, ValidateAccountStep3Activity.class);
        startActivity(show);
        finish();

    }
}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ValidateAccountStep2Activity.this, ValidateAccountActivity.class);
        startActivity(i);
        finish();
    }
    public void checkExternalStoragePermission(Boolean isCamara) {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckCamara = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheckCamara != PackageManager.PERMISSION_GRANTED) {

            if (permissionCheck != PackageManager.PERMISSION_GRANTED)  {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        }
        if(permissionCheckCamara != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para de camara");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        }

        } else {
            Log.i("Mensaje", "Se tiene permiso para leer!");

            if(isCamara){
                cameraIntent();
            }else{
                galleryIntent();
            }
        }
    }
}
