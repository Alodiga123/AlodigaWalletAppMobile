package com.alodiga.app.wallet.securityQuestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alodiga.app.wallet.main.MainActivity;
import com.alodiga.app.R;
import com.alodiga.app.wallet.utils.Session;
import com.alodiga.app.wallet.login.LoginActivity;

public class SecurityQuestionStep2WelcomeFragment extends Fragment implements
        View.OnClickListener {
    private static TextView submit, back;
    private static View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.welcome_secure_answer_layout, container, false);
        initViews();
        setListeners();
        return view;
    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_secure_answer_layout);
        initViews();
        setListeners();
    }*/

    private void initViews() {
        submit = (TextView) view.findViewById(R.id.step1_next_button);
        back = (TextView) view.findViewById(R.id.backToLoginBtn);

    }

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

    private void submitButtonTask() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }

}

