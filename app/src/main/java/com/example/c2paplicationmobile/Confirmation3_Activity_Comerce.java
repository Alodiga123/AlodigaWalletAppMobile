package com.example.c2paplicationmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.serialization.SoapObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

public class Confirmation3_Activity_Comerce extends AppCompatActivity {
	private static View view;
	private static TextView amountValue, conceptValue;




	private static TextView txtTransactionId_3,txtAccountSourceValue,txtDateTimeValue_3, acountNumberValue,destinationPhoneValue,destinationLastNameValue,destinationNameValue, txtAmountValue
	,txtConceptValue;

	private static TextView login;
	private static Button btnProcessFinisTransference;
	private static CheckBox terms_conditions;
	private static Spinner spinnerCountry;

	private ProgressDialogAlodiga progressDialogAlodiga;
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




	private ProcessOperationTransferenceTask mAuthTask = null;

	public Confirmation3_Activity_Comerce() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation_transfer3_succesfull_comerce);

		amountValue =  findViewById(R.id.txtAmountValue_3);
		conceptValue =  findViewById(R.id.txtConceptValue_3);
		acountNumberValue =  findViewById(R.id.txtAccountNumberValue_3);
		destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue_3);
		destinationLastNameValue = findViewById(R.id.txtDestinationLastNameValue_3);
		destinationNameValue = findViewById(R.id.txtDestinationNameValue_3);
		btnProcessFinisTransference = findViewById(R.id.btnProcessFinisTransference);
		txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue_3);
		txtTransactionId_3 = findViewById(R.id.txtTransactionId_3);
		txtDateTimeValue_3 = findViewById(R.id.txtDateTimeValue_3);
		acountNumberValue.setText(Session.getDestinationAccountNumber());
		destinationPhoneValue.setText(Session.getDestinationPhoneValue());
		destinationLastNameValue.setText(Session.getDestinationLastNameValue());
		destinationNameValue.setText(Session.getDestinationNameValue());
		conceptValue.setText(Session.getDestinationConcept());
		amountValue.setText(Session.getGetDestinationAmount());
		txtTransactionId_3.setText(String.valueOf(new Date().getTime()));
		txtDateTimeValue_3.setText(new Timestamp(new Date().getTime()).toGMTString());
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
		progressDialogAlodiga = new ProgressDialogAlodiga(this, "Cargando..");
		btnProcessFinisTransference.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Confirmation3_Activity_Comerce.this, MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
				finish();



			}
		});
	}

	public void procesar(){

		mAuthTask = new ProcessOperationTransferenceTask(Session.getUserId(),Session.getUsuarioDestionId(),Session.getGetDestinationAmount(),Session.getDestinationConcept());
		mAuthTask.execute((Void) null);

	}

	public class ProcessOperationTransferenceTask extends AsyncTask<Void, Void, Boolean> {

		private final String userId1;
		private final String userId2;
		private final String amount;
		private final String concept;



		ProcessOperationTransferenceTask(String userId1_, String userId2_, String amount_, String concept_) {
			userId1 = userId1_;
			userId2 = userId2_;
			amount = amount_;
			concept = concept_;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			WebService webService = new WebService();
			Utils utils = new Utils();
			SoapObject response;
			try {

				boolean availableBalance = true;
				String responseCode;
				String responseMessage = "";
				String methodName = "";

				/*switch (Session.getMoneySelected().getId()) {

					case "0":

						if(Float.valueOf(amount)>Float.valueOf(Session.getAlodigaBalance())){
							availableBalance = false;
						}
						methodName = "transferirSaldoAlodiga";
						break;

					case "1":

						if(Float.valueOf(amount)>Float.valueOf(Session.getAlocoinsBalance())){
							availableBalance = false;
						}
						methodName = "transferirAlocoins";
						break;
					case "2":
						if(Float.valueOf(amount)>Float.valueOf(Session.getHealthCareCoinsBalance())){
							availableBalance = false;
						}
						methodName = "transferirSaldoHealthCareCoins";
						break;

					default:
						break;
				}*/


				if(availableBalance){
					HashMap<String,String > map = new HashMap<String,String>();
					map.put("usuarioId1",userId1);
					map.put("usuarioId2",userId2);
					map.put("usuarioApi",Constants.WEB_SERVICES_USUARIOWS);
					map.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
					map.put("saldoAlodiga",amount);
					response = webService.invokeGetAutoConfigString(map,methodName,Constants.REGISTRO_UNIFICADO);
					responseCode = response.getProperty("codigoRespuesta").toString();
					responseMessage = response.getProperty("mensajeRespuesta").toString();

					if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
					{

						serviceStatus = true;
					}
					else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS))
					{
						responsetxt = getString(R.string.web_services_response_01);
						serviceStatus = false;

					} else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA))
					{
						responsetxt = getString(R.string.web_services_response_03);
						serviceStatus = false;
					}
					else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA))
					{
						responsetxt = getString(R.string.web_services_response_04);
						serviceStatus = false;
					}
					else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS))
					{
						responsetxt = getString(R.string.web_services_response_05);
						serviceStatus = false;
					}
					else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO))
					{
						responsetxt = getString(R.string.web_services_response_06);
						serviceStatus = false;
					}
					else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE))
					{
						responsetxt = getString(R.string.web_services_response_08);
						serviceStatus = false;
					}
					else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO))
					{
						responsetxt = getString(R.string.web_services_response_12);
						serviceStatus = false;
					}
					else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO))
					{
						responsetxt = getString(R.string.web_services_response_95);
						serviceStatus = false;
					}else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE))
					{
						responsetxt = getString(R.string.web_services_response_96);
						serviceStatus = false;
					}else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE))
					{
						responsetxt = getString(R.string.web_services_response_97);
						serviceStatus = false;
					}else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES))
					{
						responsetxt = getString(R.string.web_services_response_98);
						serviceStatus = false;
					}else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO))
					{
						responsetxt = getString(R.string.web_services_response_99);
						serviceStatus = false;
					}else{
						responsetxt = getString(R.string.web_services_response_99);
						serviceStatus = false;
					}
				}else{
					responsetxt = getString(R.string.insuficient_balance);
					serviceStatus = false;
				}
				//progressDialogAlodiga.dismiss();
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
				System.err.println(e);
				return false;
			} catch (Exception e)
			{
				e.printStackTrace();
				System.err.println(e);
				return false;
			}
			return serviceStatus;

		}

		@Override
		protected void onPreExecute() {
			progressDialogAlodiga.show();
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			//showProgress(false);
			if (success) {
				new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
						"La mando bien ....");

			} else {


				new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
						responsetxt);

			}
			progressDialogAlodiga.dismiss();
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}


}
