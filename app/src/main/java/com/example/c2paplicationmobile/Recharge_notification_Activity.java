package com.example.c2paplicationmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Recharge_notification_Activity extends AppCompatActivity {

    private Spinner spinnerbank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_layout);
        initViews();

        //List<ObjGenericObject> bank = new ArrayList<ObjGenericObject>();
        ObjGenericObject[] objBank = new ObjGenericObject[3];
        objBank[0] = new ObjGenericObject("Banco","0");
        objBank[1] = new ObjGenericObject("Estados Unidos","1");
        objBank[2] = new ObjGenericObject("Venezuela","2");
        objBank[2] = new ObjGenericObject("Peru","3");

        SpinAdapterBank SpinAdapterBank;
        SpinAdapterBank = new SpinAdapterBank(this, android.R.layout.simple_spinner_item, objBank);
        spinnerbank.setAdapter(SpinAdapterBank);


    }


    // Initialize the views
    private void initViews() {
        spinnerbank= (Spinner)  findViewById(R.id.spinnerbank);
    }
}
