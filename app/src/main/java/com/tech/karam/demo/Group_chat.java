package com.tech.karam.demo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Group_chat extends AppCompatActivity {

    public TextView group_name ;
    EditText chat_et;
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        chat_et=(EditText)  findViewById(R.id.chat_et);
        recycle=(RecyclerView) findViewById(R.id.recycler_id);

        recycle.setLayoutManager(new LinearLayoutManager(Group_chat.this , LinearLayoutManager.VERTICAL , false));

        group_name = (TextView)  findViewById(R.id.group_name);
        String name = getIntent().getStringExtra("group_name");

        group_name.setText(name);

        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {
                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)

                                      get_chat();
                                  }

                              },
//Set how long before to start calling the TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                4000);

    }
    public void close(View v){
        finish();

    }
    public void send_chat(View v){

        String chat = chat_et.getText().toString();

        String group_id = getIntent().getStringExtra("group_id");
        String user_id ="";
        String by = "";



            SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);

             user_id = sp.getString("user_id", "");
        if(user_id.equals(""))
        {
            SharedPreferences sp2 = getSharedPreferences("expert_info", MODE_PRIVATE);

            user_id = sp2.getString("expert_id", "");
            by = "expert";

        }
        else {
            by = "user";

        }





        JSONObject job = new JSONObject();
        try {
            job.put("chat_key", chat);
            job.put("group_id" , group_id);
            job.put("user_id" , user_id);
            job.put("type" , getIntent().getStringExtra("type"));
            job.put("by" , by);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println();
        JsonObjectRequest jobreq =new JsonObjectRequest("http://" + Internet_address.ip + "/add_chat.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("key").equals("1")){
                        Toast.makeText(Group_chat.this,"message sent",Toast.LENGTH_SHORT).show();

                        chat_et.getText().clear();
                        get_chat();
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

        AppController app = new AppController(Group_chat.this);
        app.addToRequestQueue(jobreq);




    }


    public void get_chat()
    {

        String group_id = getIntent().getStringExtra("group_id");

        JSONObject job = new JSONObject();
        try {

            job.put("group_id" , group_id);
            job.put("type" , getIntent().getStringExtra("type"));


        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq =new JsonObjectRequest("http://" + Internet_address.ip + "/get_group_chat.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jarr  = response.getJSONArray("result");

                    group_chat_adapter ad = new group_chat_adapter(jarr , Group_chat.this);

                    recycle.setAdapter(ad);


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

        AppController app = new AppController(Group_chat.this);
        app.addToRequestQueue(jobreq);



    }
}
