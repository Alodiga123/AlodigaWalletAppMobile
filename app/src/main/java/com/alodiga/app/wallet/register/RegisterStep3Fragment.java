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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterDocumentType;
import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.wallet.model.ObjDocumentType;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;
import com.alodiga.app.wallet.validate.ValidateAccountStep4Activity;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterStep3Fragment extends Fragment implements OnClickListener {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static View view;
    private static EditText name, lastName, emailId, mobileNumber, location,
            password, confirmPassword, pinNumber,documentNumber;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private String responsetxt = "";
    private boolean serviceStatus;
    SoapObject response2;
    private String getname = "";
    private RegisterStep3Fragment.DocumentTypeTask documentTypeTask = null;
    private String getLastName = "";
    private String getEmailId = "";
    private String getPinNumber = "";
    private EditText edtDocumentNumber;
    Spinner spinner_document_type;

    private String getPassword = "";
    private String getConfirmPassword = "";
    private String getdocumentNumber = "";
    private String getDocumentTypeId = "";

    private RegisterStep3Fragment.UserRegisterTask mAuthTask = null;

    public RegisterStep3Fragment() {
    }

    private static boolean validation_Password(final String PASSWORD_Arg) {
        boolean result = false;
        try {
            if (PASSWORD_Arg != null) {
                //_________________________
                //Parameteres
                final String MIN_LENGHT = "8";
                final String MAX_LENGHT = "20";
                final boolean SPECIAL_CHAR_NEEDED = true;

                //_________________________
                //Modules
                final String ONE_DIGIT = "(?=.*[0-9])";  //(?=.*[0-9]) a digit must occur at least once
                final String LOWER_CASE = "(?=.*[a-z])";  //(?=.*[a-z]) a lower case letter must occur at least once
                final String UPPER_CASE = "(?=.*[A-Z])";  //(?=.*[A-Z]) an upper case letter must occur at least once
                final String NO_SPACE = "(?=\\S+$)";  //(?=\\S+$) no whitespace allowed in the entire string
                //final String MIN_CHAR = ".{" + MIN_LENGHT + ",}";  //.{8,} at least 8 characters
                final String MIN_MAX_CHAR = ".{" + MIN_LENGHT + "," + MAX_LENGHT + "}";  //.{5,10} represents minimum of 5 characters and maximum of 10 characters

                final String SPECIAL_CHAR;
                if (SPECIAL_CHAR_NEEDED == true)
                    SPECIAL_CHAR = "(?=.*[@#$%^&+=])"; //(?=.*[@#$%^&+=]) a special character must occur at least once
                else SPECIAL_CHAR = "";
                //_________________________
                //Pattern
                //String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
                final String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;
                //_________________________
                result = PASSWORD_Arg.matches(PATTERN);
                //_________________________
            }

        } catch (Exception ex) {
            result = false;
        }

        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_step3_layout, container, false);
        initViews();

        setListeners();
        getDocumentType();
        return view;
    }

    // Initialize all views
    private void initViews() {
        name = view.findViewById(R.id.name);
        lastName = view.findViewById(R.id.lastName);
        emailId = view.findViewById(R.id.userEmailId);
        password = view.findViewById(R.id.password);
        edtDocumentNumber = view.findViewById(R.id.edtDocumentNumber);
        spinner_document_type = view.findViewById(R.id.spinner_document_type);
        pinNumber = view.findViewById(R.id.edtPin);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        signUpButton = view.findViewById(R.id.signUpBtn);
        login = view.findViewById(R.id.already_user);
        terms_conditions = view.findViewById(R.id.terms_conditions);
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

        getdocumentNumber = edtDocumentNumber.getText().toString();


        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        int messageForToast= Utils.validatePassword(getPassword, getConfirmPassword);

        // Check if all strings are null or not
        if (getname.equals("") || getname.length() == 0
                || getLastName.equals("") || getLastName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getdocumentNumber.equals("") || getdocumentNumber.length() == 0
                || getPinNumber.equals("") || getPinNumber.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.invalid_all_question));

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.email_invalid));

            // Check if both password should be equal
       // else if (!getConfirmPassword.equals(getPassword))
        //    new CustomToast().Show_Toast(getActivity(), view,
        //            getString(R.string.password_eq));

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.acept_ter));
            // Validate Country


        else if (getPinNumber.length() < 4)
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.pin_invalid));


            ////////////Validaciones Especiales de Password//////////////////
            // Check for a valid password, if the user entered one.


       // else if (!validation_Password(getPassword)) {
         //   new CustomToast().Show_Toast(getActivity(), view, getContext().getResources().getString(R.string.password_secure));
       // }
        else if(!getPassword.trim().equals("") && !getConfirmPassword.trim().equals("") && messageForToast!= 0)
        {
                          new CustomToast().Show_Toast(getActivity(), view,
                        getString(messageForToast));

        }

        // Else do signup or do your stuff
        else
            registrar();

    }

    public void registrar() {
        progressDialogAlodiga = new ProgressDialogAlodiga(getContext(), getString(R.string.loading));
        progressDialogAlodiga.show();
        //cifrando pin y credencial
        getPassword = Utils.aloDesencript(getPassword);
        getPinNumber = Utils.aloDesencript(getPinNumber);
        mAuthTask = new UserRegisterTask(getname, getLastName, getEmailId, Session.getPhoneNumber(), getPassword, getPinNumber, Session.getMobileCodeSms(),getdocumentNumber,((ObjDocumentType) spinner_document_type.getSelectedItem()).getId());
        mAuthTask.execute((Void) null);

    }


    public void getDocumentType() {
        progressDialogAlodiga = new ProgressDialogAlodiga(getContext(), getString(R.string.loading));
//        progressDialogAlodiga.show();
        documentTypeTask = new RegisterStep3Fragment.DocumentTypeTask();
        documentTypeTask.execute((Void) null);
    }


    public class DocumentTypeTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

            WebService webService = new WebService();
            Utils utils = new Utils();
            try {
                String responseCode;
                String responseMessage = "";
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("countryId", "1");
                map.put("originAplicationId",Constants.WEB_SERVICES_METHOD_NAME_ORGIN_APP);

                response2 = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_DOCUMENT_PERSON_TYPE, Constants.ALODIGA);
                responseCode = response2.getProperty("codigoRespuesta").toString();
                responseMessage = response2.getProperty("mensajeRespuesta").toString();
                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

                    //movilCode = response2.getProperty("datosRespuesta").toString();
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

                ObjDocumentType[] objDocumentTypes = getDocumentType(response2);
                SpinAdapterDocumentType spinAdapterDocumentType;
                spinAdapterDocumentType = new SpinAdapterDocumentType(getContext(), android.R.layout.simple_spinner_item, objDocumentTypes);
                spinner_document_type.setAdapter(spinAdapterDocumentType);

            } else {
                new CustomToast().Show_Toast(getActivity(), view,
                        responsetxt);
            }
            progressDialogAlodiga.dismiss();
        }

        protected ObjDocumentType[] getDocumentType(SoapObject response) {

            ObjDocumentType[] obj2 = new ObjDocumentType[response.getPropertyCount() - 3];

            for (int i = 3; i < response.getPropertyCount(); i++) {
                SoapObject obj = (SoapObject) response.getProperty(i);
                String propiedad = response.getProperty(i).toString();
                ObjDocumentType object = new ObjDocumentType(obj.getProperty("id").toString(),obj.getProperty("description").toString(),obj.getProperty("codeIdentification").toString());
                obj2[i - 3] = object;
            }

            return obj2;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }


    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String name;
        private final String lastName;
        private final String email;

        private final String documentTypeId;
        private final String documentNumber;
        private final String phoneNumber;
        private final String mobileCodeValidation;
        private final String password;
        private final String pin;


        UserRegisterTask(String name_, String lastName_, String email_, String phoneNumber_, String password_, String pin_, String mobileCodeValidation_, String documentNumber_,String documentTypeId_) {
            name = name_;
            lastName = lastName_;
            email = email_;

            phoneNumber = phoneNumber_;
            password = password_;
            pin = pin_;
            mobileCodeValidation = mobileCodeValidation_;
            documentTypeId = documentTypeId_;
            documentNumber = documentNumber_;
        }

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
                map.put("usuarioId", "");
                map.put("nombre", name);
                map.put("apellido", lastName);
                map.put("credencial", password);
                map.put("email", email);
                map.put("movil", phoneNumber.replace("+", ""));
                map.put("fechaNacimiento", "21-07-1988");
                map.put("direccion", "APP_MOBILE");
                map.put("paisId", "1");
                map.put("estadoId", "1");
                map.put("ciudadId", "1");
                map.put("codigoValidacionMovil", mobileCodeValidation);
                map.put("condadoId", "1");
                map.put("codigoPostal", "1006");
                map.put("nombreImagen", "AloCash App Android");
                map.put("imagenBytes", "null");
                map.put("link", "AloCash App Android");
                map.put("pin", pin);
                map.put("tipoDocumentoId", documentTypeId);
                map.put("numeroDocumento", documentNumber);
                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SAVE_USER, Constants.REGISTRO_UNIFICADO);
                responseCode = response.getProperty("codigoRespuesta").toString();
                responseMessage = response.getProperty("mensajeRespuesta").toString();

                //Activar las preguntas de seguridad


                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
                    responsetxt = getString(R.string.web_services_response_00);
                    serviceStatus = true;
                    return serviceStatus;
                }
                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_CORREO_YA_EXISTE)) {
                    responsetxt = getString(R.string.web_services_response_10);
                    serviceStatus = false;
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
                //new CustomToast().Show_Toast(getActivity(), view,
                //        responsetxt);

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
