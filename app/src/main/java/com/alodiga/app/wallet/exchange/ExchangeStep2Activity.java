package com.alodiga.app.wallet.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.utils.Session;

public class ExchangeStep2Activity extends AppCompatActivity {
    TextView txtcommission,txtpercentage, txtdebitar, txtDestino, txtincludeM, rateSource, txtrateSource, rateDestination,txtrateDestination,txtAmount_ini;
    Button next, backToLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_step2_layout);
        next= findViewById(R.id.next);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);
        txtcommission=findViewById(R.id.txtcommission);
        txtpercentage=findViewById(R.id.txtpercentage);
        txtdebitar=findViewById(R.id.txtdebitar);
        txtDestino=findViewById(R.id.txtDestino);
        txtincludeM=findViewById(R.id.txtincludeM);
        rateSource=findViewById(R.id.rateSource);
        txtrateSource=findViewById(R.id.txtrateSource);
        rateDestination=findViewById(R.id.rateDestination);
        txtrateDestination=findViewById(R.id.txtrateDestination);
        txtAmount_ini=findViewById(R.id.txtAmount_ini);

        txtcommission.setText(Session.getExchange().getExange_amountCommission()+" "+Session.getExchange().getExange_productSource().getSymbol());
        txtpercentage.setText(Session.getExchange().getExange_valueCommission()+
                ((Session.getExchange().getExange_isPercentCommision().trim().equals("1"))?  " "+getString(R.string.percentage):" "+Session.getExchange().getExange_productSource().getSymbol()));
        txtdebitar.setText(Session.getExchange().getExange_totalDebit()+" "+Session.getExchange().getExange_productSource().getSymbol());
        txtincludeM.setText((Session.getExchange().getExange_includedAmount()=="1")?getString(R.string.yes):getString(R.string.no));
        rateSource.setText(Session.getExchange().getExange_productSource().getName().split("-")[0]);
        txtDestino.setText(Session.getExchange().getExange_amountConversion()+" "+Session.getExchange().getExange_productDestination().getSymbol());
        txtrateSource.setText(Session.getExchange().getExange_exchangeRateProductSource()+" "+getString(R.string.percentage));
        rateDestination.setText(Session.getExchange().getExange_productDestination().getName().split("-")[0]);
        txtrateDestination.setText(Session.getExchange().getExange_exchangeRateProductDestination() +" "+getString(R.string.percentage));
        txtAmount_ini.setText(Session.getExchange().getAmountExchange().trim()+" "+Session.getExchange().getExange_productSource().getSymbol());

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        Intent i = new Intent(ExchangeStep2Activity.this, ExchangeStep3codeActivity.class);
                        startActivity(i);
                         finish();


            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
                Intent i = new Intent(ExchangeStep2Activity.this, ExchangeStep1Activity.class);
                startActivity(i);


            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ExchangeStep2Activity.this, ExchangeStep1Activity.class);
        startActivity(i);
        finish();

    }
}
