package com.example.jgjz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.jgjz.R;
import com.google.android.material.tabs.TabLayout;

public class AppointCourseFragment extends Fragment {
    //    @BindView(R.id.tablayout_appoint_course)
//    TabLayout mTablayout;
//    @BindView(R.id.fl)
//    FrameLayout mFl;
    private WaitingForClassFragment mWaitingForClassFragment;
    private AlreadyInClassFragment mAlreadyInClassFragment;
    private CancelledFragment mCancelledFragment;
    private FragmentTransaction mTransaction;
    private TabLayout mTablayout;
    private FrameLayout mFl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_appoint_course, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View view) {
        mTablayout = (TabLayout) view.findViewById(R.id.tablayout_appoint_course);
        mFl = (FrameLayout) view.findViewById(R.id.fl);

        mWaitingForClassFragment = new WaitingForClassFragment();
        mAlreadyInClassFragment = new AlreadyInClassFragment();
        mCancelledFragment = new CancelledFragment();

        mTransaction = getChildFragmentManager().beginTransaction();
        mTransaction.replace(R.id.fl, mWaitingForClassFragment);
        mTransaction.commit();

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = mWaitingForClassFragment;
                        break;
                    case 1:
                        fragment = mAlreadyInClassFragment;
                        break;
                    case 2:
                        fragment = mCancelledFragment;
                        break;
                }
                if (fragment != null) {
                    getChildFragmentManager().beginTransaction().replace(R.id.fl, fragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTablayout.addTab(mTablayout.newTab().setText("待上课"));
        mTablayout.addTab(mTablayout.newTab().setText("已上课"));
        mTablayout.addTab(mTablayout.newTab().setText("已取消"));



    }
}
