package com.alodiga.app.wallet.paymentComerce;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.duallibrary.model.ObjCountry;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;

public class PaymentComerceStep3Activity extends AppCompatActivity {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static View view;
    private static EditText amountValue, conceptValue;
    private static TextView txtAccountSourceValue, acountNumberValue, destinationPhoneValue, destinationLastNameValue, destinationNameValue;
    private static Button btnProcessConfirmation1, backToLoginBtn;


    public PaymentComerceStep3Activity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_transfer1);

        amountValue = findViewById(R.id.txtAmountValue);
        conceptValue = findViewById(R.id.txtConceptValue);
        acountNumberValue = findViewById(R.id.txtAccountNumberValue);
        destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue);
        destinationLastNameValue = findViewById(R.id.txtDestinationLastNameValue);
        destinationNameValue = findViewById(R.id.txtDestinationNameValue);
        txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue);
        btnProcessConfirmation1 = findViewById(R.id.btnProcessConfirmation1);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);


        acountNumberValue.setText(Session.getDestinationAccountNumber());
        destinationPhoneValue.setText(Session.getDestinationPhoneValue());
        destinationLastNameValue.setText(Session.getDestinationLastNameValue());
        destinationNameValue.setText(Session.getDestinationNameValue());
        //String hola=Session.getMoneySelected().getName();
        txtAccountSourceValue.setText(Session.getMoneySelected().getName());

        btnProcessConfirmation1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkValidation()) {
                    Session.setDestinationConcept(conceptValue.getText().toString());
                    Session.setGetDestinationAmount(amountValue.getText().toString());

                    if (Float.parseFloat(Session.getMoneySelected().getCurrentBalance().trim()) < Float.parseFloat(amountValue.getText().toString().trim())) {
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.insuficient_balance));
                    } else {
                        Intent i = new Intent(PaymentComerceStep3Activity.this, PaymentComerceStep4codeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PaymentComerceStep3Activity.this, PaymentComerceStep1Activity.class);
                startActivity(i);
                finish();
            }
        });

        amountValue.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("^\\ (\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');
                    cashAmountBuilder.insert(0, ' ');

                    amountValue.setText(cashAmountBuilder.toString());
                    // keeps the cursor always to the right
                    Selection.setSelection(amountValue.getText(), cashAmountBuilder.toString().length());

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PaymentComerceStep3Activity.this, PaymentComerceStep1Activity.class);
        startActivity(i);
        finish();
    }

    private boolean checkValidation() {
        // Check if all strings are null or not
        if (conceptValue.getText().toString().equals("")) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.concept_req));

            return false;
        }
        if (amountValue.getText().toString().equals("")) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.amount_info_invalid));
            return false;
        }
        if (Float.valueOf(amountValue.getText().toString()) <= 0) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.amount_req));
            return false;
        }
        return true;
    }


    public void procesar() {

        progressDialogAlodiga = new ProgressDialogAlodiga(getApplicationContext(), getString(R.string.loading));
        progressDialogAlodiga.show();

    }


}
