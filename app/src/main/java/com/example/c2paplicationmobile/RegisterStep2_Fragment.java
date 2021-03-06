package com.example.c2paplicationmobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class RegisterStep2_Fragment extends Fragment implements
		OnClickListener {
	private static View view;
	private static EditText phoneNumber,edtMobileCode;
	private static TextView submit, back;
	Chronometer cmTimer;
	static ProgressDialogAlodiga progressDialogAlodiga;
	private String getMobileCode = "";
	private ObjCountry objCountry;
	private Integer restCountIntent = 3;

	long countUp;
	long startTime = 1000;
	int secondLimit = 120;
	int MAX_TIME =((int) (SystemClock.elapsedRealtime() - startTime) / 1000) + secondLimit;

	public RegisterStep2_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.register_step2_layout, container,
				false);
		initViews();
		setListeners();
		return view;
	}

	// Initialize the views
	private void initViews() {
		cmTimer = (Chronometer) view.findViewById(R.id.cmTimer);
		submit = (TextView) view.findViewById(R.id.step1_next_button);
		back = (TextView) view.findViewById(R.id.backToLoginBtn);
		edtMobileCode = (EditText) view.findViewById(R.id.edtMobileCode);

		cmTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			public void onChronometerTick(Chronometer arg0) {

				countUp = (SystemClock.elapsedRealtime() - startTime) / 1000;
				int newCount = MAX_TIME - (int) countUp;  // MAIN LOGIC
				String asText = String.format("%02d",(newCount / 60)) + ":"
						+ String.format("%02d", (newCount % 60));
				cmTimer.setText(asText);
				if (countUp > MAX_TIME) {
					new CustomToast().Show_Toast(getActivity(), view,getString(R.string.register_time_finished));
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
		if ( getMobileCode.equals("") || getMobileCode.length() == 0)
			new CustomToast().Show_Toast(getActivity(), view,getString(R.string.register_validation_invalid_null_Pin));
		else if (getMobileCode.length() < 6)
			new CustomToast().Show_Toast(getActivity(), view,getString(R.string.register_validation_invalid_long_Pin));
		else{
			if(!getMobileCode.equals(Session.getMobileCodeSms())){
				restCountIntent--;
				if(restCountIntent==0){
					new CustomToast().Show_Toast(getActivity(), view,getString(R.string.register_validation_limit));
					new LoginActivity().replaceLoginFragment();
				}else{
					new CustomToast().Show_Toast(getActivity(), view,getString(R.string.register_validation_mobile_code_not_match) + restCountIntent);
				}


			}else{


				getFragmentManager()
						.beginTransaction()
						.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
						//.replace(R.id.frameContainer, new Welcome_Fragment(),
						.replace(R.id.frameContainer, new RegisterStep3_Fragment(),

								//Utils.Welcome_Fragment).commit();
								Utils.register_step3_Fragment).commit();


			}
			//checkSecurityCode();
		}
	}










}