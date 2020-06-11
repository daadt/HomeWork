package com.example.dadts;

import android.os.Bundle;

import com.example.dadts.base.BaseActivity;
import com.example.dadts.base.BasePre;
import com.example.dadts.fragmet.CourseFragment;
import com.example.dadts.fragmet.HomeFragment;
import com.example.dadts.fragmet.PersonFragment;
import com.example.dadts.fragmet.YorkFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class Main2Activity extends BaseActivity implements View.OnClickListener{

    private ViewPager mViewpageMain;
    private TabLayout mTabRecommend;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabs;
    private ArrayList<Integer> images;


 /*       mViewpageMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0){
                main_bar_text.setText("消息");
            }else {
                main_bar_text.setText("消");
            }
            main_bar_text.setText("消21");
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    });
*/


    @Override
    protected void initData() {

    }


    @Override
    protected void initListener() {
        //main_bar_text = findViewById(R.id.main_bar_text);

        mViewpageMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0){
                    ///main_bar_text.setText("消息");
                }else {
                   // main_bar_text.setText("消");
                }
              //  main_bar_text.setText("消21");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initView() {

        mViewpageMain = (ViewPager) findViewById(R.id.main_viewpage);
        mTabRecommend = (TabLayout) findViewById(R.id.main_tab);

        mTabRecommend.setSelectedTabIndicator(0);
        initFragment();
        initTabs();
        initImages();
        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), fragments, tabs);
        mViewpageMain.setAdapter(adapter);
        mTabRecommend.setupWithViewPager(mViewpageMain);
        for (int i = 0; i < tabs.size(); i++) {
            TabLayout.Tab tab = mTabRecommend.getTabAt(i);

            tab.setCustomView(getTabView(i));
        }
    }

    private View getTabView(int position) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.tab, null);
        TextView tv = inflate.findViewById(R.id.tv);
        ImageView iv = inflate.findViewById(R.id.iv);
        tv.setText(tabs.get(position));
        iv.setImageResource(images.get(position));
        return inflate;
    }

    private void initImages() {
        images = new ArrayList<>();
        images.add(R.drawable.messageselect);
        images.add(R.drawable.peopleselect);
        images.add(R.drawable.dongtaiselect);
        images.add(R.drawable.dongtaiselects);
    }

    private void initTabs() {
        tabs = new ArrayList<>();
        tabs.add("首页");
        tabs.add("课程");
        tabs.add("约课记录");
        tabs.add("个人");



    }



    private void initFragment() {

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CourseFragment());
        fragments.add(new YorkFragment());
        fragments.add(new PersonFragment());


    }

    @Override
    protected BasePre initPre() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main2;
    }



    @Override
    public void showToast(String msg) {

    }


    @Override
    public void onClick(View v) {

    }
}
