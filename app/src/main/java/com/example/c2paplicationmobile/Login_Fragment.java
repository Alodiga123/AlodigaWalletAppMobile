package com.example.c2paplicationmobile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment implements OnClickListener {
	private static View view;
	private boolean serviceStatus;
	private boolean isFirstAccess = false;
	private View mLoginFormView;
	static ProgressDialogAlodiga progressDialogAlodiga;
	private View mProgressView;

	private static EditText emailid, password;
	private static Button loginButton;
	private static TextView forgotPassword, signUp;
	private static CheckBox show_hide_password;
	private static LinearLayout loginLayout;
	private String responsetxt = "";
	private static Animation shakeAnimation;
	private String getEmailId;
	private String getPassword;
	private static FragmentManager fragmentManager;
	private UserLoginTask mAuthTask = null;


	String nameSession = "";
	String lastNameSession = "";
	String phoneNumberSession = "";
	String emailSession = "";
	String alodigaBalanceSession = "";
	String accountNumberSession = "";
	String alocoinsBalanceSesssion = "";
	String healthCareCoinsBalanceSession = "";
	String userId="";

	public Login_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.login_layout, container, false);
		initViews();
		setListeners();
		mProgressView = view.findViewById(R.id.login_progress);
		return view;
	}

	// Initiate Views
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		emailid = (EditText) view.findViewById(R.id.login_emailid);
		password = (EditText) view.findViewById(R.id.login_password);
		loginButton = (Button) view.findViewById(R.id.loginBtn);
		forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
		signUp = (TextView) view.findViewById(R.id.createAccount);
		show_hide_password = (CheckBox) view
				.findViewById(R.id.show_hide_password);
		loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
		// Load ShakeAnimation
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);
		// Setting text selector over textviews
		@SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			forgotPassword.setTextColor(csl);
			show_hide_password.setTextColor(csl);
			signUp.setTextColor(csl);
		} catch (Exception e) {
		}
		/*emailid.setText("antonioarcangelgomez@gmail.com");
		password.setText("Kg0m3z$11");*/
		/*emailid.setText("moisegrat12@gmail.com");
		password.setText("Danye852#");*/
		emailid.setText("adira0411@gmail.com");
		password.setText("Alodiga456&");
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(
					show ? 0 : 1).setListener(
							new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime).alpha(
					show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}


	// Set Listeners
	private void setListeners() {
		loginButton.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
		signUp.setOnClickListener(this);

		// Set check listener over checkbox for showing and hiding password
		show_hide_password
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton button,
							boolean isChecked) {

						// If it is checkec then show password else hide
						// password
						if (isChecked) {

							show_hide_password.setText(R.string.hide_pwd);// change
																			// checkbox
																			// text

							password.setInputType(InputType.TYPE_CLASS_TEXT);
							password.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());// show password
						} else {
							show_hide_password.setText(R.string.show_pwd);// change
																			// checkbox
																			// text

							password.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_PASSWORD);
							password.setTransformationMethod(PasswordTransformationMethod
									.getInstance());// hide password

						}

					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			checkValidation();

			break;

		case R.id.forgot_password:

			// Replace forgot password fragment with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer,
							new ForgotPassword_Fragment(),
							Utils.ForgotPassword_Fragment).commit();
			break;
		case R.id.createAccount:

			// Replace signup frgament with animation
			/*fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer, new SignUp_Fragment(),
							Utils.SignUp_Fragment).commit();*/

			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer, new RegisterStep1_Fragment(),
							Utils.Register_step_1).commit();

			break;

		}

	}

	// Check Validation before login
	private void checkValidation() {
		// Get email id and password
		 getEmailId = emailid.getText().toString();
		 getPassword = password.getText().toString();

		// Check patter for email id
		Pattern p = Pattern.compile(Utils.regEx);

		Matcher m = p.matcher(getEmailId);

		// Check for both field is empty or not
		if (getEmailId.equals("") || getEmailId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0) {
			loginLayout.startAnimation(shakeAnimation);
			new CustomToast().Show_Toast(getActivity(), view,
					"Enter both credentials.");

		}
		// Check if email id is valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"Your Email Id is Invalid.");
		// Else do login and do your stuff
		else entrar();

	}

	public void entrar(){

		progressDialogAlodiga = new ProgressDialogAlodiga(getContext(),"cargando..");
		progressDialogAlodiga.show();
		mAuthTask = new UserLoginTask(getEmailId, getPassword);
		mAuthTask.execute((Void) null);

	}

	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;
		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			WebService webService = new WebService();
			Utils utils = new Utils();
			SoapObject response;
			try {

				String responseCode;
				String responseMessage = "";

				HashMap <String,String > map = new HashMap<String,String>();
				map.put("usuarioApi",Constants.WEB_SERVICES_USUARIOWS);
				map.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
				map.put("email",mEmail);
				map.put("credencial",mPassword);
				map.put("ip","192.168.3.45");
				response = webService.invokeGetAutoConfigString(map,Constants.WEB_SERVICES_METHOD_NAME_LOGIN_APP_MOBILE);
				responseCode = response.getProperty("codigoRespuesta").toString();
				responseMessage = response.getProperty("mensajeRespuesta").toString();

				if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO))
				{
					 String res =  response.getProperty("datosRespuesta").toString();
					 nameSession = getValueFromResponseJson("nombre",res) + " "+getValueFromResponseJson("apellido",res);
					 phoneNumberSession = getValueFromResponseJson("movil",res);
					 emailSession = getValueFromResponseJson("email",res);
					 alodigaBalanceSession = getValueFromResponseJson("saldoAlodiga",res);
					 accountNumberSession = getValueFromResponseJson("numeroCuenta",res);
					 alocoinsBalanceSesssion = getValueFromResponseJson("saldoAlocoins",res);
					 healthCareCoinsBalanceSession = getValueFromResponseJson("saldoHealthCareCoins",res);
					 userId = getValueFromResponseJson("UsuarioID",res);

					if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)){
						responsetxt = getString(R.string.web_services_response_00);
						serviceStatus = true;
						return serviceStatus;}else
					{
						responsetxt = getString(R.string.web_services_response_12);
						isFirstAccess = true;
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
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			//showProgress(false);
			if (success) {
				emailid.setText("");
				password.setText("");
				Session.setUsername(nameSession);
				Session.setPhoneNumber(phoneNumberSession);
				Session.setEmail(emailSession);
				Session.setAlodigaBalance(alodigaBalanceSession);
				Session.setAccountNumber(accountNumberSession);
				Session.setAlocoinsBalance(alocoinsBalanceSesssion);
				Session.setUserId(userId);
				Session.setHealthCareCoinsBalance(healthCareCoinsBalanceSession);
				Intent intent = new Intent(getActivity(),MainActivity.class);
				getActivity().startActivity(intent);
			} else  if(isFirstAccess){

				Session.setUsername(nameSession);
				Session.setPhoneNumber(phoneNumberSession);
				Session.setEmail(emailSession);
				Session.setAlodigaBalance(alodigaBalanceSession);
				Session.setAccountNumber(accountNumberSession);
				Session.setAlocoinsBalance(alocoinsBalanceSesssion);
				Session.setUserId(userId);
				Session.setHealthCareCoinsBalance(healthCareCoinsBalanceSession);

				fragmentManager
						.beginTransaction()
						.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
						.replace(R.id.frameContainer, new SecurityQuestion_Fragment(),
								Utils.SecurityQuestion_Fragment).commit();
			}
			else  {
				new CustomToast().Show_Toast(getActivity(), view,
						responsetxt);
			}
			progressDialogAlodiga.dismiss();
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}


	private static String getValueFromResponseJson(String v, String response){
		return (response.split(v+"=")[1].split(";")[0]);
	}

}
