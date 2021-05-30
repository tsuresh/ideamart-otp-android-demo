package com.inforwaves.ideamartotp.ui.verify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inforwaves.ideamartotp.R;
import com.inforwaves.ideamartotp.data.model.Message;
import com.inforwaves.ideamartotp.data.repository.LoginUtil;
import com.inforwaves.ideamartotp.interfaces.OtpCallback;
import com.inforwaves.ideamartotp.ui.login.LoginActivity;

public class VerifyActivity extends AppCompatActivity {

    private LoginUtil loginUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        //Receiving data from previous intent
        Intent intent = getIntent();
        String refNumber = intent.getStringExtra("refNumber");

        final EditText otpCode = findViewById(R.id.otp);
        final Button loginButton = findViewById(R.id.verify);

        loginUtil = new LoginUtil(VerifyActivity.this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs(otpCode)){
                    verifyOtp(otpCode.getText().toString(), refNumber);
                }
            }
        });

    }

    private boolean validateInputs(EditText otpCode){
        //Check if the username field is empty
        if(otpCode.getText().toString().isEmpty()){
            otpCode.setError(getString(R.string.invalid_otp));
            return false;
        }

        // @TODO You can add more validations as such number pattern validation

        return true;
    }

    private void verifyOtp(String otp, String referenceCode){
        loginUtil.verifyOtp(referenceCode, otp, new OtpCallback() {
            @Override
            public void onSuccess(Message message) {
                Toast.makeText(VerifyActivity.this, "Successfully logged in", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Message message) {
                Toast.makeText(VerifyActivity.this, message.getStatusDetail(), Toast.LENGTH_LONG).show();
            }
        });
    }
}