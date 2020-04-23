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

import java.sql.Timestamp;
import java.util.Date;


public class RechargeWithCardStep5Activity extends AppCompatActivity {

    private static Spinner spinnerProduct;
    private Button btnShareInformation, backToLoginBtn;
    private TextView card,cardholder,cvv,card_type, date_recharge_expired, product,amount,number_trans,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_with_card_succesfull);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        btnShareInformation= findViewById(R.id.btnShareInformation);
        card= findViewById(R.id.card);
        cardholder= findViewById(R.id.cardholder);
        cvv= findViewById(R.id.cvv);
        card_type= findViewById(R.id.card_type);
        date_recharge_expired= findViewById(R.id.date_recharge);
        product= findViewById(R.id.product);
        amount= findViewById(R.id.amount);

        number_trans= findViewById(R.id.number_trans);
        date=findViewById(R.id.date);


        /*card.setText(Session.getTarjetahabienteSelect().getCard_number());
        cardholder.setText(Session.getTarjetahabienteSelect().getCardholder_name());
        cvv.setText(Session.getTarjetahabienteSelect().getSecurity_code());
        card_type.setText(Session.getTarjetahabienteSelect().getType_card());
        date_recharge_expired.setText(Session.getTarjetahabienteSelect().getExpiration_date_moth()+" / "+Session.getTarjetahabienteSelect().getExpiration_date_year());
        product.setText(Session.getTarjetahabienteSelect().getProduct().getName());
        amount.setText(Session.getTarjetahabienteSelect().getAmount());*/

        card.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumber());
        cardholder.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
        cvv.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardCVV());
        card_type.setText(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardTypeId().getName());
        date_recharge_expired.setText(Session.getTarjetahabienteSelect().getCardInfo().getMonth()+" / "+Session.getTarjetahabienteSelect().getCardInfo().getYear());
        product.setText(Session.getTarjetahabienteSelect().getProduct().getName().split("-")[0]);
        amount.setText(Session.getTarjetahabienteSelect().getAmount());

        number_trans.setText(Session.getRechargeWhitCardIdTransaccion());
        date.setText(new Timestamp(new Date().getTime()).toGMTString());



        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.confirmation_title_successfull_Alodiga)+
                        "\n" + getString(R.string.destination_operatión_resum)+" - "+getString(R.string.recharge_whih_card_title)+
                        "\n" + "**********"+
                        "\n" + getString(R.string.number_trans)+": "+ number_trans.getText() +
                        "\n" + getString(R.string.destination_date_time)+" "  +date.getText()+
                        "\n" + "**********"+
                        "\n" + getString(R.string.payment_method_information)+
                        "\n" + getString(R.string.balance_card_number)+": " + card.getText() +
                        "\n" + getString(R.string.cardholder)+": " + cardholder.getText() +
                        "\n" + getString(R.string.cvv_)+": " + cvv.getText() +
                        "\n" + getString(R.string.card_type_)+": " + card_type.getText() +
                        "\n" + getString(R.string.date_recharge_)+": " + date_recharge_expired.getText() +
                        "\n" + "**********"+
                        "\n" + getString(R.string.payment_method_information_recharge)+
                        "\n" + getString(R.string.product)+": " + product.getText()+
                        "\n" + getString(R.string.amount)+": " + amount.getText());
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RechargeWithCardStep5Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
