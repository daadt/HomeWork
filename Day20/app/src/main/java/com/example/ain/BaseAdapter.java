package com.example.ain;

import android.view.View;

public abstract class BaseAdapter extends BaseRlvAdapter {
    public OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        mListener = listener;
    }

}
