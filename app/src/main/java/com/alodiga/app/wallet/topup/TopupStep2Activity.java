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
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;


public class TopupStep2Activity extends AppCompatActivity  {

    private Button next;
    private static Spinner spinnerProduct;
    //private ObjCountry objCountry;
    TextView range, product,operator;
    EditText number;
    //String tipo="1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup_step2_layout);
        next= findViewById(R.id.next);
        number= findViewById(R.id.number);
        product= findViewById(R.id.product);
        range= findViewById(R.id.range);
        operator= findViewById(R.id.operator);
        spinnerProduct= findViewById(R.id.spinnerProduct);
        product.setVisibility(View.INVISIBLE);
        spinnerProduct.setVisibility(View.INVISIBLE);
        range.setVisibility(View.INVISIBLE);
        number.setVisibility(View.INVISIBLE);




        if(Session.getTypeTopup().equals(Constants.TOPUP_DF)){
            operator.setText(Session.getDatosDenominacionFijaTopup()[0].getOpertador()+ " " + Session.getNumberDestinationTopup());

            product.setVisibility(View.VISIBLE);
            spinnerProduct.setVisibility(View.VISIBLE);

        }


        if(Session.getTypeTopup().equals(Constants.TOPUP_OR)){

            range.setVisibility(View.VISIBLE);
            number.setVisibility(View.VISIBLE);
            operator.setText(Session.getObjIsOpenRangeTopup().getOpertador()+ " " + Session.getNumberDestinationTopup());
            String range_info= getString(R.string.product_spinner)+" "+Session.getObjIsOpenRangeTopup().getMinimumAmount()
                    +getString(R.string.dolar_a)+ " "+Session.getObjIsOpenRangeTopup().getMaximumAmount()+getString(R.string.dolar_a)+" "+
                    getString(R.string.increment)+" "+Session.getObjIsOpenRangeTopup().getIncrement()+ getString(R.string.dolar)+":";

            range.setText(range_info);
            //Seteo de decimales al campo de monto
            number.addTextChangedListener(new TextWatcher() {
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

                        number.setText(cashAmountBuilder.toString());
                        // keeps the cursor always to the right
                        Selection.setSelection(number.getText(), cashAmountBuilder.toString().length());

                    }

                }
            });

        }


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(Session.getTypeTopup().equals(Constants.TOPUP_OR)){
                    float number_value=Float.parseFloat(number.getText().toString());
                    String number_st= number.getText().toString();
                    float max= Float.parseFloat(Session.getObjIsOpenRangeTopup().getMaximumAmount());
                    float min= Float.parseFloat(Session.getObjIsOpenRangeTopup().getMinimumAmount());

                    if (number_st.length()==0 || number_st.equals("")){
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.amount_info_invalid));

                    }else
                    if(number_value > max || number_value < min){
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.amount_req));

                    }else{
                        Session.setDestinationAmountTopup(number_st);
                        Intent show;
                        show = new Intent(getApplicationContext(), TopupStep3Activity.class);
                        startActivity(show);
                    }
                }

                if(Session.getTypeTopup().equals(Constants.TOPUP_DF)){


                }




            }
        });


    }



}
