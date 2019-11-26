package com.alodiga.app.wallet.main;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.QR.CreateQRCodeActivity;
import com.alodiga.app.wallet.adapters.AdapterMoneyProduct;
import com.alodiga.app.wallet.exchange.ExchangeStep1Activity;
import com.alodiga.app.wallet.listTransactionExecuted.ListTransactionExecutedActivity;
import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.wallet.manualRecharge.ManualRechargeStep1Activity;
import com.alodiga.app.wallet.manualRemoval.ManualRemovalStep1Activity;
import com.alodiga.app.wallet.model.ObjMoney;
import com.alodiga.app.wallet.model.ObjNewsItem;
import com.alodiga.app.wallet.model.ObjTransaction;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.changePassword.ChangePasswordStep1Activity;
import com.alodiga.app.wallet.paymentComerce.PaymentComerceStep1Activity;
import com.alodiga.app.wallet.topup.TopupStep1Activity;
import com.alodiga.app.wallet.transference.TransferenceStep1Activity;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.validate.ValidateAccountActivity;
import com.alodiga.app.wallet.validate.ValidateAccountCode3Activity;
import com.alodiga.app.wallet.validate.ValidateAccountCode4Activity;
import com.alodiga.app.wallet.validate.ValidateAccountStep5Activity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    Animation atg, atgtwo, atgthree;
    ImageView imageView3;
    TextView nameuser, phoneuser, emailuser, pagetitle, pagesubtitle;
    private Button btnPaymet;
    private Button btnViewTransaction;
    private Button btnChangePassword;
    private RecyclerView mRecyclerView;
    private AdapterMoneyProduct mAdapter;
    private List<ObjMoney> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);
        //getting the recyclerview from xml
        mRecyclerView = findViewById(R.id.idRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductList = new ArrayList<ObjMoney>();

        for (ObjUserHasProduct objUserHasProduct : Session.getObjUserHasProducts()) {
                int resID = getResources().getIdentifier(objUserHasProduct.getSymbol().toLowerCase() , "drawable", getPackageName());
                mProductList.add(new ObjMoney(objUserHasProduct.getName(),resID, Session.getAccountNumber(), "Alodiga ", objUserHasProduct.getSymbol() + " " + objUserHasProduct.getCurrentBalance(),Boolean.parseBoolean(objUserHasProduct.getIsTopUp())));
        }


        //Populate the products
        //mProductList = new ArrayList<ObjMoney>();
        /*mProductList.add(new ObjMoney("Dolares",R.drawable.dolarsimbol,Session.getAccountNumber(), "Alodiga ", "USD "+ Session.getAlodigaBalance()));
        mProductList.add(new ObjMoney("Alocoins",R.drawable.alocoinlogo,"001035497978", "Alodiga", "AC " +Session.getAlocoinsBalance()));
        mProductList.add(new ObjMoney("Tarjeta",R.drawable.cardalodiga,"5412474313455121", "Alodiga", "ACD " +Session.getHealthCareCoinsBalance()));
        mProductList.add(new ObjMoney("Bolivares",R.drawable.bolivarsimbol,"0105****3626", "Alodiga", "BS " +Session.getAlodigaBalance()));
        mProductList.add(new ObjMoney("Monero",R.drawable.monero,"XMR", "Cripto", "XMR " +Session.getAlocoinsBalance()));*/

        //set adapter to recyclerview
        mAdapter = new AdapterMoneyProduct(mProductList, this);
        mRecyclerView.setAdapter(mAdapter);
        // Registro el ListView para que tenga menú contextual.
        registerForContextMenu(mRecyclerView);

        emailuser = findViewById(R.id.emailuser);
        nameuser = findViewById(R.id.nameuser);
        phoneuser = findViewById(R.id.phoneuser);


        //insertValueSession
        emailuser.setText(Session.getEmail());
        nameuser.setText(Session.getUsername());
        phoneuser.setText(Session.getPhoneNumber());


        imageView3 = findViewById(R.id.imageView2);
        /*review = findViewById(R.id.review);
        network = findViewById(R.id.network);
        plugins = findViewById(R.id.plugins);
        myapps = findViewById(R.id.myapps);*/


        pagetitle = findViewById(R.id.pagetitle);
        pagesubtitle = findViewById(R.id.pagesubtitle);

       /* btnguide = findViewById(R.id.btntake);

        btnguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,PackageAct.class);
                startActivity(a);
            }
        });*/

        // pass an animation
        imageView3.startAnimation(atg);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this,R.style.yourDialog);
        dialogo1.setTitle(getString(R.string.close_session));
        dialogo1.setMessage(getString(R.string.close_session_answer));
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent show = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(show);

            }
        });
        dialogo1.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        dialogo1.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this, R.style.yourDialog);
            dialogo1.setTitle(R.string.close_session);
            dialogo1.setMessage(R.string.close_session_answer);
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Session.clearALL();
                    Intent show = new Intent(MainActivity.this, LoginActivity.class);
                    finish();
                    startActivity(show);

                }
            });
            dialogo1.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //cancelar();
                }
            });
            dialogo1.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_recharge || id == R.id.nav_Withdrawal || id == R.id.nav_transfer
               || id == R.id.nav_last_activity || id == R.id.nav_pay_qr
               || id == R.id.nav_topup || id == R.id.nav_convert){
           Intent show;
           switch(Session.getCumplimient()) {
               case "1":
                   show = new Intent(MainActivity.this, ValidateAccountStep5Activity.class);
                   startActivity(show);
                   break;
               case "3":
                   show = new Intent(MainActivity.this, ValidateAccountCode3Activity.class);
                   startActivity(show);
                   break;
               case "4":
                   show = new Intent(MainActivity.this, ValidateAccountCode4Activity.class);
                   startActivity(show);
                   break;
               default:
                   if (id == R.id.nav_recharge) {
                       show = new Intent(MainActivity.this, ManualRechargeStep1Activity.class);
                       startActivity(show);
                   } else if (id == R.id.nav_Withdrawal) {
                       show = new Intent(MainActivity.this, ManualRemovalStep1Activity.class);
                       startActivity(show);
                   } else if (id == R.id.nav_transfer) {
                       show = new Intent(MainActivity.this, TransferenceStep1Activity.class);
                       startActivity(show);
                   } else if (id == R.id.nav_last_activity) {
                        show = new Intent(MainActivity.this, ListTransactionExecutedActivity.class);
                       startActivity(show);
                   } else if (id == R.id.nav_pay_qr) {
                       show = new Intent(MainActivity.this, PaymentComerceStep1Activity.class);
                       startActivity(show);
                   } else if (id == R.id.nav_topup) {
                       show = new Intent(MainActivity.this, TopupStep1Activity.class);
                       startActivity(show);

                   }else if (id == R.id.nav_convert) {
                       show = new Intent(MainActivity.this, ExchangeStep1Activity.class);
                       startActivity(show);
                   }
       }

       }else if (id == R.id.nav_home) {
           Intent show = new Intent(MainActivity.this, CreateQRCodeActivity.class);
           startActivity(show);

        }else if (id == R.id.nav_change_password) {
            Intent show = new Intent(MainActivity.this, ChangePasswordStep1Activity.class);
            startActivity(show);

        }else if (id == R.id.nav_validate_account) {
            Intent show = new Intent(MainActivity.this, ValidateAccountActivity.class);
            startActivity(show);
        } else if (id == R.id.nav_close_session) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this, R.style.yourDialog);
            dialogo1.setTitle(R.string.close_session);
            dialogo1.setMessage(R.string.close_session_answer);
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Session.clearALL();
                    Intent show = new Intent(MainActivity.this, LoginActivity.class);
                    finish();
                    startActivity(show);

                }
            });
            dialogo1.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //cancelar();
                }
            });
            dialogo1.show();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
