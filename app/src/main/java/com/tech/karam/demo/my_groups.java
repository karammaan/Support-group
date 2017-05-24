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

public class my_groups extends AppCompatActivity {
    EditText group_name , group_cause , description;
    Button createe_group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);
        createe_group = (Button)  findViewById(R.id.create_button);
        group_name = (EditText) findViewById(R.id.first_edit_text);

        group_cause=(EditText) findViewById(R.id.second_edit_text);

        description=(EditText) findViewById(R.id.third_edit_text);

    }

    public void button(View v){
       String g_name =   group_name.getText().toString();
        String g_cause =  group_cause.getText().toString();
        String g_description =  description.getText().toString();

        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String userid = sp.getString("user_id","");


        try {
            jobj.put("name_key" , g_name);
            jobj.put("cause_key" , g_cause);
            jobj.put("description_key" , g_description);
            jobj.put("user_id", userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jobj);

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Internet_address.ip+"/add_group.php", jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

                try {
                    if( response.getString("key").equals("done"))
                    {


                        finish();
                        Toast.makeText(my_groups.this, "group created successfully", Toast.LENGTH_SHORT).show();

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

        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 , 2));

        AppController app = new AppController(my_groups.this);

        app.addToRequestQueue(jobjreq);



    }
}
