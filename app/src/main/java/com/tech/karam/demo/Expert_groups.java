package com.tech.karam.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Expert_groups extends AppCompatActivity {

    RecyclerView recycle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_groups);

        recycle = (RecyclerView) findViewById(R.id.expert_recycle);
        recycle.setLayoutManager(new LinearLayoutManager(Expert_groups.this , LinearLayoutManager.VERTICAL, false));

        get_data();
    }

    public  void get_data()
    {


        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/get_all_expert_groups.php", new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    JSONArray jarr = response.getJSONArray("result");

                    System.out.println(jarr);

                    expert_group_adapter ad = new expert_group_adapter(jarr , Expert_groups.this);

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

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2  , 2));

        AppController app = new AppController(Expert_groups.this);

        app.addToRequestQueue(jobreq);
    }

    public static void group_join(String group_id , final Activity a )  {

        JSONObject job = new JSONObject();

        SharedPreferences sp = a.getSharedPreferences("user_info", MODE_PRIVATE);
        String userid = sp.getString("user_id","");

        try {
            job.put("group_id" , group_id);
            job.put("user_id" , userid);
            job.put("type" , "expert");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/join_groups.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    if(response.getString("key").equals("done"))
                    {
                        Toast.makeText(a , "group joined" , Toast.LENGTH_SHORT).show();
                    }

                    else if(response.getString("key").equals("not done"))
                    {
                        Toast.makeText(a , "already joined" , Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(a , "error" , Toast.LENGTH_SHORT).show();
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

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2  , 2));

        AppController app = new AppController(a);

        app.addToRequestQueue(jobreq);
    }

}
