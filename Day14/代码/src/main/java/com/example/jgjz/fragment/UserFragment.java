package com.example.jgjz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.jgjz.R;
import com.example.jgjz.RequireActivity;

import java.time.Instant;


public class UserFragment extends Fragment {
    private ImageView mUnloginIv;
    private TextView mLoginRegTv;
    private CardView mMainUserCv;
    private ImageView mYaoqingIv;
    private RelativeLayout mYaoqingRl;
    private LinearLayout mKechenLl;
    private LinearLayout mDindanLl;
    private LinearLayout mMyzhanghuLl;
    private LinearLayout mAutofuwuLl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_user, container, false);
        initView(inflate);

        return inflate;
    }

    private void initView(@NonNull final View itemView) {
        mUnloginIv = (ImageView) itemView.findViewById(R.id.iv_unlogin);
        mLoginRegTv = (TextView) itemView.findViewById(R.id.tv_login_reg);
        mMainUserCv = (CardView) itemView.findViewById(R.id.cv_main_user);
        mYaoqingIv = (ImageView) itemView.findViewById(R.id.iv_yaoqing);
        mYaoqingRl = (RelativeLayout) itemView.findViewById(R.id.rl_yaoqing);
        mKechenLl = (LinearLayout) itemView.findViewById(R.id.ll_kechen);
        mDindanLl = (LinearLayout) itemView.findViewById(R.id.ll_dindan);
        mMyzhanghuLl = (LinearLayout) itemView.findViewById(R.id.ll_myzhanghu);
        mAutofuwuLl = (LinearLayout) itemView.findViewById(R.id.ll_autofuwu);



        mLoginRegTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RequireActivity.class));

            }
        });

    }

}
