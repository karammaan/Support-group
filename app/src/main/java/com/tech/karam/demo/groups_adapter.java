package com.tech.karam.demo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karam on 25-04-17.
 */

public class groups_adapter extends RecyclerView.Adapter<groups_view_holder> {


    JSONArray jsarr;
    Activity b ;

    public groups_adapter(JSONArray jarr , Activity a )
    {

        jsarr = jarr;
        b = a ;
    }


    @Override
    public groups_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        groups_view_holder v = new groups_view_holder(LayoutInflater.from(b).inflate(R.layout.my_group_cell, parent , false));

        return v ;
    }

    @Override
    public void onBindViewHolder(groups_view_holder holder, int position) {

        try {
            JSONObject job =  jsarr.getJSONObject(position);

            holder.grp_name.setText(job.getString("Group_name"));
            holder.grp_cause.setText(job.getString("Group_cause"));
            holder.grp_desc.setText(job.getString("Description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsarr.length();
    }
}
