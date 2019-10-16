package com.alodiga.app.wallet.paymentComerce;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.R;
import com.alodiga.app.wallet.model.ObjCountry;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentComerceStep5Activity extends AppCompatActivity {
	private static View view;
	private static TextView amountValue, conceptValue;

	private static TextView txtAccountSourceValue, acountNumberValue,destinationPhoneValue,destinationLastNameValue,destinationNameValue, txtAmountValue
	,txtConceptValue;

	private static TextView login;
	private static Button btnProcessTransaction;
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

	private String balancePrepaidCard = "";
	private String balanceAlocoins = "";
	private String balanceAlodiga = "";
	SoapObject response;





	private ProcessOperationTransferenceTask mAuthTask = null;

	public PaymentComerceStep5Activity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation_transfer2);

		amountValue =  findViewById(R.id.txtAmountValue_2);
		conceptValue =  findViewById(R.id.txtConceptValue_2);
		acountNumberValue =  findViewById(R.id.txtAccountNumberValue_2);
		destinationPhoneValue = findViewById(R.id.txtDestinationPhoneValue_2);
		destinationLastNameValue = findViewById(R.id.txtDestinationLastNameValue_2);
		destinationNameValue = findViewById(R.id.txtDestinationNameValue_2);
		btnProcessTransaction = findViewById(R.id.btnProcessTransaction);
		txtAccountSourceValue = findViewById(R.id.txtAccountSourceValue_2);
		acountNumberValue.setText(Session.getDestinationAccountNumber());
		destinationPhoneValue.setText(Session.getDestinationPhoneValue());
		destinationLastNameValue.setText(Session.getDestinationLastNameValue());
		destinationNameValue.setText(Session.getDestinationNameValue());
		conceptValue.setText(Session.getDestinationConcept());
		amountValue.setText(Session.getGetDestinationAmount());
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
		btnProcessTransaction.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				procesar();
			}
		});




	}

	public void procesar(){



		mAuthTask = new ProcessOperationTransferenceTask("1",Session.getEmail(),Session.getMoneySelected().getId(),Session.getGetDestinationAmount(),Session.getDestinationConcept(),"1",Session.getUsuarioDestionId());
		mAuthTask.execute((Void) null);


	}
	public class ProcessOperationTransferenceTask extends AsyncTask<Void, Void, Boolean> {


		private String cryptogramShop, emailUser, productId, amountPayment,
				conceptTransaction, cryptogramUser, idUserDestination;

		public ProcessOperationTransferenceTask(String cryptogramShop_, String emailUser_, String productId_, String amountPayment_, String conceptTransaction_, String cryptogramUser_, String idUserDestination_) {
			this.cryptogramShop = cryptogramShop_; //nulo
			this.emailUser = emailUser_; //email
			this.productId = productId_; // idproductos
			this.amountPayment = amountPayment_; //monto
			this.conceptTransaction = conceptTransaction_; //paymentshop
			this.cryptogramUser = cryptogramUser_; // nulo
			this.idUserDestination = idUserDestination_; //destino
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			WebService webService = new WebService();
			Utils utils = new Utils();

			try {

				boolean availableBalance = true;
				String responseCode;
				String responseMessage = "";


				if(availableBalance){
					HashMap<String,String > map = new HashMap<String,String>();
					map.put("cryptogramShop",cryptogramShop);
					map.put("emailUser",emailUser);
					map.put("productId",productId);
					map.put("amountPayment",amountPayment);
					map.put("conceptTransaction",conceptTransaction);
					map.put("cryptogramUser",cryptogramUser);
					map.put("idUserDestination",idUserDestination);

					response = webService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_PAYMENT_COMERCE,Constants.ALODIGA);
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
					}else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_AMOUNT_LIMIT))
					{
						responsetxt = getString(R.string.web_services_response_30);
						serviceStatus = false;
					} else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_ACCOUNT))
					{
						responsetxt = getString(R.string.web_services_response_31);
						serviceStatus = false;

					}else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_CUSTOMER))
					{
						responsetxt = getString(R.string.web_services_response_32);
						serviceStatus = false;

					}else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USER_HAS_NOT_BALANCE))
					{
						responsetxt = getString(R.string.web_services_response_33);
						serviceStatus = false;

					} else if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO))
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
				responsetxt = getString(R.string.web_services_response_99);
				serviceStatus = false;
				e.printStackTrace();
				System.err.println(e);
				return false;
			} catch (Exception e)
			{
				responsetxt = getString(R.string.web_services_response_99);
				serviceStatus = false;
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
			if (success) {

                Session.setAlocoinsBalance(balanceAlocoins);
                Session.setHealthCareCoinsBalance(balancePrepaidCard);
                Session.setAlodigaBalance(balanceAlodiga);
				Session.setObjUserHasProducts(getListProduct(response));
                Intent i = new Intent(PaymentComerceStep5Activity.this, PaymentComerceStep6Activity.class);
                startActivity(i);
                finish();



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


	/*public class ProcessOperationTransferenceTask extends AsyncTask<Void, Void, Boolean> {

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

				switch (Session.getMoneySelected()) {

					case 0:

						if(Float.valueOf(amount)>Float.valueOf(Session.getAlodigaBalance())){
							availableBalance = false;
						}
						methodName = "transferirSaldoAlodiga";
						break;

					case 1:

						if(Float.valueOf(amount)>Float.valueOf(Session.getAlocoinsBalance())){
							availableBalance = false;
						}
						methodName = "transferirAlocoins";
						break;
					case 2:
						if(Float.valueOf(amount)>Float.valueOf(Session.getHealthCareCoinsBalance())){
							availableBalance = false;
						}
						methodName = "transferirSaldoHealthCareCoins";
						break;

					default:
						break;
				}


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
						SoapObject response2;
						String responseCode2;
						String responseMessage2 = "";
						HashMap<String,String > map2 = new HashMap<String,String>();
						map2.put("usuarioApi",Constants.WEB_SERVICES_USUARIOWS);
						map2.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
						map2.put("email",Session.getEmail());
						String methodName2 = "getUsuarioporemail";
						response2 = webService.invokeGetAutoConfigString(map2,methodName2,Constants.REGISTRO_UNIFICADO);
						responseCode2 = response2.getProperty("codigoRespuesta").toString();

						if(responseCode2.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
						{
							  String res2 =  response2.getProperty("datosRespuesta").toString();
							  balancePrepaidCard = getValueFromResponseJson("saldoHealthCareCoins",res2);
							  balanceAlocoins = getValueFromResponseJson("saldoAlocoins",res2);
							  balanceAlodiga = getValueFromResponseJson("saldoAlodiga",res2);

							serviceStatus = true;
						}else{
							serviceStatus = false;
						}
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

				Session.setAlocoinsBalance(balanceAlocoins);
				Session.setHealthCareCoinsBalance(balancePrepaidCard);
				Session.setAlodigaBalance(balanceAlodiga);

				Intent i = new Intent(PaymentComerceStep5Activity.this, PaymentComerceStep6Activity.class);
				startActivity(i);
				finish();
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


	private static String getValueFromResponseJson(String v, String response){
		return (response.split(v+"=")[1].split(";")[0]);
	}
*/

	protected ArrayList<ObjUserHasProduct>  getListProduct (SoapObject response){
		//ObjUserHasProduct[] obj2_aux= new ObjUserHasProduct[response.getPropertyCount()-3];
		//ObjUserHasProduct[] obj2 = new ObjUserHasProduct[response.getPropertyCount()-3];
		ArrayList<ObjUserHasProduct> obj2= new ArrayList<>();
		for(int i=3; i<response.getPropertyCount(); i++)
		{
			SoapObject obj = (SoapObject) response.getProperty(i);
			String propiedad = response.getProperty(i).toString();
			ObjUserHasProduct object = new ObjUserHasProduct(obj.getProperty("id").toString(),obj.getProperty("name").toString(),obj.getProperty("currentBalance").toString(),obj.getProperty("symbol").toString());
			obj2.add(object);
			//obj2[i-3] = object;
		}

		return obj2;
	}

}
