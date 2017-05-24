package com.tech.karam.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class all_group extends AppCompatActivity {

    RecyclerView recycle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_group);

        recycle = (RecyclerView) findViewById(R.id.all_group_recycle);
        recycle.setLayoutManager(new LinearLayoutManager(all_group.this , LinearLayoutManager.VERTICAL, false));

        get_data();

}

    public  void get_data()
    {


        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/get_all_groups.php", new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    JSONArray jarr = response.getJSONArray("result");

                   System.out.println(jarr);

                    all_group_adapter ad = new all_group_adapter(jarr , all_group.this);

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

        AppController app = new AppController(all_group.this);

        app.addToRequestQueue(jobreq);
    }


    public static void group_join(String group_id , final Activity a )  {

        JSONObject job = new JSONObject();

        SharedPreferences sp = a.getSharedPreferences("user_info", MODE_PRIVATE);
        String userid = sp.getString("user_id","");

        try {
            job.put("group_id" , group_id);
            job.put("user_id" , userid);
            job.put("type" , "user");

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

    public static void report_group (String group_id , final Activity a )  {

        JSONObject job = new JSONObject();

        SharedPreferences sp = a.getSharedPreferences("user_info", MODE_PRIVATE);
        String userid = sp.getString("user_id","");

        try {
            job.put("group_id" , group_id);
            job.put("user_id" , userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);
        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/report_group.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    if(response.getString("key").equals("done"))
                    {
                        Toast.makeText(a , "group reported" , Toast.LENGTH_SHORT).show();

                    }

                    else if(response.getString("key").equals("not done"))
                    {
                        Toast.makeText(a , "already reported" , Toast.LENGTH_SHORT).show();
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
                System.out.println(error);

            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2  , 2));

        AppController app = new AppController(a);

        app.addToRequestQueue(jobreq);
    }

    public void close(View view) {

        finish();
    }
}





