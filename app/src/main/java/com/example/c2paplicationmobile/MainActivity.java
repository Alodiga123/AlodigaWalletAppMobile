package com.example.c2paplicationmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button btnPaymet;
    private Button btnViewTransaction;
    private Button btnChangePassword;
    Animation atg, atgtwo, atgthree;
    private RecyclerView mRecyclerView;
    private MoneyProductAdapter mAdapter;
    ImageView imageView3;
    private List<Money> mProductList;
    TextView nameuser, phoneuser,emailuser,pagetitle, pagesubtitle;


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
        mRecyclerView = (RecyclerView) findViewById(R.id.idRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        //Populate the products

        mProductList = new ArrayList<Money>();
        mProductList.add(new Money("Dolares",R.drawable.dolarsimbol,Session.getAccountNumber(), "Alodiga ", "USD "+ Session.getAlodigaBalance()));
        mProductList.add(new Money("Alocoins",R.drawable.alocoinlogo,"001035497978", "Alodiga", "AC " +Session.getAlocoinsBalance()));
        mProductList.add(new Money("Tarjeta",R.drawable.cardalodiga,"5412474313455121", "Alodiga", "ACD " +Session.getHealthCareCoinsBalance()));
        mProductList.add(new Money("Bolivares",R.drawable.bolivarsimbol,"0105****3626", "Alodiga", "BS 2"));
        mProductList.add(new Money("Monero",R.drawable.monero,"XMR", "Cripto", "XMR 0.63"));

        //set adapter to recyclerview
        mAdapter = new MoneyProductAdapter(mProductList,this);
        mRecyclerView.setAdapter(mAdapter);



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
    }

    @Override


    public void onBackPressed(){


        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Cerrar Sesion");
        dialogo1.setMessage("¿ Esta Seguro que desea cerrar la sesion?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent show = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(show);

            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Cerrar Sesion");
            dialogo1.setMessage("¿ Esta Seguro que desea cerrar la sesion?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                    Intent show = new Intent(MainActivity.this, LoginActivity.class);
                    finish();
                    startActivity(show);

                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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

        if (id == R.id.nav_home) {
            Intent show = new Intent(MainActivity.this, CreateQRCodeActivity.class);
            startActivity(show);
        } else if (id == R.id.nav_pay_invoice) {
            Intent show = new Intent(MainActivity.this, Change_Password_Activity.class);
            startActivity(show);
        } else if (id == R.id.nav_recharge) {
            Intent show = new Intent(MainActivity.this, List_Transaction_Activity.class);
            startActivity(show);
        } else if (id == R.id.nav_Withdrawal) {
            Intent show = new Intent(MainActivity.this, manual_removal_Activity.class);
            startActivity(show);
        } else if (id == R.id.nav_transfer) {
            Intent show = new Intent(MainActivity.this, Payment_Activity.class);
            startActivity(show);
        }
        else if (id == R.id.nav_pay_qr) {
            Intent show = new Intent(MainActivity.this, Payment_Activity_Comerce.class);
            startActivity(show);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
