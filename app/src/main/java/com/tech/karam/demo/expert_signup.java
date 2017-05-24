package com.tech.karam.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class expert_signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_signup);


    }
    public void verify_button(View v){
        EditText mobile_id = (EditText) findViewById(R.id.mobile_id);

        String mobile = mobile_id.getText().toString();

        if (mobile.length() < 10) {
            Toast.makeText(expert_signup.this, "please enter valid mobile number", Toast.LENGTH_SHORT).show();

            return;
        }

        int randompin =   (int) (((Math.random())*9000)+1000);

        send_sms(mobile , randompin);

        Intent i = new Intent(expert_signup.this , expert_verify_layout.class);

        i.putExtra("mobile_key" , mobile);

        i.putExtra("pin_key" , randompin);

        startActivity(i);
        finish();

    }

    public void send_sms(String mobile , int pin)
    {
        StringRequest strreq = new StringRequest("https://control.msg91.com/api/sendhttp.php?authkey=128970AkPZGR9Ggo7k5809c7aa&mobiles="+mobile+"&message=Your OTP for support group app verification is"+String.valueOf(pin)+"&sender=SGROUP&route=4&country=91", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        strreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 , 2));

        AppController app = new AppController(expert_signup.this);
        app.addToRequestQueue(strreq);
    }
}
