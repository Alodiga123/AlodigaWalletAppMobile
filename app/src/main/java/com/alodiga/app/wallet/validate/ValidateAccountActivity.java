package com.alodiga.app.wallet.validate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alodiga.app.R;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.BipmapUtils;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;

import org.ksoap2.serialization.SoapObject;

import java.io.IOException;
import java.util.Locale;

import static com.alodiga.app.wallet.duallibrary.validate.ValidateAccountController.getKYC;


public class ValidateAccountActivity extends AppCompatActivity {

    private Button take_photogaraphy,attach;

    private  int RESULT_LOAD_IMG = 0;
    private int REQUEST_CAMERA = 0;
    String imgDecodableString;
    private String userChoosenTask;
    private View View;
    SoapObject response;
    static ProgressDialogAlodiga progressDialogAlodiga;
    ProcessTask mAuthTask;
    private String responsetxt = "";
    private boolean serviceStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_layout);
        take_photogaraphy= findViewById(R.id.take_photogaraphy);
        attach= findViewById(R.id.attach);

        entrar();

        take_photogaraphy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkExternalStoragePermission(true);
            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ValidateAccountActivity.this, MainActivity.class);
                startActivity(i);
                finish();              }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
         
            if (data.getExtras()!= null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                BipmapUtils.setSelectedImage(imageBitmap);
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
                    BipmapUtils.setSelectedImage(bit);

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

    public void entrar() {

        progressDialogAlodiga = new ProgressDialogAlodiga(this, getString(R.string.loading));
        progressDialogAlodiga.show();
        mAuthTask = new ProcessTask();
        mAuthTask.execute((Void) null);

    }
    public class ProcessTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                boolean availableBalance = true;

                final String idioma = Locale.getDefault().getLanguage();

                if (availableBalance) {
                    response = getKYC(idioma);
                    String responseCode = response.getProperty("codigoRespuesta").toString();
                    String responseMessage = response.getProperty("mensajeRespuesta").toString();

                    if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

                        serviceStatus = true;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS)) {
                        responsetxt = getString(R.string.web_services_response_01);
                        serviceStatus = false;

                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA)) {
                        responsetxt = getString(R.string.web_services_response_03);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA)) {
                        responsetxt = getString(R.string.web_services_response_04);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS)) {
                        responsetxt = getString(R.string.web_services_response_05);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO)) {
                        responsetxt = getString(R.string.web_services_response_06);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE)) {
                        responsetxt = getString(R.string.web_services_response_08);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_NULOS)) {
                        responsetxt = getString(R.string.web_services_response_11);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO)) {
                        responsetxt = getString(R.string.web_services_response_12);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_AMOUNT_LIMIT)) {
                        responsetxt = getString(R.string.web_services_response_30);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_ACCOUNT)) {
                        responsetxt = getString(R.string.web_services_response_31);
                        serviceStatus = false;

                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_CUSTOMER)) {
                        responsetxt = getString(R.string.web_services_response_32);
                        serviceStatus = false;

                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_HAS_NOT_BALANCE)) {
                        responsetxt = getString(R.string.web_services_response_33);
                        serviceStatus = false;

                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO)) {
                        responsetxt = getString(R.string.web_services_response_95);
                        serviceStatus = false;

                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE)) {
                        responsetxt = getString(R.string.web_services_response_96);
                        serviceStatus = false;

                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE)) {
                        responsetxt = getString(R.string.web_services_response_97);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES)) {
                        responsetxt = getString(R.string.web_services_response_98);
                        serviceStatus = false;
                    } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
                        responsetxt = getString(R.string.web_services_response_99);
                        serviceStatus = false;
                    } else {
                        responsetxt = responseMessage;
                        serviceStatus = false;
                    }
                } else {
                    responsetxt = getString(R.string.insuficient_balance);
                    serviceStatus = false;
                }
            } catch (IllegalArgumentException e) {
                responsetxt = getString(R.string.web_services_response_99);
                serviceStatus = false;
                e.printStackTrace();
                System.err.println(e);
                return false;
            } catch (Exception e) {
                responsetxt = getString(R.string.web_services_response_99);
                serviceStatus = false;
                e.printStackTrace();
                System.err.println(e);
                return false;
            }
            return serviceStatus;

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {

                LinearLayout ll=(LinearLayout) findViewById(R.id.layout);
                int prevTextViewId=0;
                    for (int i = 3; i < response.getPropertyCount(); i++) {
                        SoapObject obj = (SoapObject) response.getProperty(i);
                        String propiedad = response.getProperty(i).toString();

                        final TextView textView = new TextView(getApplicationContext());
                        int curTextViewId = prevTextViewId + 1;
                        textView.setText("- "+ obj.getProperty("name").toString());

                        textView.setId(curTextViewId);
                        textView.setTextSize(16);
                        textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
                        //textView.setPadding(12,1,1,1);
                        textView.setTextColor(Color.WHITE);
                        prevTextViewId = curTextViewId;
                        ll.addView(textView);
                    }


            } else {


                new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                        responsetxt);

            }
            progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }


    private static String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }
}
