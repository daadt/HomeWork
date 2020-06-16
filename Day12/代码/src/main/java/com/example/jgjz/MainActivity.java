package com.example.jgjz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.jgjz.fragment.AppointCourseFragment;
import com.example.jgjz.fragment.CourseFragment;
import com.example.jgjz.fragment.HomeFragment;
import com.example.jgjz.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl)
    FrameLayout mFl;

    @BindView(R.id.main_navigation_bar)
    BottomNavigationView mMainNavigationBar;
    private FragmentTransaction mTransaction;
    private HomeFragment mHomeFragment;
    private Fragment mCourseFragment;
    private Fragment mAppointCourseFragment;
    private Fragment mUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHomeFragment = new HomeFragment();
        mCourseFragment = new CourseFragment();
        mAppointCourseFragment = new AppointCourseFragment();
        mUserFragment = new UserFragment();
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.fl, mHomeFragment);
        mTransaction.commit();

        mMainNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.tab_home:
                        fragment = mHomeFragment;
                        break;
                    case R.id.tab_course:
                        fragment = mCourseFragment;
                        break;
                    case R.id.tab_appoint_course:
                        fragment = mAppointCourseFragment;
                        break;
                    case R.id.tab_user:
                        fragment = mUserFragment;
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl, fragment).commit();
                }
                return true;
            }
        });


    }
}
