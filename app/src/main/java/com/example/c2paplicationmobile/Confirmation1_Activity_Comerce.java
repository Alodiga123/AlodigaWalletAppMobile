package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Confirmation1_Activity_Comerce extends AppCompatActivity {
	private static View view;
	private static EditText amountValue, conceptValue;

	private static TextView txtAccountSourceValue, acountNumberValue,destinationPhoneValue,destinationLastNameValue,destinationNameValue;

	private static TextView login;
	private static Button btnProcessConfirmation1;
	private static CheckBox terms_conditions;
	private static Spinner spinnerCountry;
	static ProgressDialogAlodiga progressDialogAlodiga;
	private ObjCountry objCountry;
	private String responsetxt = "";
	private boolean serviceStatus;
	private String getAmountValue = "";
	private String getconceptValue = "";

	private String getTxtAccountNumberValue = "";
	private String getTxtDestinationPhoneValue = "";
	private String getTxtDestinationLastNameValue = "";
	private String getTxtDestinationNameValue = "";
	private Integer caseFindMoneyType = 0;


	public Confirmation1_Activity_Comerce() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation_transfer1_comerce);

		amountValue = (EditText) findViewById(R.id.txtAmountValue);
		conceptValue = (EditText) findViewById(R.id.txtConceptValue);
		acountNumberValue =  findViewById(R.id.txtAccountNumberValue);
		destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue);
		destinationLastNameValue = findViewById(R.id.txtDestinationLastNameValue);
		destinationNameValue = findViewById(R.id.txtDestinationNameValue);
		txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue);
		btnProcessConfirmation1 = findViewById(R.id.btnProcessConfirmation1);
		acountNumberValue.setText(Session.getDestinationAccountNumber());
		destinationPhoneValue.setText(Session.getDestinationPhoneValue());
		destinationLastNameValue.setText(Session.getDestinationLastNameValue());
		destinationNameValue.setText(Session.getDestinationNameValue());

		switch (Session.getMoneySelected()) {
			case 0:
				txtAccountSourceValue.setText("Saldo Alodiga / USD "+ Session.getAlodigaBalance());
				break;
			case 1:
				txtAccountSourceValue.setText("Saldo Alocoins / ALC "+ Session.getAlocoinsBalance());
				break;
			case 2:
				txtAccountSourceValue.setText("Tarjeta Alodiga / USD "+ Session.getHealthCareCoinsBalance());
				break;
		}





		btnProcessConfirmation1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(checkValidation()){
					Session.setDestinationConcept(conceptValue.getText().toString());
					Session.setGetDestinationAmount(amountValue.getText().toString());

					Intent i = new Intent(Confirmation1_Activity_Comerce.this, Confirmation2_Activity_Comerce.class);
					startActivity(i);
					finish();
				}
			}
		});

	}





	private boolean checkValidation() {
		// Check if all strings are null or not
		if (conceptValue.getText().toString().equals("")){
			new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
					"El campo concepto es requerido");

			return false;
		}if (amountValue.getText().toString().equals("")){
			new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
					"El campo monto es requerido");
			return false;
		}if (Float.valueOf(amountValue.getText().toString())<= 0){
			new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
					"El monto es invalido");
			return false;
		}
		return true;
	}




	public void procesar(){

		progressDialogAlodiga = new ProgressDialogAlodiga(getApplicationContext(),"cargando..");
		progressDialogAlodiga.show();


	//	mAuthTask = new ProcessOperationTransferenceTask(,getLastName,getEmailId,objCountry.getId(),getMobileNumber,getPassword);
	//	mAuthTask.execute((Void) null);

	}


}
