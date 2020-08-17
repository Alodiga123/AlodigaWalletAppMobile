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

import com.alodiga.app.R;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;

import org.ksoap2.serialization.SoapObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.alodiga.app.wallet.duallibrary.register.RegisterController.saveUser;

public class RegisterStep3Fragment extends Fragment implements OnClickListener {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static View view;
    private static EditText name, lastName, emailId, mobileNumber, location,
            password, confirmPassword, pinNumber;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private String responsetxt = "";
    private boolean serviceStatus;
    private String getname = "";
    private String getLastName = "";
    private String getEmailId = "";
    private String getPinNumber = "";
    private String getPassword = "";
    private String getConfirmPassword = "";

    private RegisterStep3Fragment.UserRegisterTask mAuthTask = null;

    public RegisterStep3Fragment() {
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
        name = view.findViewById(R.id.name);
        lastName = view.findViewById(R.id.lastName);
        emailId = view.findViewById(R.id.userEmailId);
        password = view.findViewById(R.id.password);
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

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        int messageForToast= Utils.validatePassword(getPassword, getConfirmPassword);

        // Check if all strings are null or not
        if (getname.equals("") || getname.length() == 0
                || getLastName.equals("") || getLastName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getPinNumber.equals("") || getPinNumber.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.invalid_all_question));

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.email_invalid));

        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.acept_ter));


        else if (getPinNumber.length() < 4)
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.pin_invalid));

        else if(!getPassword.trim().equals("") && !getConfirmPassword.trim().equals("") && messageForToast!= 0)
        {
            int msj= 0;
            switch (messageForToast){
                case 1:
                    msj= R.string.validate_password_change_capital_letter;
                    break;
                case 2:
                    msj= R.string.validate_password_change_lowercase_letter;
                    break;
                case 3:
                    msj= R.string.validate_password_change_number;
                    break;
                case 4:
                    msj= R.string.validate_password_change_special_character;
                    break;
                case 5:
                    msj= R.string.validate_password_change_number_characters;
                case 6:
                    msj= R.string.toast_different_passwords;
            }

            new CustomToast().Show_Toast(getActivity(), view,
                        getString(msj));

        }

        // Else do signup or do your stuff
        else
            registrar();

    }

    public void registrar() {
        progressDialogAlodiga = new ProgressDialogAlodiga(getContext(), getString(R.string.loading));
        progressDialogAlodiga.show();

        getPassword = Utils.aloDesencript(getPassword);
        getPinNumber = Utils.aloDesencript(getPinNumber);


        mAuthTask = new UserRegisterTask(getname, getLastName, getEmailId, Session.getPhoneNumber(), getPassword, getPinNumber, Session.getMobileCodeSms());
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


        UserRegisterTask(String name_, String lastName_, String email_, String phoneNumber_, String password_, String pin_, String mobileCodeValidation_) {
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

            try{
                SoapObject response = saveUser( name,  lastName,  password,  email,  phoneNumber,  mobileCodeValidation,  pin);
                String responseCode = response.getProperty("codigoRespuesta").toString();

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
            if (success) {
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .replace(R.id.frameContainer, new RegisterStep4WelcomeFragment(),
                                Utils.Welcome_Fragment).commit();
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
