package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Transference_Confirmation1_Activity extends AppCompatActivity {
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


	public Transference_Confirmation1_Activity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transference_confirmation_transfer1);

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
		//String hola=Session.getMoneySelected().getName();
        txtAccountSourceValue.setText(Session.getMoneySelected().getName());

		/*switch (Session.getMoneySelected()) {
			case 0:
				txtAccountSourceValue.setText("Saldo Alodiga / USD "+ Session.getAlodigaBalance());
				break;
			case 1:
				txtAccountSourceValue.setText("Saldo Alocoins / ALC "+ Session.getAlocoinsBalance());
				break;
			case 2:
				txtAccountSourceValue.setText("Tarjeta Alodiga / USD "+ Session.getHealthCareCoinsBalance());
				break;
		}*/





		btnProcessConfirmation1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(checkValidation()){
					Session.setDestinationConcept(conceptValue.getText().toString());
					Session.setGetDestinationAmount(amountValue.getText().toString());

					//Intent i = new Intent(Confirmation1_Activity.this, Confirmation2_Activity.class);
					//startActivity(i);
					//String saldo_a= Session.getMoneySelected().getCurrentBalance();
					//String monto_=amountValue.getText().toString().trim();
					//float saldo = Float.parseFloat(Session.getMoneySelected().getCurrentBalance().trim());
					//float montodebitar=	Float.parseFloat(amountValue.getText().toString().trim());
                    if (Float.parseFloat(Session.getMoneySelected().getCurrentBalance().trim()) < Float.parseFloat(amountValue.getText().toString().trim())){
                        new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                                getString(R.string.insuficient_balance));
                    }else{
					Intent i = new Intent(Transference_Confirmation1_Activity.this, Payment_transference_code_Activity.class);
					startActivity(i);
					finish();
                    }
				}
			}
		});


        amountValue.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().matches("^\\ (\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$"))
                {
                    String userInput= ""+s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length()-2, '.');
                    cashAmountBuilder.insert(0, ' ');

                    amountValue.setText(cashAmountBuilder.toString());
                    // keeps the cursor always to the right
                    Selection.setSelection(amountValue.getText(), cashAmountBuilder.toString().length());

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
