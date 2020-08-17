package com.alodiga.app.wallet.reloadCard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;

public class ReloadCardStep3Activity extends AppCompatActivity {
    private static TextView amountValue, conceptValue;
    private static TextView txtTransactionId_3, txtAccountSourceValue, txtDateTimeValue_3, acountNumberValue, destinationPhoneValue, destinationLastNameValue, destinationNameValue, txtAmountValue, txtConceptValue;
    private static Button btnProcessFinisTransference;
    private static Button btnShareInformation;
    private ProgressDialogAlodiga progressDialogAlodiga;
    private String responsetxt = "";
    private boolean serviceStatus;


    public ReloadCardStep3Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reloadcard_step3_layout);
    }



    @Override
    public void onBackPressed() {
        Intent show = new Intent(ReloadCardStep3Activity.this, MainActivity.class);
        finish();
        startActivity(show);

    }
}
