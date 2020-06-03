package com.example.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tab.Frag.AFragment;
import com.example.tab.Frag.BFragment;
import com.example.tab.Frag.CFragment;
import com.example.tab.Frag.FragmentAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTool;
    private ViewPager mView;
    private TabLayout mTab;
    private LinearLayout mLinear;
    private NavigationView mNavigation;

    private ImageView viewById;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
    }

    private void initFragment() {
        AFragment aFragment = new AFragment();
        BFragment bFragment = new BFragment();
        CFragment cFragment = new CFragment();


        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(aFragment);
        fragments.add(bFragment);
        fragments.add(cFragment);
        FragmentAdapter aadapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mView.setAdapter(aadapter);
        mTab.setupWithViewPager(mView);
        mTab.getTabAt(0).setText("首页").setIcon(R.drawable.bian1);
        mTab.getTabAt(1).setText("我的").setIcon(R.drawable.bain2);
        mTab.getTabAt(2).setText("联系人").setIcon(R.drawable.bian3);

        View headerView = mNavigation.getHeaderView(0);
        viewById = headerView.findViewById(R.id.header_img);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTool.setTitle(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initView() {
        mTool = (Toolbar) findViewById(R.id.tool);
        mView = (ViewPager) findViewById(R.id.view);
        mTab = (TabLayout) findViewById(R.id.tab);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mNavigation = (NavigationView) findViewById(R.id.navigation);
        mDrawer = (DrawerLayout) findViewById(R.id.Drawer);
        //   mDrawer = (DrawerLayout) findViewById(R.id.Drawer);
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item1:
                        break;
                    case R.id.item2:

                        break;
                    case R.id.item3:
                        break;
                }
                return false;
            }
        });

        mTool.setTitle("ToolBar");
        setSupportActionBar(mTool);
        mNavigation.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mTool, R.string.open, R.string.closs);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();


        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                int right = mNavigation.getRight();
                mLinear.setX(right);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });



    }


}
