package com.alodiga.app.wallet.exchange;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.utils.Session;

import java.sql.Timestamp;
import java.util.Date;

public class ExchangeStep4Activity extends AppCompatActivity {
    TextView txtTransactionId_3, txtdate,txtcommission,txtpercentage, txtdebitar, txtDestino, txtincludeM, rateSource, txtrateSource, rateDestination,txtrateDestination,txtAmount_ini;
    private  Button btnShareInformation,btnProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_step4_layout);
        txtdate=findViewById(R.id.txtdate);
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
        btnProcess= findViewById(R.id.btnProcess);
        btnShareInformation=findViewById(R.id.btnShareInformation);
        txtTransactionId_3= findViewById(R.id.txtTransactionId_3);

        txtTransactionId_3.setText(Session.getOperationExchange());
        txtcommission.setText(Session.getExchange().getExange_amountCommission()+" "+Session.getExchange().getExange_productSource().getSymbol());
        txtpercentage.setText(Session.getExchange().getExange_valueCommission()+
                ((Session.getExchange().getExange_isPercentCommision().trim().equals("1"))?  " "+getString(R.string.percentage):" "+Session.getExchange().getExange_productSource().getSymbol()));
        txtdebitar.setText(Session.getExchange().getExange_totalDebit()+" "+Session.getExchange().getExange_productSource().getSymbol());
        txtincludeM.setText((Session.getExchange().getExange_includedAmount()=="1")?getString(R.string.yes):getString(R.string.no));
        rateSource.setText(Session.getExchange().getExange_productSource().getName().split("-")[0]);
        txtDestino.setText(Session.getExchange().getExange_amountConversion()+" "+Session.getExchange().getExange_productDestination().getSymbol());
        txtrateSource.setText(Session.getExchange().getExange_exchangeRateProductSource());
        rateDestination.setText(Session.getExchange().getExange_productDestination().getName().split("-")[0]);
        txtrateDestination.setText(Session.getExchange().getExange_exchangeRateProductDestination() );
        txtAmount_ini.setText(Session.getExchange().getAmountExchange().trim()+" "+Session.getExchange().getExange_productSource().getSymbol());
        txtdate.setText(new Timestamp(new Date().getTime()).toGMTString());
        btnProcess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
                finish();

            }
        });

        btnShareInformation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //updateProduct();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.confirmation_title_successfull_Alodiga) +"\n"+
                        "\n" + getString(R.string.destination_date_time) + " " + new Timestamp(new Date().getTime()).toGMTString()+
                        "\n" + getString(R.string.number_trans) + Session.getOperationExchange() +
                        "\n" + getString(R.string.includeM) + " " + txtincludeM.getText().toString() +
                        "\n" + getString(R.string.commission) + " " + txtcommission.getText().toString() +
                        "\n" + getString(R.string.percentageRate) + " " + txtpercentage.getText().toString() +
                        "\n" + getString(R.string.a_ini) + " " + txtAmount_ini.getText().toString() +
                        "\n" + getString(R.string.debitTotal) + " " + txtdebitar.getText().toString() +
                        "\n" + getString(R.string.destinationA) + " " + txtDestino.getText().toString() +
                        "\n" + getString(R.string.table_title_exchange_rate) + ":" +
                        "\n" +   rateSource.getText().toString() + " : "+ txtrateSource.getText().toString() +
                        "\n" +   rateDestination.getText().toString() + " : "+ txtrateDestination.getText().toString());
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pasIntent = getIntent();
        Intent i = new Intent(ExchangeStep4Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
