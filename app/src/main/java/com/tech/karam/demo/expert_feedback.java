package com.tech.karam.demo;

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

public class expert_feedback extends AppCompatActivity {
    EditText message_et;
    Button btn_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_feedback);
        message_et = (EditText) findViewById(R.id.feedback_id);
        btn_et = (Button) findViewById(R.id.message_id);
    }
    public void send_message(View v){


        String message = message_et.getText().toString();

        SharedPreferences sp = getSharedPreferences("expert_info",MODE_PRIVATE);

        String expert_id =  sp.getString("expert_id","");

        JSONObject job = new JSONObject();
        try {
            job.put("message_key", message);
            job.put("expert_id" , expert_id);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq =new JsonObjectRequest("http://" + Internet_address.ip + "/get_expert_feedback.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("key").equals("1")){
                        Toast.makeText(expert_feedback.this,"message sent",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 , 2));

        AppController app = new AppController(expert_feedback.this);
        app.addToRequestQueue(jobreq);



    }
}

