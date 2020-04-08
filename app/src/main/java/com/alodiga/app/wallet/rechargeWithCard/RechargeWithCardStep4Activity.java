package com.alodiga.app.wallet.rechargeWithCard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.Session;


public class RechargeWithCardStep4Activity extends AppCompatActivity {

    private static Spinner spinnerProduct;
    private Button btnProcessTransaction, backToLoginBtn;
    private TextView card,cardholder,cvv,card_type, date_recharge_expired, product,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_with_card_resume);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        btnProcessTransaction= findViewById(R.id.ProcessTransaction);
        card= findViewById(R.id.card);
        cardholder= findViewById(R.id.cardholder);
        cvv= findViewById(R.id.cvv);
        card_type= findViewById(R.id.card_type);
        date_recharge_expired= findViewById(R.id.date_recharge);
        product= findViewById(R.id.product);
        amount= findViewById(R.id.amount);


        card.setText(Session.getTarjetahabienteSelect().getCard_number());
        cardholder.setText(Session.getTarjetahabienteSelect().getCardholder_name());
        cvv.setText(Session.getTarjetahabienteSelect().getSecurity_code());
        card_type.setText(Session.getTarjetahabienteSelect().getType_card());
        date_recharge_expired.setText(Session.getTarjetahabienteSelect().getExpiration_date_moth()+" / "+Session.getTarjetahabienteSelect().getExpiration_date_year());
        product.setText(Session.getTarjetahabienteSelect().getProduct().getName());
        amount.setText(Session.getTarjetahabienteSelect().getAmount());



        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                Intent show;
                show = new Intent(getApplicationContext(), RechargeWithCardStep3CodeActivity.class);
                startActivity(show);
            }
        });


        btnProcessTransaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                Intent show;
                show = new Intent(RechargeWithCardStep4Activity.this, RechargeWithCardStep5Activity.class);
                startActivity(show);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RechargeWithCardStep4Activity.this, RechargeWithCardStep3CodeActivity.class);
        startActivity(i);
        finish();
    }

}
