package com.alodiga.app.wallet.securityQuestion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterSecurityAnswer;
import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SecurityQuestionStep1Fragment extends Fragment implements
        OnClickListener {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static View view;
    private static EditText edtAnswer1, edtAnswer2, edtAnswer3;
    private static TextView textAnswer1, textAnswer2, textAnswer3;
    private static String getedtAnswer1, getedtAnswer2, getedtAnswer3;
    private static Button submit, back;
    private static Spinner spinnerAnswer1;
    private static Spinner spinnerAnswer2;
    private static Spinner spinnerAnswer3;
    private static String stringResponse = "";
    private String responsetxt = "";
    private boolean serviceStatus;
    private SecurityQuestionStep1Fragment.UserGetSecurityAnswerTask mAuthTask = null;
    private SecurityQuestionStep1Fragment.UserSendAnswerTask AnswerTask = null;
    private ObjGenericObject select1;
    private ObjGenericObject select2;
    private ObjGenericObject object_select, object2_select, object3_select;

    private static int getLenghtFromResponseJson(String v) {
        return (v.split("pregunta=").length);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.security_questions_layout, container,
                false);
        progressDialogAlodiga = new ProgressDialogAlodiga(getContext(), getString(R.string.loading));
        progressDialogAlodiga.show();
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        mAuthTask = new UserGetSecurityAnswerTask();
        mAuthTask.execute((Void) null);
        submit = view.findViewById(R.id.step1_next_button);
        back = view.findViewById(R.id.backToLoginBtn);


        spinnerAnswer1 = view.findViewById(R.id.spinnerAnswer1);
        spinnerAnswer2 = view.findViewById(R.id.spinnerAnswer2);
        spinnerAnswer3 = view.findViewById(R.id.spinnerAnswer3);

        edtAnswer1 = view.findViewById(R.id.edtAnswer1);
        edtAnswer2 = view.findViewById(R.id.edtAnswer2);
        edtAnswer3 = view.findViewById(R.id.edtAnswer3);

        //Toast.makeText(getContext(),"antes",Toast.LENGTH_SHORT).show();
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
                Session.clearALL();
                new LoginActivity().replaceLoginFragment();
                break;

            case R.id.step1_next_button:
                submitButtonTask();
                break;
        }
    }

    public void sendAnswer() {
        progressDialogAlodiga = new ProgressDialogAlodiga(getContext(), getString(R.string.loading));
        progressDialogAlodiga.show();
        AnswerTask = new UserSendAnswerTask();
        AnswerTask.execute((Void) null);

    }

    private void submitButtonTask() {
        getedtAnswer1 = edtAnswer1.getText().toString();
        getedtAnswer2 = edtAnswer2.getText().toString();
        getedtAnswer3 = edtAnswer3.getText().toString();

        object_select = (ObjGenericObject) spinnerAnswer1.getSelectedItem();
        object2_select = (ObjGenericObject) spinnerAnswer2.getSelectedItem();
        object3_select = (ObjGenericObject) spinnerAnswer3.getSelectedItem();

        // Check if all strings are null or not
        if (getedtAnswer1.equals("") || getedtAnswer1.length() == 0
                || getedtAnswer2.equals("") || getedtAnswer2.length() == 0
                || getedtAnswer3.equals("") || getedtAnswer3.length() == 0
        ) {
            new CustomToast().Show_Toast(getActivity(), view, getString(R.string.invalid_all_question));

            // Else do signup or do your stuff
        } else {
            //new CustomToast().Show_Toast(getActivity(), view, getString(R.string.web_services_response_00));

			/*Intent intent = new Intent(getActivity(), SecurityQuestionStep2WelcomeFragment.class);
			getActivity().startActivity(intent);*/
            sendAnswer();

			/*getFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
					//.replace(R.id.frameContainer, new RegisterStep4WelcomeFragment(),
					.replace(R.id.frameContainer, new SecurityQuestionStep2WelcomeFragment(),

							//Utils.RegisterStep4WelcomeFragment).commit();
							Utils.SecurityQuestionStep2WelcomeFragment).commit();*/
        }

    }

    private List<ObjGenericObject> getParseResponse(String response) {
        List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();
        for (int i = 1; i < getLenghtFromResponseJson(response); i++) {
            String name = response.split("pregunta=")[i].split(";")[0];
            String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];
            ObjGenericObject objGenericObject = new ObjGenericObject(name, idValue);
            objGenericObjectList.add(objGenericObject);
        }
        return objGenericObjectList;
    }

    private List<ObjGenericObject> getParseResponse(String response, String id) {
        List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();

        for (int i = 1; i < getLenghtFromResponseJson(response); i++) {

            String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];

            if (idValue.equals(id)) {
                continue;
            }

            String name = response.split("pregunta=")[i].split(";")[0];
            ObjGenericObject objGenericObject = new ObjGenericObject(name, idValue);
            objGenericObjectList.add(objGenericObject);
        }
        return objGenericObjectList;
    }

    private List<ObjGenericObject> getParseResponse(String response, String id, String id1) {
        List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();

        for (int i = 1; i < getLenghtFromResponseJson(response); i++) {

            String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];

            if (idValue.equals(id) || idValue.equals(id1)) {
                continue;
            }

            String name = response.split("pregunta=")[i].split(";")[0];
            ObjGenericObject objGenericObject = new ObjGenericObject(name, idValue);
            objGenericObjectList.add(objGenericObject);
        }
        return objGenericObjectList;
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
                String responseMessage = "";


                HashMap<String, String> map = new HashMap<String, String>();
                map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("IdIdioma", "4");

                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SECRET_AMSWER, Constants.REGISTRO_UNIFICADO);
                stringResponse = response.toString();
                responseCode = response.getProperty("codigoRespuesta").toString();
                datosRespuesta = response.getProperty("datosRespuesta").toString();

                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
                    responsetxt = getString(R.string.web_services_response_00);
                    serviceStatus = true;
                    return serviceStatus;

                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS)) {
                    responsetxt = getString(R.string.web_services_response_01);
                    serviceStatus = false;

                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA)) {
                    responsetxt = getString(R.string.web_services_response_03);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA)) {
                    responsetxt = getString(R.string.web_services_response_04);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS)) {
                    responsetxt = getString(R.string.web_services_response_05);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO)) {
                    responsetxt = getString(R.string.web_services_response_06);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE)) {
                    responsetxt = getString(R.string.web_services_response_08);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO)) {
                    responsetxt = getString(R.string.web_services_response_12);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO)) {
                    responsetxt = getString(R.string.web_services_response_95);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE)) {
                    responsetxt = getString(R.string.web_services_response_96);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE)) {
                    responsetxt = getString(R.string.web_services_response_97);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES)) {
                    responsetxt = getString(R.string.web_services_response_98);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                } else {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                }
                //progressDialogAlodiga.dismiss();
            } catch (IllegalArgumentException e) {

                responsetxt = getString(R.string.web_services_response_99);
                serviceStatus = false;
                e.printStackTrace();
                System.err.println(e);
                return false;
            } catch (Exception e) {
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
                for (int i = 0; i < securityAnswers.size(); i++) {
                    objsecurityAnswer[i] = new ObjGenericObject(securityAnswers.get(i).getName(), securityAnswers.get(i).getId());
                }
                SpinAdapterSecurityAnswer spinAdapterSecurityAnswer;
                spinAdapterSecurityAnswer = new SpinAdapterSecurityAnswer(getContext(), android.R.layout.simple_spinner_item, objsecurityAnswer);


                spinnerAnswer1.setAdapter(spinAdapterSecurityAnswer);

                //	spinnerAnswer2.setSelection(2);
                //	spinnerAnswer3.setSelection(3);


                spinnerAnswer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(getContext(),"spinnerAnswer1",Toast.LENGTH_SHORT).show();

                        ObjGenericObject object = (ObjGenericObject) spinnerAnswer1.getSelectedItem();

                        //Toast.makeText(getContext(),"id"+object.getId()+"",Toast.LENGTH_SHORT).show();

                        List<ObjGenericObject> securityAnswersk = getParseResponse(stringResponse, object.getId());

                        final ObjGenericObject[] objsecurityAnswerk = new ObjGenericObject[securityAnswersk.size()];
                        for (int j = 0; j < securityAnswersk.size(); j++) {
                            objsecurityAnswerk[j] = new ObjGenericObject(securityAnswersk.get(j).getName(), securityAnswersk.get(j).getId());
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

                        //Toast.makeText(getContext(),"spinnerAnswer2",Toast.LENGTH_SHORT).show();
                        ObjGenericObject object = (ObjGenericObject) spinnerAnswer2.getSelectedItem();
                        ObjGenericObject object2 = (ObjGenericObject) spinnerAnswer1.getSelectedItem();
                        //Toast.makeText(getContext(),"id"+object.getId()+"",Toast.LENGTH_SHORT).show();


                        List<ObjGenericObject> securityAnswersk = getParseResponse(stringResponse, object.getId(), object2.getId());

                        final ObjGenericObject[] objsecurityAnswerk = new ObjGenericObject[securityAnswersk.size()];
                        for (int j = 0; j < securityAnswersk.size(); j++) {
                            objsecurityAnswerk[j] = new ObjGenericObject(securityAnswersk.get(j).getName(), securityAnswersk.get(j).getId());
                        }
                        SpinAdapterSecurityAnswer spinAdapterSecurityAnswer2;
                        spinAdapterSecurityAnswer2 = new SpinAdapterSecurityAnswer(getContext(), android.R.layout.simple_spinner_item, objsecurityAnswerk);
                        spinnerAnswer3.setAdapter(spinAdapterSecurityAnswer2);

                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                });


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

    public class UserSendAnswerTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();
            SoapObject response;
            try {
                String responseCode;
                String responseMessage = "";


                HashMap<String, String> map = new HashMap<String, String>();
                map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("usuarioId", Session.getUserId());
                map.put("preguntaId1", object_select.getId());
                map.put("repuestaId1", getedtAnswer1);
                map.put("preguntaId2", object2_select.getId());
                map.put("repuestaId2", getedtAnswer2);
                map.put("preguntaId3", object3_select.getId());
                map.put("repuestaId3", getedtAnswer3);

                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_SECRET_AMSWER, Constants.REGISTRO_UNIFICADO);
                responseCode = response.getProperty("codigoRespuesta").toString();
                responseMessage = response.getProperty("mensajeRespuesta").toString();


                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

                    //movilCode = response.getProperty("datosRespuesta").toString();
                    responsetxt = getString(R.string.web_services_response_00);
                    serviceStatus = true;
                    return serviceStatus;

                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS)) {
                    responsetxt = getString(R.string.web_services_response_01);
                    serviceStatus = false;

                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA)) {
                    responsetxt = getString(R.string.web_services_response_03);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA)) {
                    responsetxt = getString(R.string.web_services_response_04);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS)) {
                    responsetxt = getString(R.string.web_services_response_05);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO)) {
                    responsetxt = getString(R.string.web_services_response_06);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE)) {
                    responsetxt = getString(R.string.web_services_response_08);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO)) {
                    responsetxt = getString(R.string.web_services_response_12);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO)) {
                    responsetxt = getString(R.string.web_services_response_95);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE)) {
                    responsetxt = getString(R.string.web_services_response_96);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE)) {
                    responsetxt = getString(R.string.web_services_response_97);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES)) {
                    responsetxt = getString(R.string.web_services_response_98);
                    serviceStatus = false;
                } else if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
                    responsetxt = getString(R.string.web_services_response_99);
                    serviceStatus = false;
                }
                //progressDialogAlodiga.dismiss();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.err.println(e);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e);
                return false;
            }
            return serviceStatus;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            AnswerTask = null;
            //showProgress(false);
            if (success) {
                //Session.setMobileCodeSms(movilCode);
                //Session.setPhoneNumber(getMobileNumber);

				/*Intent intent = new Intent(getActivity(), MainActivity.class);
				getActivity().startActivity(intent);*/
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        //.replace(R.id.frameContainer, new RegisterStep4WelcomeFragment(),
                        .replace(R.id.frameContainer, new SecurityQuestionStep2WelcomeFragment(),

                                //Utils.RegisterStep4WelcomeFragment).commit();
                                Utils.Welcome_Secure_Fragment).commit();


                //new CustomToast().Show_Toast(getActivity(), view,
                //		responsetxt);

            } else {
                new CustomToast().Show_Toast(getActivity(), view,
                        responsetxt);
            }
            progressDialogAlodiga.dismiss();
        }

        @Override
        protected void onCancelled() {
            AnswerTask = null;
        }
    }


}







