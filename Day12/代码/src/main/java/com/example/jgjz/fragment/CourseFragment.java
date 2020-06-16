package com.example.jgjz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.jgjz.R;

import butterknife.BindView;

public class CourseFragment extends Fragment {


    private RelativeLayout mCourseTopRl;
    private RadioButton mGroupType;
    private RadioButton mGroupSort;
    private RadioButton mGroupSelect;
    private RadioGroup mCourseGroupRg;
    private View m66TypeView;
    private LinearLayout mTypeGroup;
    private View m66SortView;
    private LinearLayout mSortGroup;
    private View m66SelectView;
    private LinearLayout mSelectGroup;

    private boolean isShowType = false;
    private boolean isShowSort = false;
    private boolean isShowSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_course, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mCourseTopRl = (RelativeLayout) inflate.findViewById(R.id.rl_course_top);
        mGroupType = (RadioButton) inflate.findViewById(R.id.rb_group_type);
        mGroupSort = (RadioButton) inflate.findViewById(R.id.rb_group_sort);
        mGroupSelect = (RadioButton) inflate.findViewById(R.id.rb_group_select);
        mCourseGroupRg = (RadioGroup) inflate.findViewById(R.id.rg_course_group);
        m66TypeView = (View) inflate.findViewById(R.id.view_66_type);
        mTypeGroup = (LinearLayout) inflate.findViewById(R.id.group_type);
        m66SortView = (View) inflate.findViewById(R.id.view_66_sort);
        mSortGroup = (LinearLayout) inflate.findViewById(R.id.group_sort);
        m66SelectView = (View) inflate.findViewById(R.id.view_66_select);
        mSelectGroup = (LinearLayout) inflate.findViewById(R.id.group_select);

        mCourseGroupRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_group_type:
                            mTypeGroup.setVisibility(View.VISIBLE);
                            mSortGroup.setVisibility(View.GONE);
                            mSelectGroup.setVisibility(View.GONE);

                        break;
                    case R.id.rb_group_sort:
                            mTypeGroup.setVisibility(View.GONE);
                            mSortGroup.setVisibility(View.VISIBLE);
                            mSelectGroup.setVisibility(View.GONE);

                        break;
                    case R.id.rb_group_select:
                            mTypeGroup.setVisibility(View.GONE);
                            mSortGroup.setVisibility(View.GONE);
                            mSelectGroup.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });
        m66TypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupType.setChecked(false);
                mTypeGroup.setVisibility(View.GONE);
                mSortGroup.setVisibility(View.GONE);
                mSelectGroup.setVisibility(View.GONE);
            }
        });
        m66SortView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupSort.setChecked(false);
                mTypeGroup.setVisibility(View.GONE);
                mSortGroup.setVisibility(View.GONE);
                mSelectGroup.setVisibility(View.GONE);
            }
        });
        m66SelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupSelect.setChecked(false);
                mTypeGroup.setVisibility(View.GONE);
                mSortGroup.setVisibility(View.GONE);
                mSelectGroup.setVisibility(View.GONE);
            }
        });

    }

    private void groupGone() {
        mTypeGroup.setVisibility(View.GONE);
        mSortGroup.setVisibility(View.GONE);
        mSelectGroup.setVisibility(View.GONE);
    }
}
