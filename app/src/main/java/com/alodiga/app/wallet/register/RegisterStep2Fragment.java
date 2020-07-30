package com.alodiga.app.wallet.register;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.alodiga.app.R;
import com.alodiga.app.wallet.login.LoginActivity;
import com.alodiga.app.wallet.duallibrary.model.ObjCountry;
import com.alodiga.app.wallet.utils.CustomToast;
import com.alodiga.app.wallet.utils.ProgressDialogAlodiga;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.utils.SmsReceiver;
import com.alodiga.app.wallet.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterStep2Fragment extends Fragment implements
        OnClickListener {
    public static final String OTP_REGEX = "[0-9]{1,6}";
    static ProgressDialogAlodiga progressDialogAlodiga;
    private static View view;
    private static EditText phoneNumber, edtMobileCode;
    private static Button submit, back;
    Chronometer cmTimer;
    long countUp;
    long startTime = 1000;
    int secondLimit = 120;
    int MAX_TIME = ((int) (SystemClock.elapsedRealtime() - startTime) / 1000) + secondLimit;
    private String getMobileCode = "";
    private ObjCountry objCountry;
    private Integer restCountIntent = 3;

    public RegisterStep2Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_step2_layout, container,
                false);
        initViews();
        setListeners();


        SmsReceiver.bindListener(new SmsReceiver.SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format

                // If your OTP is six digits number, you may use the below code

                Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText);
                String otp = "";
                while (matcher.find()) {
                    otp = matcher.group();
                }


                edtMobileCode.setText(otp);
            }
        });


        return view;
    }

    // Initialize the views
    private void initViews() {
        cmTimer = view.findViewById(R.id.cmTimer);
        submit = view.findViewById(R.id.step1_next_button);
        back = view.findViewById(R.id.backToLoginBtn);
        edtMobileCode = view.findViewById(R.id.edtMobileCode);

        cmTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer arg0) {

                countUp = (SystemClock.elapsedRealtime() - startTime) / 1000;
                int newCount = MAX_TIME - (int) countUp;  // MAIN LOGIC
                String asText = String.format("%02d", (newCount / 60)) + ":"
                        + String.format("%02d", (newCount % 60));
                cmTimer.setText(asText);
                if (countUp > MAX_TIME) {
                    new CustomToast().Show_Toast(getActivity(), view, getString(R.string.register_time_finished));
                    new LoginActivity().replaceLoginFragment();
                }
            }
        });

        cmTimer.start();

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
                new LoginActivity().replaceLoginFragment_step1();
                break;

            case R.id.step1_next_button:
                submitButtonTask();
                break;
        }
    }

    private void submitButtonTask() {

        getMobileCode = edtMobileCode.getText().toString();
        if (getMobileCode.equals("") || getMobileCode.length() == 0)
            new CustomToast().Show_Toast(getActivity(), view, getString(R.string.register_validation_invalid_null_Pin));
        else if (getMobileCode.length() < 6)
            new CustomToast().Show_Toast(getActivity(), view, getString(R.string.register_validation_invalid_long_Pin));
        else {
            if (!getMobileCode.equals(Session.getMobileCodeSms())) {
                restCountIntent--;
                if (restCountIntent == 0) {
                    new CustomToast().Show_Toast(getActivity(), view, getString(R.string.register_validation_limit));
                    new LoginActivity().replaceLoginFragment();
                } else {
                    new CustomToast().Show_Toast(getActivity(), view, getString(R.string.register_validation_mobile_code_not_match) + restCountIntent);
                }


            } else {


                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        //.replace(R.id.frameContainer, new RegisterStep4WelcomeFragment(),
                        .replace(R.id.frameContainer, new RegisterStep3Fragment(),

                                //Utils.RegisterStep4WelcomeFragment).commit();
                                Utils.register_step3_Fragment).commit();


            }
            //checkSecurityCode();
        }
    }


}