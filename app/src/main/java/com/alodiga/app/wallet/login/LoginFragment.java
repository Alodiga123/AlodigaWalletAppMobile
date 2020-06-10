package com.alodiga.app.wallet.login;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alodiga.app.R;
import com.alodiga.app.wallet.forgotPassword.ForgotPasswordStep1Fragment;
import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.wallet.model.ObjDireccion;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.register.RegisterStep1Fragment;
import com.alodiga.app.wallet.securityQuestion.SecurityQuestionStep1Fragment;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements OnClickListener {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static View view;
    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    ArrayList<ObjUserHasProduct> userHasProducts = new ArrayList<ObjUserHasProduct>();
    String nameSession = "";
    String name;
    String lastName;
    String lastNameSession = "";
    String phoneNumberSession = "";
    String emailSession = "";
    String alodigaBalanceSession = "";
    String accountNumberSession = "";
    String alocoinsBalanceSesssion = "";
    String healthCareCoinsBalanceSession = "";
    String userId = "";
    String cumplimient= "";
    String prepayCard= "";
    String numberCard="";
    String prepayCardAsociate="";
    private boolean serviceStatus;
    private boolean isFirstAccess = false;
    private View mLoginFormView;
    private View mProgressView;
    private String responsetxt = "";
    private String getEmailId;
    private String getPassword;
    private UserLoginTask mAuthTask = null;

    public LoginFragment() {

    }

    private static ArrayList<ObjUserHasProduct> getElementsProduct(String elementGet, String response) {
        ArrayList<ObjUserHasProduct> objUserHasProducts = new ArrayList<ObjUserHasProduct>();
        String elementgetId = "id=";
        String elementGetName = "nombreProducto=";
        String elementGetCurrentBalance = "saldoActual=";
        String elementGetSymbol = "simbolo=";
        String litaProd = "respuestaListadoProductos=";
        String isTopup = "isPayTopUP=";



        for (int i = 1; i < getLenghtFromResponseJson(litaProd, response); i++) {
            ObjUserHasProduct objUserHasProduct = new ObjUserHasProduct(response.split(elementgetId)[i].split(";")[0], response.split(elementGetName)[i].split(";")[0], response.split(elementGetCurrentBalance)[i].split(";")[0], response.split(elementGetSymbol)[i].split(";")[0],response.split(isTopup)[i].split(";")[0]);

            if (objUserHasProduct.getName().equals("Tarjeta Prepagada") || objUserHasProduct.getName().equals("Prepaid Card") ){
                Session.setAffiliatedCard(Boolean.parseBoolean(Session.getPrepayCardAsociate()));
                objUserHasProduct.setNumberCard(Session.getNumberCard());
            }else{
                objUserHasProduct.setNumberCard(Session.getAccountNumber());
            }

            objUserHasProducts.add(objUserHasProduct);
        }

        return objUserHasProducts;
    }

    private static String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }

    private static Integer getLenghtFromResponseJson(String v, String response) {
        return (response.split(v).length);
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
        emailid = view.findViewById(R.id.login_emailid);
        password = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.loginBtn);
        forgotPassword = view.findViewById(R.id.forgot_password);
        signUp = view.findViewById(R.id.createAccount);
        show_hide_password = view
                .findViewById(R.id.show_hide_password);
        loginLayout = view.findViewById(R.id.login_layout);
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
		/*emailid.setText("kerwin2821@gmail.com");
		password.setText("Kg0m3z$11");*/
        //emailid.setText("adira0411@gmail.com");

        //password.setText("123456");
        //password.setText("Alo$1234");

        //emailid.setText("jcalderaso@gmail.com");
        //password.setText("Alo#1234");

        emailid.setText("moisegrat12@gmail.com");
        password.setText("Alodi12-");

        //emailid.setText("kerwin2821@gmail.com");
        //password.setText("Alo#1234");

        //emailid.setText("gomezvadriana@gmail.com");
        //password.setText("Alo#1234");

        //emailid.setText("dalonso@alodiga.com");
        //password.setText("Alo#1234");

        //emailid.setText("elmoi_88@hotmail.com");
        //password.setText("Alo$1234");

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
                                new ForgotPasswordStep1Fragment(),
                                Utils.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:

                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new RegisterStep1Fragment(),
                                Utils.Register_step_1).commit();

                break;

        }

    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        getEmailId = emailid.getText().toString();
        //getPassword = password.getText().toString();

        getPassword = Utils.aloDesencript(password.getText().toString());
        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not

        if (getPassword.equals(Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.web_services_response_99));
        } else if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.enter_both_credentials));

        }
        // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.email_invalid));
            // Else do login and do your stuff
        else entrar();

    }

    public void entrar() {


        progressDialogAlodiga = new ProgressDialogAlodiga(getContext(), getString(R.string.loading_session));
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

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("email", mEmail);
                map.put("credencial", mPassword);
                map.put("ip", "192.168.3.20");
                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_LOGIN_APP_MOBILE, Constants.REGISTRO_UNIFICADO);
                responseCode = response.getProperty("codigoRespuesta").toString();
                responseMessage = response.getProperty("mensajeRespuesta").toString();

                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO) || responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO)) {
                    String res = response.getProperty("datosRespuesta").toString();
                    SoapObject res_ = (SoapObject) response.getProperty("datosRespuesta");
                    name = getValueFromResponseJson("nombre", res) ;
                    lastName= getValueFromResponseJson("apellido", res);
                    nameSession = getValueFromResponseJson("nombre", res) + " " + getValueFromResponseJson("apellido", res);
                    phoneNumberSession = getValueFromResponseJson("movil", res);
                    emailSession = getValueFromResponseJson("email", res);
                    alodigaBalanceSession = getValueFromResponseJson("saldoAlodiga", res);
                    accountNumberSession = getValueFromResponseJson("numeroCuenta", res);
                    alocoinsBalanceSesssion = getValueFromResponseJson("saldoAlocoins", res);
                    healthCareCoinsBalanceSession = getValueFromResponseJson("saldoHealthCareCoins", res);
                    userId = getValueFromResponseJson("UsuarioID", res);
                    String direccion = res_.getProperty("direccion").toString();
                    ObjDireccion direccion_ = new ObjDireccion(getValueFromResponseJson("ciudadId", direccion),
                            getValueFromResponseJson("codigoPostal", direccion),
                            getValueFromResponseJson("condadoId", direccion),
                            getValueFromResponseJson("direccion", direccion),
                            getValueFromResponseJson("direccionId", direccion),
                            getValueFromResponseJson("estadoId", direccion),
                            getValueFromResponseJson("paisId", direccion));
                    Session.setDireccionUsuario(direccion_);
                    Session.setRemettencesDireccionId(getValueFromResponseJson("remettencesDireccionId", res)) ;
                    Session.setName(name);
                    Session.setLastname(lastName);


                    if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO)){
                        cumplimient = "4";
                        prepayCardAsociate= "false";
                        prepayCard="false";


                    }else{
                        cumplimient = getValueFromResponseJson("cumplimient", res);
                        //prepayCardAsociate = getValueFromResponseJson("prepayCardAsociate", res);
                        prepayCardAsociate = getValueFromResponseJson("prepayCardAsociate", res);
                        prepayCard= getValueFromResponseJson("prepayCard", res);
                        if(Boolean.valueOf(prepayCard)){
                            numberCard = getValueFromResponseJson("numberCard", res);
                            Session.setNumberCard(numberCard);
                            Session.setNumberCard_aux(numberCard);
                        }
                    }



                    Session.setAccountNumber(accountNumberSession);
                    Session.setAccountNumber_aux(accountNumberSession);
                    Session.setPrepayCardAsociate(prepayCardAsociate);


                    userHasProducts = getElementsProduct("", res);

                    if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {
                        responsetxt = getString(R.string.web_services_response_00);
                        serviceStatus = true;
                        return serviceStatus;
                    } else {
                        responsetxt = getString(R.string.web_services_response_12);
                        isFirstAccess = true;
                        serviceStatus = false;
                    }

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
         //return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);
            if (success) {
                emailid.setText("");
                password.setText("");

                //setElementInitialSession("prueba", "0414", "adi123", "0.0", "00", "000", "379", "000", userHasProducts, "0", "00", "00", "0000");

                setElementInitialSession(nameSession, phoneNumberSession, emailSession, alodigaBalanceSession, accountNumberSession, alocoinsBalanceSesssion, userId, healthCareCoinsBalanceSession, userHasProducts, cumplimient, prepayCard, prepayCardAsociate, numberCard);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            } else if (isFirstAccess) {
                setElementInitialSession(nameSession, phoneNumberSession, emailSession, alodigaBalanceSession, accountNumberSession, alocoinsBalanceSesssion, userId, healthCareCoinsBalanceSession, userHasProducts, cumplimient, prepayCard, prepayCardAsociate, numberCard);
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SecurityQuestionStep1Fragment(),
                                Utils.SecurityQuestion_Fragment).commit();
            } else {
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


        private void setElementInitialSession(String nameSession, String phoneNumberSession, String emailSession, String alodigaBalanceSession, String accountNumberSession, String alocoinsBalanceSesssion, String userId, String healthCareCoinsBalanceSession, ArrayList<ObjUserHasProduct> objUserHasProducts, String cumplimient, String prepayCard, String prepayCardAsociate, String numberCard) {

            Session.setObjUserHasProducts(objUserHasProducts);
            Session.setUsername(nameSession);
            Session.setPhoneNumber(phoneNumberSession);
            Session.setEmail(emailSession);
            Session.setAlodigaBalance(alodigaBalanceSession);
            Session.setAccountNumber(accountNumberSession);
            Session.setAlocoinsBalance(alocoinsBalanceSesssion);
            Session.setUserId(userId);
            Session.setHealthCareCoinsBalance(healthCareCoinsBalanceSession);
            Session.setCumplimient(cumplimient);
            Session.setPrepayCard(prepayCard);
            Session.setPrepayCardAsociate(prepayCardAsociate);
            Session.setNumberCard(numberCard);
            Session.setNumberCard_aux(numberCard);
        }
    }


}
