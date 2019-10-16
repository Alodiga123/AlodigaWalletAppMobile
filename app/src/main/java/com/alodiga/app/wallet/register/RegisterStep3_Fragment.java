package com.alodiga.app.wallet.register;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.R;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;
import com.alodiga.app.wallet.login.LoginActivity;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterStep3_Fragment extends Fragment implements OnClickListener {
	private static View view;
	private static EditText name, lastName,emailId, mobileNumber, location,
			password, confirmPassword,pinNumber;
	private static TextView login;
	private static Button signUpButton;
	private static CheckBox terms_conditions;
	static ProgressDialogAlodiga progressDialogAlodiga;
	private String responsetxt = "";
	private boolean serviceStatus;
	private String getname = "";
	private String getLastName = "";
	private String getEmailId = "";
	private String getPinNumber = "";
	private String getPassword = "";
	private String getConfirmPassword = "";

	private RegisterStep3_Fragment.UserRegisterTask mAuthTask = null;

	public RegisterStep3_Fragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.register_step3_layout, container, false);
		initViews();

		setListeners();
		return view;
	}



	// Initialize all views
	private void initViews() {
		name = (EditText) view.findViewById(R.id.name);
		lastName = (EditText) view.findViewById(R.id.lastName);
		emailId = (EditText) view.findViewById(R.id.userEmailId);
		password = (EditText) view.findViewById(R.id.password);


		pinNumber = (EditText) view.findViewById(R.id.edtPin);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
		signUpButton = (Button) view.findViewById(R.id.signUpBtn);
		login = (TextView) view.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);




	}

	// Set Listeners
	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signUpBtn:

			// Call checkValidation method
			checkValidation();
			break;

		case R.id.already_user:

			// Replace login fragment
			new LoginActivity().replaceLoginFragment();
			break;
		}

	}

	// Check Validation Method
	private void checkValidation() {
		// Get all edittext texts

		 getname = name.getText().toString();
		 getLastName = lastName.getText().toString();
		 getEmailId = emailId.getText().toString();

		 getPassword = password.getText().toString();
		 getConfirmPassword = confirmPassword.getText().toString();
		 getPinNumber = pinNumber.getText().toString();


		// Pattern match for email id
		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		// Check if all strings are null or not
		if (getname.equals("") || getname.length() == 0
				|| getLastName.equals("") || getLastName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getPinNumber.equals("") || getPinNumber.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					"Todos los campos son requeridos.");

		// Check if email id valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"El email es invalido.");

		// Check if both password should be equal
		else if (!getConfirmPassword.equals(getPassword))
			new CustomToast().Show_Toast(getActivity(), view,
					"Las contraseñas deben ser iguales");

		// Make sure user should check Terms and Conditions checkbox
		else if (!terms_conditions.isChecked())
			new CustomToast().Show_Toast(getActivity(), view,
					"Debe aceptar terminos y condiciones.");
			// Validate Country



		else if (getPinNumber.length()< 4)
			new CustomToast().Show_Toast(getActivity(), view,
					"Clave de operaciones es invalida, debe tener 4 digitos");


		////////////Validaciones Especiales de Password//////////////////
		// Check for a valid password, if the user entered one.


		else if (!validation_Password(getPassword)){
			new CustomToast().Show_Toast(getActivity(), view, getContext().getResources().getString(R.string.password_secure));
		}


			// Else do signup or do your stuff
		else
			registrar();

	}


	private static boolean validation_Password(final String PASSWORD_Arg)    {
		boolean result = false;
		try {
			if (PASSWORD_Arg!=null) {
				//_________________________
				//Parameteres
				final String MIN_LENGHT="8";
				final String MAX_LENGHT="20";
				final boolean SPECIAL_CHAR_NEEDED=true;

				//_________________________
				//Modules
				final String ONE_DIGIT = "(?=.*[0-9])";  //(?=.*[0-9]) a digit must occur at least once
				final String LOWER_CASE = "(?=.*[a-z])";  //(?=.*[a-z]) a lower case letter must occur at least once
				final String UPPER_CASE = "(?=.*[A-Z])";  //(?=.*[A-Z]) an upper case letter must occur at least once
				final String NO_SPACE = "(?=\\S+$)";  //(?=\\S+$) no whitespace allowed in the entire string
				//final String MIN_CHAR = ".{" + MIN_LENGHT + ",}";  //.{8,} at least 8 characters
				final String MIN_MAX_CHAR = ".{" + MIN_LENGHT + "," + MAX_LENGHT + "}";  //.{5,10} represents minimum of 5 characters and maximum of 10 characters

				final String SPECIAL_CHAR;
				if (SPECIAL_CHAR_NEEDED==true) SPECIAL_CHAR= "(?=.*[@#$%^&+=])"; //(?=.*[@#$%^&+=]) a special character must occur at least once
				else SPECIAL_CHAR="";
				//_________________________
				//Pattern
				//String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
				final String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;
				//_________________________
				result = PASSWORD_Arg.matches(PATTERN);
				//_________________________
			}

		} catch (Exception ex) {
			result=false;
		}

		return result;
	}

	public void registrar(){
		progressDialogAlodiga = new ProgressDialogAlodiga(getContext(),"cargando..");
		progressDialogAlodiga.show();

		//cifrando pin y credencial
		getPassword = Utils.aloDesencript(getPassword.toString());
		getPinNumber = Utils.aloDesencript(getPinNumber.toString());


		mAuthTask = new UserRegisterTask(getname,getLastName,getEmailId, Session.getPhoneNumber(),getPassword,getPinNumber,Session.getMobileCodeSms());
		mAuthTask.execute((Void) null);

	}

	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

		private final String name;
		private final String lastName;
		private final String email;

		private final String phoneNumber;
		private final String mobileCodeValidation;
		private final String password;
		private final String pin;


		UserRegisterTask(String name_, String lastName_,String email_, String phoneNumber_,String password_,String pin_,String mobileCodeValidation_) {
			name = name_;
			lastName = lastName_;
			email = email_;

			phoneNumber = phoneNumber_;
			password = password_;
			pin = pin_;
			mobileCodeValidation = mobileCodeValidation_;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			WebService webService = new WebService();
			Utils utils = new Utils();
			SoapObject response;
			try {

				String responseCode;
				String responseMessage = "";

				HashMap<String,String > map = new HashMap<String,String>();
				map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
				map.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
				map.put("usuarioId","");
				map.put("nombre",name);
				map.put("apellido",lastName);
				map.put("credencial",password);
				map.put("email",email);
				map.put("movil",phoneNumber.replace("+",""));
				map.put("fechaNacimiento","21-07-1988");
				map.put("direccion","APP_MOBILE");
				map.put("paisId","1");
				map.put("estadoId","1");
				map.put("ciudadId","1");
				map.put("codigoValidacionMovil",mobileCodeValidation);
				map.put("condadoId","1");
				map.put("codigoPostal","1006");
				map.put("nombreImagen","AloCash App Android");
				map.put("imagenBytes","null");
				map.put("link","AloCash App Android");
				map.put("pin",pin);

				response = webService.invokeGetAutoConfigString(map,Constants.WEB_SERVICES_METHOD_NAME_SAVE_USER,Constants.REGISTRO_UNIFICADO);
				responseCode = response.getProperty("codigoRespuesta").toString();
				responseMessage = response.getProperty("mensajeRespuesta").toString();

				//Activar las preguntas de seguridad


				if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
				{
					responsetxt = getString(R.string.web_services_response_00);
					serviceStatus = true;
					return serviceStatus;
				}
				if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CORREO_YA_EXISTE))
				{
					responsetxt = getString(R.string.web_services_response_10);
					serviceStatus = false;
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
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			//showProgress(false);
			if (success) {
				getFragmentManager()
						.beginTransaction()
						.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
						.replace(R.id.frameContainer, new RegisterStep4WelcomeFragment(),
								Utils.Welcome_Fragment).commit();
				new CustomToast().Show_Toast(getActivity(), view,
						responsetxt);

			} else {
				new CustomToast().Show_Toast(getActivity(), view,
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
