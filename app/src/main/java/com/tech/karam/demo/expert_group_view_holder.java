package com.tech.karam.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by karam on 14-05-17.
 */

public class expert_group_view_holder extends RecyclerView.ViewHolder {

    TextView group_name , group_cause , group_description , join ;



    public expert_group_view_holder(View itemView) {
        super(itemView);

        group_name = (TextView) itemView.findViewById(R.id.name_id);
        group_cause = (TextView) itemView.findViewById(R.id.cause_id);
        group_description = (TextView) itemView.findViewById(R.id.description_id);

        join = (TextView) itemView.findViewById(R.id.join_id);

    }
}
