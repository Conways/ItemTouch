package com.conways.itemtouch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Conways on 2017/3/13.
 */

public class MyAdapter extends RecyclerView.Adapter<Holder> {
    private Context context;
    private String[] strs;
    private String TAG="zzzz";

    public MyAdapter(Context context, String[] strs) {
        this.context = context;
        this.strs = strs;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv.setText(strs[position]);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (position%2==0){
            layoutParams.height=600;
        }else{
            layoutParams.height=300;
        }
        holder.itemView.setLayoutParams(layoutParams);
        Log.d(TAG, "onBindViewHolder: "+holder.itemView.getLayoutParams().height);
    }

    @Override
    public int getItemCount() {
        return strs.length;
    }
}
