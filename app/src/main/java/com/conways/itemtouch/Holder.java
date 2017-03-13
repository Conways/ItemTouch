package com.conways.itemtouch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Conways on 2017/3/13.
 */

public class Holder extends RecyclerView.ViewHolder {

    public ImageView iv;
    public TextView tv;

    public Holder(View itemView) {
        super(itemView);
        init(itemView);
    }

    private void init(View itemView) {
        iv = (ImageView) itemView.findViewById(R.id.iv);
        tv = (TextView) itemView.findViewById(R.id.tv);
    }
}
