package com.example.c2paplicationmobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Change_Password_Activity_es extends AppCompatActivity {

    private static CheckBox show_hide_password;
    private static TextView forgotPassword, signUp;
    private static Button loginButton;
    private static EditText emailid, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password__es);
    }
}
