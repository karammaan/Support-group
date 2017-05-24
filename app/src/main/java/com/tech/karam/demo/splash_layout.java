package com.tech.karam.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class splash_layout extends AppCompatActivity {
    private Button login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        login= (Button) findViewById(R.id.button_login);
        signup = (Button) findViewById(R.id.button_signup);

        final View.OnClickListener onbtn_click= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.button_login)
                {
                    Intent i = new Intent(splash_layout.this , login_layout.class);


                    startActivity(i);


                }

              if (v.getId()==R.id.button_signup)
                {
                    Intent i =new Intent(splash_layout.this , sign_up_layout.class);
                    startActivity(i);
                }
            }
        };
        login.setOnClickListener(onbtn_click);
        signup.setOnClickListener(onbtn_click);
    }
    public void signup(View v){
        Intent i = new Intent(splash_layout.this , expert_signup.class);


        startActivity(i);
    }
    public void login(View v){
        Intent i = new Intent(splash_layout.this , expert_login_layout.class);


        startActivity(i);
    }
}
