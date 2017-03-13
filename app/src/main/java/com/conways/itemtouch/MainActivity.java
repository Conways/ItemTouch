package com.conways.itemtouch;

import android.support.annotation.MainThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "zzzzzzzzzz";
    private RecyclerView rv;
    private SwipeRefreshLayout sl;
    private MyAdapter myAdapter;
    private ItemTouchHelper helper;
    private String[] strs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDate();
        initView();
    }

    private void initDate() {
        strs = new String[10];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = i + "";
        }
    }

    private void initView() {
        rv = $(R.id.rv);
        sl = $(R.id.sl);
        sl.setOnRefreshListener(this);
        myAdapter = new MyAdapter(this, strs);
        rv.setLayoutManager(new GridLayoutManager(this, 4));
        rv.setAdapter(myAdapter);
        helper = new ItemTouchHelper(new MyCallBack());
        helper.attachToRecyclerView(rv);

    }

    class MyCallBack extends ItemTouchHelper.Callback {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                Log.i(TAG, "onMove: " + fromPosition + "  " + toPosition);
                String temp = strs[fromPosition];
                for (int i = fromPosition; i < toPosition; i++) {
                    strs[i] = strs[i + 1];
                    Log.i(TAG, "onMove: " + strs[i]);
                }
                strs[toPosition] = temp;
            }
            if (fromPosition > toPosition) {
                String temp = strs[fromPosition];
                for (int i = fromPosition; i > toPosition; i--) {
                    strs[i] = strs[i - 1];
                }
                strs[toPosition] = temp;
            }

            myAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags;
            int swipeFlags = 0;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    ;


    protected <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onRefresh() {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < strs.length / 2; i++) {
                        String temp = strs[i];
                        strs[i] = strs[strs.length - i - 1];
                        strs[strs.length - i - 1] = temp;
                    }
                    Thread.sleep(2000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MainActivity.this.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                myAdapter.notifyDataSetChanged();
                                sl.setRefreshing(false);
                            }
                        }
                );


            }
        }.start();
    }
}
