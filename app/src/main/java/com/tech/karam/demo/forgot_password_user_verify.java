package com.tech.karam.demo;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class forgot_password_user_verify extends AppCompatActivity {

    int pin ;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_user_verify);

        pin = getIntent().getIntExtra("pin_key" , 0);

        Toast.makeText(forgot_password_user_verify.this , "enter otp sent to mobile" , Toast.LENGTH_SHORT).show();

        final EditText otp1=(EditText) findViewById(R.id.otp1);
        final EditText otp2=(EditText) findViewById(R.id.otp2);
        final EditText otp3=(EditText) findViewById(R.id.otp3);
        final EditText otp4=(EditText) findViewById(R.id.otp4);

        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() == 1)
                {
                    otp1.clearFocus();
                    otp2.requestFocus();
                    otp2.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1) {
                    otp2.clearFocus();
                    otp3.requestFocus();
                    otp3.setCursorVisible(true);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()==1) {
                    otp3.clearFocus();
                    otp4.requestFocus();
                    otp4.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() == 1)
                {
                    String value1 = otp1.getText().toString();
                    String value2 = otp2.getText().toString();
                    String value3 = otp3.getText().toString();
                    String value4 = otp4.getText().toString();

                    String all_value = value1+value2+value3+value4;

                    if( Integer.parseInt(all_value) == pin )

                    {

                        Intent i =new Intent(forgot_password_user_verify.this ,new_password_user.class);

                        i.putExtra("mobile_key" , getIntent().getStringExtra("mobile_key"));

                        startActivity(i);
                        finish();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}


