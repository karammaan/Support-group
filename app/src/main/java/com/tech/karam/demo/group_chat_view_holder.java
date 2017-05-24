package com.tech.karam.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by karam on 01-05-17.
 */

public class group_chat_view_holder extends RecyclerView.ViewHolder {

        TextView name, time, message;

        public group_chat_view_holder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_user_id);
            time = (TextView) itemView.findViewById(R.id.time_id);
        message = (TextView) itemView.findViewById(R.id.message_id);
    }

    }
