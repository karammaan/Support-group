package com.tech.karam.demo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karam on 01-05-17.
 */

public class group_chat_adapter extends RecyclerView.Adapter<group_chat_view_holder> {

    JSONArray jarr;
    Activity b;

    public group_chat_adapter (JSONArray jsarr, Activity a)

    {
        jarr= jsarr;
        b=a;
    }
    @Override
    public group_chat_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
         group_chat_view_holder v = new group_chat_view_holder(LayoutInflater.from(b).inflate(R.layout.group_chat_cell,parent,false));
        return v ;
    }

    @Override
    public void onBindViewHolder(group_chat_view_holder holder, int position) {

        try {
            JSONObject job =   jarr.getJSONObject(position);

            if(job.getString("by_").equals("expert"))
            {
                holder.name.setText( job.getString("Name")+" ( expert )");
            }
            else {
                holder.name.setText(job.getString("Name"));
            }
            holder.message.setText( job.getString("Chat"));
            holder.time.setText( job.getString("Time"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jarr.length();
    }
}
