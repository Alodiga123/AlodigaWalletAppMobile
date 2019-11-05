package com.alodiga.app.wallet.changePassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;


public class ChangePasswordStep2Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_step2_succesfull_layout);
        final TextView next = (TextView) findViewById(R.id.step1_next_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent newFormsi2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(newFormsi2);
                finish();
            }
        });

    }

}
