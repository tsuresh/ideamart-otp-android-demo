package com.inforwaves.ideamartotp.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inforwaves.ideamartotp.R;
import com.inforwaves.ideamartotp.data.model.Message;
import com.inforwaves.ideamartotp.data.repository.LoginUtil;
import com.inforwaves.ideamartotp.exceptions.OtpException;
import com.inforwaves.ideamartotp.interfaces.OtpCallback;
import com.inforwaves.ideamartotp.ui.verify.VerifyActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginUtil loginUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText mobileEditText = findViewById(R.id.phone);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginUtil = new LoginUtil(LoginActivity.this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs(usernameEditText, mobileEditText)){
                    getOtp(mobileEditText.getText().toString());
                }
            }
        });
    }

    private boolean validateInputs(EditText usernameField, EditText mobileField){
        //Check if the username field is empty
        if(usernameField.getText().toString().isEmpty()){
            usernameField.setError(getString(R.string.invalid_username));
            return false;
        }

        // Check if the mobile number field is empty
        if(mobileField.getText().toString().isEmpty()){
            mobileField.setError(getString(R.string.invalid_mobile));
            return false;
        }

        // @TODO You can add more validations as such mobile number pattern validation

        return true;
    }

    private void getOtp(String mobile){
        loginUtil.requestOtp(mobile, new OtpCallback() {
            @Override
            public void onSuccess(Message message) {
                Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                intent.putExtra("refNumber", message.getData());
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Message message) {
                Toast.makeText(LoginActivity.this, message.getStatusDetail(), Toast.LENGTH_LONG).show();
            }
        });
    }

}