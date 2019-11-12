package com.alodiga.app.wallet.register;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alodiga.app.R;
import com.alodiga.app.wallet.login.LoginActivity;

public class RegisterStep4WelcomeFragment extends Fragment implements OnClickListener {
    private static View view;

    private static TextView TextWelcome;
    private static Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.welcome_layout, container, false);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    new LoginActivity().replaceLoginFragment();
                    return true;
                }
                return false;
            }
        });
        initViews();
        return view;
    }


    // Initialize all views
    private void initViews() {
        login = view.findViewById(R.id.already_user_);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.already_user_:
                // Replace login fragment
                new LoginActivity().replaceLoginFragment();
                break;
        }

    }


}
