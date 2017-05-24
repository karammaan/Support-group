package com.tech.karam.demo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karam on 28-04-17.
 */

public class joined_group_adapter extends RecyclerView.Adapter<joined_group_view_holder> {

    JSONArray jsarr;
    Activity b ;

    public joined_group_adapter(JSONArray jarr , Activity a )
    {
        jsarr = jarr;
        b = a;
    }


    @Override
    public joined_group_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        joined_group_view_holder v = new joined_group_view_holder(LayoutInflater.from(b).inflate(R.layout.joined_group_cell,parent,false));

        return v ;

    }

    @Override
    public void onBindViewHolder(joined_group_view_holder holder, int p) {

        try {
            final JSONObject job =  jsarr.getJSONObject(p);

            holder.name.setText("Group name : "+job.getString("Group_name"));
            holder.cause.setText("Group cause : "+job.getString("Group_cause"));
            holder.since.setText("Joined since : "+job.getString("since"));
            holder.type.setText(job.getString("type")+" group");

            holder.joined_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(b , Group_chat.class);

                    try {
                        i.putExtra("group_name" ,job.getString("Group_name") );
                        i.putExtra("group_id" ,job.getString("Group_id") );
                        i.putExtra("type" , job.getString("type"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    b.startActivity(i);
                }
            });




        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return jsarr.length();
    }
}
