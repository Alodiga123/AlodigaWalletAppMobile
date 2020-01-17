package com.alodiga.app.wallet.companionCards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.AdapterCardProduct;
import com.alodiga.app.wallet.adapters.AdapterMoneyProduct;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjMoney;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.utils.Session;

import java.util.ArrayList;
import java.util.List;


public class CompanionCardsStep1Activity extends AppCompatActivity {

    private static Spinner spinnerProduct;
    private Button step1_next_button, backToLoginBtn;
    private RecyclerView mRecyclerView;
    private AdapterCardProduct mAdapter;
    private List<ObjMoney> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_card);
        backToLoginBtn= findViewById(R.id.backToLoginBtn);


        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), "Prueba exitosa");
                Intent show;
                show = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(show);
            }
        });




        mRecyclerView = findViewById(R.id.idRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductList = new ArrayList<ObjMoney>();

        for (ObjUserHasProduct objUserHasProduct : Session.getObjUserHasProducts()) {
            int resID = getResources().getIdentifier(objUserHasProduct.getSymbol().toLowerCase() , "drawable", getPackageName());
            mProductList.add(new ObjMoney(objUserHasProduct.getName(),resID, objUserHasProduct.getNumberCard(), objUserHasProduct.getNumberCard().substring(0,4) + "*********" + objUserHasProduct.getNumberCard().substring(objUserHasProduct.getNumberCard().length()-4,objUserHasProduct.getNumberCard().length()), "Alodiga ", objUserHasProduct.getSymbol() + " " + objUserHasProduct.getCurrentBalance(),Boolean.parseBoolean(objUserHasProduct.getIsTopUp())));

        }

            //set adapter to recyclerview
        mAdapter = new AdapterCardProduct(mProductList, this);
        mRecyclerView.setAdapter(mAdapter);
        // Registro el ListView para que tenga menú contextual.
        registerForContextMenu(mRecyclerView);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CompanionCardsStep1Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
