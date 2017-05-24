package com.tech.karam.demo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karam on 14-05-17.
 */

public class expert_group_adapter extends RecyclerView.Adapter<expert_group_view_holder> {

    JSONArray jarr ;
    Activity a ;

    public  expert_group_adapter(JSONArray jarr , Activity a)
    {
        this.jarr = jarr ;
        this.a = a ;
    }
    @Override
    public expert_group_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new expert_group_view_holder(LayoutInflater.from(a ).inflate(R.layout.expert_group_cell , parent ,false));

    }

    @Override
    public void onBindViewHolder(expert_group_view_holder holder, int position) {
        try {
            final JSONObject job = jarr.getJSONObject(position);

            holder.group_name.setText("Group name : "+job.getString("Group_name"));
            holder.group_cause.setText("Group cause : "+job.getString("Group_cause"));
            holder.group_description.setText("About : "+job.getString("Description"));

            holder.join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Expert_groups.group_join(job.getString("Group_id") , a);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jarr.length();
    }
}
