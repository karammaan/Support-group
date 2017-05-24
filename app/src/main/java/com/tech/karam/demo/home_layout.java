package com.tech.karam.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class home_layout extends AppCompatActivity {
    RecyclerView recycle;

    DrawerLayout drawer ;
    LinearLayout open_prof, open_al_grp, open_my_grp, open_expert_grp, open_fedbck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
         open_prof= (LinearLayout) findViewById(R.id.profile_layout);
         open_al_grp= (LinearLayout) findViewById(R.id.all_groups_layout);
        open_my_grp= (LinearLayout) findViewById(R.id.my_group_layout);
        open_expert_grp= (LinearLayout) findViewById(R.id.expert_groups_layout);
        open_fedbck= (LinearLayout) findViewById(R.id.feedback_layout);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        recycle =(RecyclerView) findViewById(R.id.joined_group_recycler);
        recycle.setLayoutManager(new LinearLayoutManager(home_layout.this , LinearLayoutManager.VERTICAL, false));
        get_data();


        final View.OnClickListener onbtn_click= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.profile_layout)
                {

                    Intent i = new Intent(home_layout.this , profile_edit_layout.class);


                    startActivity(i);

                    drawer.closeDrawer(Gravity.LEFT);


                }
                if(v.getId() == R.id.all_groups_layout)
                {

                    Intent i = new Intent(home_layout.this ,all_group.class);


                    startActivity(i);

                    drawer.closeDrawer(Gravity.LEFT);


                }



            }
        };

        open_prof.setOnClickListener(onbtn_click);
       open_al_grp.setOnClickListener(onbtn_click);
    }
    public void button_press(View v){
        Intent i = new Intent(home_layout.this ,my_groups.class);

        startActivity(i);

        drawer.closeDrawer(Gravity.LEFT);
    }

    public void open_groups(View v){
        Intent i = new Intent(home_layout.this ,Show_groups.class);

        startActivity(i);

        drawer.closeDrawer(Gravity.LEFT);
    }


    public  void get_data()
    {

        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);

        String user_id =  sp.getString("user_id","");

        JSONObject jobj  = new JSONObject();

        try {
            jobj.put("user_id" , user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/get_joined_groups.php", jobj , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    JSONArray jarr = response.getJSONArray("result");

                    System.out.println(jarr);

                    joined_group_adapter ad = new joined_group_adapter(jarr , home_layout.this);

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

        AppController app = new AppController(home_layout.this);

        app.addToRequestQueue(jobreq);
    }


    public void open_drawer(View v)
    {
        drawer.openDrawer(Gravity.LEFT);
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

    @Override
    protected void onResume() {
        super.onResume();
        get_data();
    }
    public void logout(View v){
        finish();

    }

    public void open_expert_groups(View view) {

        Intent i = new Intent(home_layout.this ,Expert_groups.class);
        startActivity(i);

        drawer.closeDrawer(Gravity.LEFT);

    }

    public void open_feedback(View v) {

        Intent i = new Intent(home_layout.this ,send_feedback.class);
        startActivity(i);

        drawer.closeDrawer(Gravity.LEFT);
    }
}

