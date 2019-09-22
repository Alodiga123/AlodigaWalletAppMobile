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
import android.os.SystemClock;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityQuestion_Fragment extends Fragment implements
		OnClickListener {
	private static View view;
	private static EditText edtAnswer1, edtAnswer2,edtAnswer3;
	private static TextView textAnswer1,textAnswer2,textAnswer3 ;
	private static TextView submit, back;
	private static Spinner spinnerAnswer1;
	private static Spinner spinnerAnswer2;
	private static Spinner spinnerAnswer3;
	private static String stringResponse = "";
	private String responsetxt = "";
	private boolean serviceStatus;
	private SecurityQuestion_Fragment.UserGetSecurityAnswerTask mAuthTask = null;
	private ObjGenericObject select1;
	private ObjGenericObject select2;
	static ProgressDialogAlodiga progressDialogAlodiga;




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.security_questions_layout, container,
				false);
		progressDialogAlodiga = new ProgressDialogAlodiga(getContext(),"cargando..");
		progressDialogAlodiga.show();
		initViews();
		setListeners();
		return view;
	}

	// Initialize the views
	private void initViews() {
		mAuthTask = new UserGetSecurityAnswerTask();
		mAuthTask.execute((Void) null);
		submit = (TextView) view.findViewById(R.id.step1_next_button);
		back = (TextView) view.findViewById(R.id.backToLoginBtn);

		spinnerAnswer1 = (Spinner) view.findViewById(R.id.spinnerAnswer1);
		spinnerAnswer2 = (Spinner) view.findViewById(R.id.spinnerAnswer2);
		spinnerAnswer3 = (Spinner) view.findViewById(R.id.spinnerAnswer3);

		edtAnswer1 = (EditText) view.findViewById(R.id.edtAnswer1);
		edtAnswer2 = (EditText) view.findViewById(R.id.edtAnswer2);
		edtAnswer3 = (EditText) view.findViewById(R.id.edtAnswer3);


		Toast.makeText(getContext(),"antes",Toast.LENGTH_SHORT).show();








	}


	// Set Listeners over buttons
	private void setListeners() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.backToLoginBtn:
				// Replace Login Fragment on Back Presses
				new LoginActivity().replaceLoginFragment();
				break;

			case R.id.step1_next_button:
				submitButtonTask();
				break;
		}
	}

	private void submitButtonTask() {


	}


	public class UserGetSecurityAnswerTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {

			WebService webService = new WebService();
			Utils utils = new Utils();
			SoapObject response;
			try {
				String responseCode;
				String datosRespuesta = "";


				HashMap<String,String > map = new HashMap<String,String>();
				map.put("usuarioApi",Constants.WEB_SERVICES_USUARIOWS);
				map.put("passwordApi",Constants.WEB_SERVICES_PASSWORDWS);
				map.put("IdIdioma","4");

				response = webService.invokeGetAutoConfigString(map,Constants.WEB_SERVICES_METHOD_NAME_SECRET_AMSWER);
				stringResponse = response.toString();
				responseCode = response.getProperty("codigoRespuesta").toString();
				datosRespuesta = response.getProperty("datosRespuesta").toString();

				if(responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO))
				{
					responsetxt = getString(R.string.web_services_response_00);
					serviceStatus = true;
					return serviceStatus;

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
				List<ObjGenericObject> securityAnswers = getParseResponse(stringResponse);
				final ObjGenericObject[] objsecurityAnswer = new ObjGenericObject[securityAnswers.size()];
				for(int i = 0;i<securityAnswers.size();i++){
					objsecurityAnswer[i] = new ObjGenericObject(securityAnswers.get(i).getName(),securityAnswers.get(i).getId());
				}
				SpinAdapterSecurityAnswer spinAdapterSecurityAnswer;
				spinAdapterSecurityAnswer = new SpinAdapterSecurityAnswer(getContext(), android.R.layout.simple_spinner_item, objsecurityAnswer);



				spinnerAnswer1.setAdapter(spinAdapterSecurityAnswer);

			//	spinnerAnswer2.setSelection(2);
			//	spinnerAnswer3.setSelection(3);


				spinnerAnswer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
						Toast.makeText(getContext(),"spinnerAnswer1",Toast.LENGTH_SHORT).show();

						ObjGenericObject object = (ObjGenericObject) spinnerAnswer1.getSelectedItem();

						Toast.makeText(getContext(),"id"+object.getId()+"",Toast.LENGTH_SHORT).show();

						List<ObjGenericObject> securityAnswersk = getParseResponse(stringResponse,object.getId());

						final ObjGenericObject[] objsecurityAnswerk = new ObjGenericObject[securityAnswersk.size()];
						for(int j = 0;j<securityAnswersk.size();j++){
							objsecurityAnswerk[j] = new ObjGenericObject(securityAnswersk.get(j).getName(),securityAnswersk.get(j).getId());
						}
						SpinAdapterSecurityAnswer spinAdapterSecurityAnswer2;
						spinAdapterSecurityAnswer2 = new SpinAdapterSecurityAnswer(getContext(), android.R.layout.simple_spinner_item, objsecurityAnswerk);
						spinnerAnswer2.setAdapter(spinAdapterSecurityAnswer2);

					}
					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});


				spinnerAnswer2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

						Toast.makeText(getContext(),"spinnerAnswer2",Toast.LENGTH_SHORT).show();
						ObjGenericObject object = (ObjGenericObject) spinnerAnswer2.getSelectedItem();
						ObjGenericObject object2 = (ObjGenericObject) spinnerAnswer1.getSelectedItem();
						Toast.makeText(getContext(),"id"+object.getId()+"",Toast.LENGTH_SHORT).show();


						List<ObjGenericObject> securityAnswersk = getParseResponse(stringResponse,object.getId(),object2.getId());


						final ObjGenericObject[] objsecurityAnswerk = new ObjGenericObject[securityAnswersk.size()];
						for(int j = 0;j<securityAnswersk.size();j++){
							objsecurityAnswerk[j] = new ObjGenericObject(securityAnswersk.get(j).getName(),securityAnswersk.get(j).getId());
						}
						SpinAdapterSecurityAnswer spinAdapterSecurityAnswer2;
						spinAdapterSecurityAnswer2 = new SpinAdapterSecurityAnswer(getContext(), android.R.layout.simple_spinner_item, objsecurityAnswerk);
						spinnerAnswer3.setAdapter(spinAdapterSecurityAnswer2);






					}
					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});







			/*	getFragmentManager()
						.beginTransaction()
						.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
						//.replace(R.id.frameContainer, new Welcome_Fragment(),
						.replace(R.id.frameContainer, new RegisterStep2_Fragment(),

								//Utils.Welcome_Fragment).commit();
								Utils.register_step2_Fragment).commit();*/

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


	private List<ObjGenericObject> getParseResponse (String response){
		List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();
		for(int i = 1; i< getLenghtFromResponseJson(response);i++){
			String name = response.split("pregunta=")[i].split(";")[0];
			String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];
			ObjGenericObject objGenericObject = new ObjGenericObject(name,idValue);
			objGenericObjectList.add(objGenericObject);
		}
		return objGenericObjectList;
	}

	private List<ObjGenericObject> getParseResponse (String response,String id){
		List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();

		for(int i = 1; i< getLenghtFromResponseJson(response);i++){

			String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];

			if(idValue.equals(id)){
				continue;
			}

			String name = response.split("pregunta=")[i].split(";")[0];
			ObjGenericObject objGenericObject = new ObjGenericObject(name,idValue);
			objGenericObjectList.add(objGenericObject);
		}
		return objGenericObjectList;
	}

	private List<ObjGenericObject> getParseResponse (String response,String id,String id1){
		List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();

		for(int i = 1; i< getLenghtFromResponseJson(response);i++){

			String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];

			if(idValue.equals(id)|| idValue.equals(id1)){
				continue;
			}

			String name = response.split("pregunta=")[i].split(";")[0];
			ObjGenericObject objGenericObject = new ObjGenericObject(name,idValue);
			objGenericObjectList.add(objGenericObject);
		}
		return objGenericObjectList;
	}


	private static int getLenghtFromResponseJson(String v){
		return (v.split("pregunta=").length);
	}





}







