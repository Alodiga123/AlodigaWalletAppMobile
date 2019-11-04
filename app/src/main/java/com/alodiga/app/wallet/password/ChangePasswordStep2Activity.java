package com.alodiga.app.wallet.password;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;


/**
 * Created by anyeli on 03/07/17.
 */

public class ChangePasswordStep2Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_step2_succesfull_layout);
        final TextView next = (TextView) findViewById(R.id.step1_next_button);
        //final TextView back = (TextView) findViewById(R.id.backToLoginBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent newFormsi2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(newFormsi2);
                finish();
            }
        });

      /*  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent newFormsi2 = new Intent(getApplicationContext(), ChangePasswordStep1Activity.class);
                startActivity(newFormsi2);
                finish();
            }
        });*/

    }
   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ChangePasswordStep2Activity.this, ChangePasswordStep1Activity.class);
        startActivity(i);
        finish();
    }*/
}
