package com.tech.karam.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by karam on 28-04-17.
 */

public class joined_group_view_holder extends RecyclerView.ViewHolder {

    public TextView name , cause , since , type ;

    public LinearLayout joined_box;


    public joined_group_view_holder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.name_id);

        joined_box = (LinearLayout) itemView.findViewById(R.id.joined_box);

        cause = (TextView) itemView.findViewById(R.id.cause_id);

        since = (TextView) itemView.findViewById(R.id.since_id);

        type = (TextView) itemView.findViewById(R.id.type_id);


    }
}
