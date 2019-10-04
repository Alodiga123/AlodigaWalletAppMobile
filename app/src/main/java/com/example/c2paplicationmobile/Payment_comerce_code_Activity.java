package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Payment_comerce_code_Activity extends AppCompatActivity {
    private TextView backToLoginBtn, step1_next_button;
    private EditText edtMobileCode;
    int cout=1;

    String clave= "1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_code_layout);
        step1_next_button= findViewById(R.id.step1_next_button);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);
        edtMobileCode= findViewById(R.id.edtMobileCode);

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
                }else if(edtMobileCode.equals(clave)){
                    Intent i = new Intent(Payment_comerce_code_Activity.this, Confirmation2_Activity.class);
                    startActivity(i);
                }else{
                    new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                            "clave invalida. Intento " +cout+ "/3");
                    cout=cout+1;

                }
                }
            }
        });
    }
}
