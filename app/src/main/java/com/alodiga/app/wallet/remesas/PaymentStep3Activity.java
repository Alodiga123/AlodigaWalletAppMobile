package com.alodiga.app.wallet.remesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.R;
import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.model.ObjRemittencePerson;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;

import org.ksoap2.serialization.SoapObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentStep3Activity extends AppCompatActivity {
    private static Button next, backToLoginBtn;
    private static EditText name, lastName, editTextTelephone, edtstate, edtcity, edtcode, edtAv, userEmailIdTransfer, secondname,secondSurmane;

    private static TextView info_estado, info_ciudad, location;
    private static Spinner spinner_pais, spinner_estado,spinner_ciudad ;
    private String responsetxt = "";
    private boolean serviceStatus;
    private static String stringResponse = "";
    String datosRespuesta = "";
    static SoapObject response_pais,response_estado,response_ciudad;
    static ObjGenericObject[] listSpinner_pais = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_estado = new ObjGenericObject[0];
    static ObjGenericObject[] listSpinner_ciudad = new ObjGenericObject[0];
    private Integer caseFind = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_step3_layout);
        next=findViewById(R.id.next);
        backToLoginBtn=findViewById(R.id.backToLoginBtn);
        userEmailIdTransfer=findViewById(R.id.userEmailIdTransfer);

        name= findViewById(R.id.name);
        secondname= findViewById(R.id.secondname);
        lastName= findViewById(R.id.lastName);
        secondSurmane= findViewById(R.id.secondSurmane);
        editTextTelephone= findViewById(R.id.editTextTelephone);


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate();
            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(Session.getRemettencesDireccionId().equals(Constants.REMITTENCE_ID)){
                    Intent pasIntent = getIntent();
                    Intent i = new Intent(PaymentStep3Activity.this, PaymentStep2Activity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(PaymentStep3Activity.this, PaymentStep1Activity.class);
                    startActivity(i);
                    finish();
                }

            }
        });


    }

    public void validate(){

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(userEmailIdTransfer.getText());

        String getname= name.getText().toString();
        String getsecondname = secondname.getText().toString();
        String getlastName= lastName.getText().toString();
        String getmidleName= secondSurmane.getText().toString();
        String geteditTextTelephone= editTextTelephone.getText().toString();

        String getuserEmailIdTransfer= userEmailIdTransfer.getText().toString();

        if( getname.equals("") || getname.length() == 0
                || getlastName.equals("") || getlastName.length() == 0
                || geteditTextTelephone.equals("") || geteditTextTelephone.length() == 0
                //|| ((spinner_ciudad.getVisibility()== View.INVISIBLE) && (getedtcity.equals("") || getedtcity.length() == 0))
                //|| ((spinner_estado.getVisibility() == View.INVISIBLE) && (getedtstate.equals("") || getedtstate.length() == 0 || getedtcity.equals("") || getedtcity.length() == 0))
                //|| (getedtcode.equals("") || getedtcode.length() == 0
                //||getedtAv.equals("") || getedtAv.length() == 0
                || getuserEmailIdTransfer.equals("") || getuserEmailIdTransfer.length() == 0
                || getsecondname.equals("") || getsecondname.length() == 0
                || getmidleName.equals("") || getmidleName.length() == 0){

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(), getString(R.string.invalid_all_question));

        }else if ((caseFind == 0) && (!m.find())) {

            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    getString(R.string.email_invalid));
        }else{

            ObjRemittencePerson remittenceDestinatario= new ObjRemittencePerson();
            remittenceDestinatario.setName(getname);
            remittenceDestinatario.setSecondname(getsecondname);
            remittenceDestinatario.setLastName(getlastName);
            remittenceDestinatario.setSecondSurmane(getmidleName);
            remittenceDestinatario.setEmail(getuserEmailIdTransfer);
            remittenceDestinatario.setTelephone(geteditTextTelephone);
            remittenceDestinatario.setLocation(Session.getPay().getDestination_country());


            Session.setRemittenceDestinatario(remittenceDestinatario);

            Intent pasIntent = getIntent();
            Intent i = new Intent(PaymentStep3Activity.this, PaymentStep3_2Activity.class);
            startActivity(i);
            finish();

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Session.getRemettencesDireccionId().equals(Constants.REMITTENCE_ID)){
            Intent pasIntent = getIntent();
            Intent i = new Intent(PaymentStep3Activity.this, PaymentStep2Activity.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(PaymentStep3Activity.this, PaymentStep1Activity.class);
            startActivity(i);
            finish();
        }
    }



}
