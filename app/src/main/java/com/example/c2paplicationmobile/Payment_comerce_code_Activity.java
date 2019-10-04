package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Payment_comerce_code_Activity extends AppCompatActivity {
    private TextView backToLoginBtn, step1_next_button,tvintentos;
    private EditText edtMobileCode;
    static int cout=1;
    static int cout_aux=3;

    String clave= "1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_code_layout);
        step1_next_button= findViewById(R.id.step1_next_button);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);
        edtMobileCode= findViewById(R.id.edtMobileCode);
        tvintentos= findViewById(R.id.tvintentos);
        /*backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Payment_comerce_code_Activity.this, Confirmation1_Activity.class);
                startActivity(i);
            }
        });*/

        step1_next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String getCode= edtMobileCode.getText().toString();

               if(cout<=3){

                if (getCode.equals("") || getCode.length() == 0) {
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            getString(R.string.pin_text));
                }else if(getCode.equals(clave)){
                    Session.setCodeOperation(edtMobileCode.getText().toString());
                    Intent i = new Intent(Payment_comerce_code_Activity.this, Confirmation2_Activity.class);
                    startActivity(i);
                }else{
                    /*new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            "clave invalida. Intento " +cout+ "/3");*/
                    edtMobileCode.setText("");
                    cout_aux=cout_aux-1;
                    tvintentos.setText( getString(R.string.info_fail_code) + cout_aux);
                    cout=cout+1;
                    if (cout_aux==0){
                        Intent i = new Intent(Payment_comerce_code_Activity.this, fail_code_Activity.class);
                        startActivity(i);
                    }
                }
                }else{
                   Intent i = new Intent(Payment_comerce_code_Activity.this, fail_code_Activity.class);
                   startActivity(i);

               }
            }
        });
    }
}
