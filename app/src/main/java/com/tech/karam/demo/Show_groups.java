package com.tech.karam.demo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Show_groups extends AppCompatActivity {

    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_groups);

        recycle = (RecyclerView) findViewById(R.id.recycler_id);
        recycle.setLayoutManager(new LinearLayoutManager(Show_groups.this , LinearLayoutManager.VERTICAL, false));


        get_data();
    }

    public void get_data()
    {
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);

        String user_id =  sp.getString("user_id","");

        JSONObject jobj  = new JSONObject();

        try {
            jobj.put("user_id" , user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/get_groups.php", jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    JSONArray jarr = response.getJSONArray("result");

                    groups_adapter adapter = new groups_adapter(jarr , Show_groups.this);


                    recycle.setAdapter(adapter);

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

        AppController app = new AppController(Show_groups.this);

        app.addToRequestQueue(jobreq);

    }

    public void close(View view) {

        finish();
    }
}
