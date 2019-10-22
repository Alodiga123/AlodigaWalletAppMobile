package com.alodiga.app.wallet.topup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;

import java.sql.Timestamp;
import java.util.Date;


public class TopupStep3Activity extends AppCompatActivity  {

    private Button btnProcess,btnShareInformation;
    private static Spinner spinnerProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup_step3_layout);
        btnProcess= findViewById(R.id.btnProcess);
        btnShareInformation= findViewById(R.id.btnShareInformation);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
            }
        });

        btnShareInformation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.confirmation_title_successfull_Alodiga) +" \n" +
                        "\n" + getString(R.string.number_trans) + new Date().getTime() + "\n" + getString(R.string.operator_text)+" " + Session.getOperatorTopup() + "\n" +  getString(R.string.register_phone_number_message_dest) +" "+ Session.getNumberDestinationTopup() + "\n" + getString(R.string.register_phone_number_message_rem) +" " + Session.getPhonenumberTopup() + "\n" +
                        getString(R.string.location)+" "+ Session.getCountryTopup() + "\n" + getString(R.string.destination_date_time)+" " + new Timestamp(new Date().getTime()).toGMTString());
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
            }
        });



    }



}
