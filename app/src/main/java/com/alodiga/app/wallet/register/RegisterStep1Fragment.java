package com.alodiga.app.wallet.register;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alodiga.app.R;
import com.alodiga.app.wallet.adapters.SpinAdapterCountry;
import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.wallet.model.ObjCountry;
import com.alodiga.app.wallet.utils.Constants;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.Utils;
import com.alodiga.app.wallet.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterStep1Fragment extends Fragment implements
        OnClickListener {
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static View view;
    private static EditText phoneNumber;
    private static TextView submit, back;
    private static Spinner spinnerCountry;
    private static EditText mobileNumber;
    String movilCode;
    private String responsetxt = "";
    private boolean serviceStatus;
    private String getMobileNumber = "";
    private ObjCountry objCountry;
    private RegisterStep1Fragment.UserSendSmsTask mAuthTask = null;

    public RegisterStep1Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_step1_layout, container,
                false);
        initViews();

        List<ObjCountry> countries = new ArrayList<ObjCountry>();
        ObjCountry[] objCountry = new ObjCountry[40];
        objCountry[0] = new ObjCountry("0", "País");
        objCountry[1] = new ObjCountry("1", "Estados Unidos");
        objCountry[2] = new ObjCountry("58", "Venezuela");
        objCountry[3] = new ObjCountry("93", "Afganistán ");
        objCountry[4] = new ObjCountry("355", "Albania ");
        objCountry[5] = new ObjCountry("49", "Alemania ");
        objCountry[6] = new ObjCountry("376", "Andorra ");
        objCountry[7] = new ObjCountry("244", "Angola ");
        objCountry[8] = new ObjCountry("1", "Antigua y Barbuda ");
        objCountry[9] = new ObjCountry("966", "Arabia Saudita");
        objCountry[10] = new ObjCountry("54", "Argentina");
        objCountry[11] = new ObjCountry("374", "Armenia ");
        objCountry[12] = new ObjCountry("43", "Australia ");
        objCountry[13] = new ObjCountry("994", "Azerbaiyán ");
        objCountry[14] = new ObjCountry("1", "Bahamas ");
        objCountry[15] = new ObjCountry("880", "Bangladés ");
        objCountry[16] = new ObjCountry("1", "Barbados ");
        objCountry[17] = new ObjCountry("973", "Baréin ");
        objCountry[18] = new ObjCountry("32", "Bélgica ");
        objCountry[19] = new ObjCountry("501", "Belice ");
        objCountry[20] = new ObjCountry("229", "Benin ");
        objCountry[21] = new ObjCountry("375", "Bielorrusia ");
        objCountry[22] = new ObjCountry("591", "Bolivia ");
        objCountry[23] = new ObjCountry("387", "Bosnia y Herzegovina");
        objCountry[24] = new ObjCountry("267", "Botsuana");
        objCountry[25] = new ObjCountry("55", "Brasil");
        objCountry[26] = new ObjCountry("673", "Brunéi");
        objCountry[27] = new ObjCountry("359", "Bulgaria");
        objCountry[28] = new ObjCountry("226", "Burkina Faso");
        objCountry[29] = new ObjCountry("257", "Burundi");
        objCountry[30] = new ObjCountry("975", "Bután");
        objCountry[31] = new ObjCountry("238", "Cabo Verde");
        objCountry[32] = new ObjCountry("855", "Camboya");
        objCountry[33] = new ObjCountry("237", "Camerún");
        objCountry[34] = new ObjCountry("1", "Canadá");
        objCountry[35] = new ObjCountry("974", "Catar");
        objCountry[36] = new ObjCountry("235", "Chad");
        objCountry[37] = new ObjCountry("56", "Chile");
        objCountry[38] = new ObjCountry("86", "China");
        objCountry[39] = new ObjCountry("357", "Chipre");
        SpinAdapterCountry spinAdapterCountry;
        spinAdapterCountry = new SpinAdapterCountry(this.getContext(), android.R.layout.simple_spinner_item, objCountry);
        spinnerCountry.setAdapter(spinAdapterCountry);

        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        phoneNumber = view.findViewById(R.id.mobileNumberRegister);
        submit = view.findViewById(R.id.step1_next_button);
        back = view.findViewById(R.id.backToLoginBtn);
        spinnerCountry = view.findViewById(R.id.spinnerCountry);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                objCountry = (ObjCountry) spinnerCountry.getSelectedItem();

                if (!objCountry.getId().equals("0")) {
                    phoneNumber.setText("+" + objCountry.getId());
                } else {
                    phoneNumber.setText("");
                }

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


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

        getMobileNumber = phoneNumber.getText().toString();

        if (getMobileNumber.equals("") || getMobileNumber.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view, getString(R.string.register_validation_invalid_long));

            // Validate Country
        else if (objCountry.getId().equals("0"))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Debe seleccionar un Pais");

        else if (getMobileNumber.length() <= 11)
            new CustomToast().Show_Toast(getActivity(), view,
                    "La longitud del telefono en invalida");

        else {
            sendSmsSecurityCode();
        }
    }


    public void sendSmsSecurityCode() {
        progressDialogAlodiga = new ProgressDialogAlodiga(getContext(), "cargando..");
        progressDialogAlodiga.show();
        mAuthTask = new UserSendSmsTask(getMobileNumber);
        mAuthTask.execute((Void) null);

    }

    /**
     * @param phone
     * @return
     */
    public String processPhone(String phone) {
        if (phone != null && phone.charAt(0) == '+')
            phone = phone.substring(1);
        return phone;
    }

    public class UserSendSmsTask extends AsyncTask<Void, Void, Boolean> {

        private final String phone;


        UserSendSmsTask(String phone_) {
            phone = phone_;

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
                map.put("movil", processPhone(phone));


                response = WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS, Constants.REGISTRO_UNIFICADO);
                responseCode = response.getProperty("codigoRespuesta").toString();
                responseMessage = response.getProperty("mensajeRespuesta").toString();


                if (responseCode.equals(Constants.WEB_SERVICES_RESPONSE_CODE_EXITO)) {

                    movilCode = response.getProperty("datosRespuesta").toString();
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
                Session.setMobileCodeSms(movilCode);
                Session.setPhoneNumber(getMobileNumber);
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        //.replace(R.id.frameContainer, new RegisterStep4WelcomeFragment(),
                        .replace(R.id.frameContainer, new RegisterStep2Fragment(),

                                //Utils.RegisterStep4WelcomeFragment).commit();
                                Utils.register_step2_Fragment).commit();

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
            mAuthTask = null;
        }
    }

}