package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.CustomToast;

public class PaymentStep2Activity extends AppCompatActivity {
    private static Button next, backToLoginBtn;
    private static EditText name, lastName, editTextTelephone, location, edtstate, edtcity, edtcode, edtAv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step2_layout);
        next=findViewById(R.id.next);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);

        name= findViewById(R.id.name);
        lastName= findViewById(R.id.lastName);
        editTextTelephone= findViewById(R.id.editTextTelephone);
        location= findViewById(R.id.location);
        edtstate=findViewById(R.id.edtstate);
        edtcity=findViewById(R.id.edtcity);
        edtcode=findViewById(R.id.edtcode);
        edtAv=findViewById(R.id.edtAv);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate();
            }
        });



        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pasIntent = getIntent();
                Intent i = new Intent(PaymentStep2Activity.this, PaymentStep1Activity.class);
                startActivity(i);
                finish();
            }
        });


    }


    public void validate(){

        String getname= name.getText().toString();
        String getlastName= lastName.getText().toString();
        String geteditTextTelephone= editTextTelephone.getText().toString();
        String getlocation= location.getText().toString();
        String getedtstate= edtstate.getText().toString();
        String getedtcity= edtcity.getText().toString();
        String getedtcode= edtcode.getText().toString();
        String getedtAv= edtAv.getText().toString();

        if( getname.equals("") || getname.length() == 0
                || getlastName.equals("") || getlastName.length() == 0
                || geteditTextTelephone.equals("") || geteditTextTelephone.length() == 0
                || getlocation.equals("") || getlocation.length() == 0
                || getedtstate.equals("") || getedtstate.length() == 0
                || getedtcity.equals("") || getedtcity.length() == 0
                ||getedtcode.equals("") || getedtcode.length() == 0
                ||getedtAv.equals("") || getedtAv.length() == 0 ){

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.invalid_all_question));

        }else{

            Intent pasIntent = getIntent();
            Intent i = new Intent(PaymentStep2Activity.this, PaymentStep3Activity.class);
            startActivity(i);
            finish();

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(PaymentStep2Activity.this, PaymentStep1Activity.class);
        startActivity(i);
        finish();
    }



}
