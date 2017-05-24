package com.tech.karam.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class expert_home_layout extends AppCompatActivity {


    DrawerLayout drawer;
    TextView chat , description , cause, name ;

    String name_s , group_id_s ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_home_layout);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        name =(TextView) findViewById(R.id.group_name_id);
        cause=(TextView) findViewById(R.id.group_cause_id);
        description =(TextView) findViewById(R.id.group_description_id);
        chat = (TextView) findViewById(R.id.chat);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( expert_home_layout.this, Group_chat.class);


                    i.putExtra("group_name" ,name_s );
                    i.putExtra("group_id" ,group_id_s );
                    i.putExtra("type" , "expert");

              startActivity(i);
            }
        });

        get_group();
    }

    public void open_drawer(View v) {

        drawer.openDrawer(Gravity.LEFT);
    }

    public void open_profile(View v) {
        Intent i = new Intent(expert_home_layout.this, expert_profile_update.class);

        startActivity(i);
    }

    public void get_group()
    {
        SharedPreferences sp = getSharedPreferences("expert_info",MODE_PRIVATE);

        String expert_id =  sp.getString("expert_id","");

        JSONObject jobj  = new JSONObject();

        try {
            jobj.put("expert_id" , expert_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/get_joined_expert_groups.php", jobj , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                   JSONObject job = response.getJSONObject("result");


                    name.setText("Group Name : "+job.getString("Group_name"));
                    cause.setText("Group Cause : "+job.getString("Group_cause"));

                    description.setText("About : "+job.getString("Description"));

                    name_s = job.getString("Group_name");
                    group_id_s = job.getString("Group_id");

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

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2  , 2));

        AppController app = new AppController(expert_home_layout.this);

        app.addToRequestQueue(jobreq);
    }
    public void open_feedback(View v){
        Intent i = new Intent(expert_home_layout.this, expert_feedback.class);

        startActivity(i);
    }
    public void share_app(View view) {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Support Group");
            String sAux = "\nHey download this amazing app for helping people goin through tough situations\n\n";
            sAux = sAux + " download link \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
    public void sign_out(View v){
        finish();

    }
}
