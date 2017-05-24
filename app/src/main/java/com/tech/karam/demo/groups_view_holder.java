package com.tech.karam.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by karam on 25-04-17.
 */

public class groups_view_holder extends RecyclerView.ViewHolder {

    TextView grp_name , grp_cause , grp_desc;

    public groups_view_holder(View itemView) {
        super(itemView);

        grp_name = (TextView) itemView.findViewById(R.id.group_name_id);

        grp_cause = (TextView) itemView.findViewById(R.id.group_cause_id);
        grp_desc =(TextView) itemView.findViewById(R.id.group_description_id);
    }
}
