package com.tech.karam.demo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.util.Patterns;

public class expert_signup_details extends AppCompatActivity {

    String mobile ;
    EditText username_et , password_et , age_et , email_et, experience_et;
    Spinner expert_in_et,gender_et  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_signup_details);

        mobile= getIntent().getStringExtra("mobile_key");

        username_et=(EditText) findViewById(R.id.username_id);
        gender_et=(Spinner) findViewById(R.id.gender_id);
        password_et=(EditText) findViewById(R.id.password_id);
        age_et=(EditText) findViewById(R.id.age_id);
        experience_et=(EditText) findViewById(R.id.experience_id);
        email_et=(EditText) findViewById(R.id.email_id);
        expert_in_et=(Spinner)findViewById(R.id.expert_id);

        get_groups_names();
        get_experts_gender();
    }

    public void register(View v)
    {
        String username = username_et.getText().toString();

        String gender = gender_et.getSelectedItem().toString();

        String password = password_et.getText().toString();

        String email = email_et.getText().toString();
        String age = age_et.getText().toString();
        String experience = experience_et.getText().toString();
        String expert_in= expert_in_et.getSelectedItem().toString();

        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";

        if(username.length() < 4 || !username.matches("[a-zA-Z ]+"))
        {

            Toast.makeText(expert_signup_details.this, "name must be 4 character long and not contain any digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if(gender.equals(""))
        {
            Toast.makeText(expert_signup_details.this , "please enter gender" , Toast.LENGTH_SHORT).show();

            return;
        }
        if(!password.matches(pattern) || password.length() < 8)
        {
            Toast.makeText(expert_signup_details.this, "password must contain atleast one alphabet , digit , special character and length must be 8 character", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.contains("_"))
        {
            Toast.makeText(expert_signup_details.this , "please enter valid email type" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(age.equals(""))
        {
            Toast.makeText(expert_signup_details.this , "please enter age" , Toast.LENGTH_SHORT).show();

            return;
        }
        if(experience.equals(""))
        {
            Toast.makeText(expert_signup_details.this , "please enter experience" , Toast.LENGTH_SHORT).show();

            return;
        }
        if(expert_in.equals(""))
        {
            Toast.makeText(expert_signup_details.this , "please enter expert in field" , Toast.LENGTH_SHORT).show();

            return;
        }

        JSONObject job = new JSONObject();

        try {
            job.put("name_key" , username);
            job.put("mobile_key", mobile);
            job.put("pass_key", password);
            job.put("gender_key" , gender);
            job.put("email_key",email);
            job.put("age_key",age);
            job.put("experience_key",experience);
            job.put("expert_key",expert_in);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Internet_address.ip+"/expert_signup_details.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("1"))
                    {

                        SharedPreferences.Editor sp = getSharedPreferences("expert_info",MODE_PRIVATE).edit();

                        sp.putString("expert_id",response.getString("expert_id"));

                        sp.commit();

                        SharedPreferences.Editor sp2 = getSharedPreferences("expert_info", MODE_PRIVATE).edit();

                        sp2.clear();

                        sp2.commit();

                        Intent i = new Intent(expert_signup_details.this , expert_signup_detail_2.class);
                        startActivity(i);
                        finish();
                    }



                    else {


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

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 , 2));

        AppController app = new AppController(expert_signup_details.this);
        app.addToRequestQueue(jobreq);



    }


    public void get_groups_names()
    {
        JSONObject job = new JSONObject();
        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Internet_address.ip+"/get_group_names.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    List<String> name_list = new ArrayList<>();

                    JSONArray jarr = response.getJSONArray("result");

                    for(int i = 0 ; i < jarr.length() ; i++)
                    {
                        JSONObject job = jarr.getJSONObject(i);
                        String name = job.getString("Group_name");
                        name_list.add(name);
                    }
                    ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(expert_signup_details.this, android.R.layout.simple_dropdown_item_1line,name_list);

                    expert_in_et.setAdapter(adapter_spinner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2, 2));

        AppController app = new AppController(expert_signup_details.this);
        app.addToRequestQueue(jobreq);
    }

    public void get_experts_gender()
    {
        JSONObject job = new JSONObject();
        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Internet_address.ip+"/get_expert_gender.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    List<String> name_list = new ArrayList<>();

                    JSONArray jarr = response.getJSONArray("result");

                    for(int i = 0 ; i < jarr.length() ; i++)
                    {
                        JSONObject job = jarr.getJSONObject(i);
                        String name = job.getString("Gender");
                        name_list.add(name);
                    }
                    ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(expert_signup_details.this, android.R.layout.simple_dropdown_item_1line,name_list);

                    gender_et.setAdapter(adapter_spinner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2, 2));

        AppController app = new AppController(expert_signup_details.this);
        app.addToRequestQueue(jobreq);
    }
}


