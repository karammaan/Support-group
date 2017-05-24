package com.tech.karam.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class expert_login_layout extends AppCompatActivity {

    EditText mobile_et,password_et;
    Button login_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_login_layout);
        login_et = (Button) findViewById(R.id.button_login);

        mobile_et = (EditText) findViewById(R.id.mobile_edit_text);
        password_et = (EditText) findViewById(R.id.password_edit_text);
    }


    public void login(View v) {
        {
            String mobile = mobile_et.getText().toString();
            String password = password_et.getText().toString();

            JSONObject job = new JSONObject();

            try {
                job.put("mobile_key", mobile);
                job.put("password_key", password);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/expert_login.php", job, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if (response.getString("key").equals("done")) {
                            Toast.makeText(expert_login_layout.this, " done ", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor sp = getSharedPreferences("expert_info", MODE_PRIVATE).edit();

                            sp.putString("expert_id", response.getString("expert_id"));

                            sp.commit();

                            SharedPreferences.Editor sp2 = getSharedPreferences("user_info", MODE_PRIVATE).edit();

                            sp2.clear();

                            sp2.commit();

                            Intent i = new Intent(expert_login_layout.this,expert_home_layout.class);

                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(expert_login_layout.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println(error);

                }
            });

            jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

            AppController app = new AppController(expert_login_layout.this);

            app.addToRequestQueue(jobreq);
        }
    }
    public void forgot_pass(View v){
        Intent i = new Intent(expert_login_layout.this , expert_forgot_password.class);

        startActivity(i);
    }
}





