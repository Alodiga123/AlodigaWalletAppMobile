package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.AdapterCardContacts;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjTarjetahabiente;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;


public class RechargeWhithCarContactsSave extends AppCompatActivity {

    private Button backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.succefull_save_cards);
        backToLoginBtn = findViewById(R.id.step1_next_button);

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show;
                show = new Intent(getApplicationContext(), RechargeWhithCardContactsActivity.class);
                startActivity(show);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RechargeWhithCarContactsSave.this, RechargeWhithCardContactsActivity.class);
        startActivity(i);
        finish();
    }

}
