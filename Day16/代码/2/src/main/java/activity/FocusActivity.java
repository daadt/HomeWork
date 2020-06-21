package activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.example.douts.R;

import java.util.ArrayList;

import base.BaseActivity;
import base.CommPagerAdapter;
import butterknife.BindView;
import fragment.FansFragment;

/**
 * create by libo
 * create on 2020-05-14
 * description 粉丝关注人页面
 */
public class FocusActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommPagerAdapter pagerAdapter;
    private String[] titles = new String[] {"关注 128", "粉丝 128", "推荐关注"};

    @Override
    protected int setLayoutId() {
        return R.layout.activity_focus;
    }

    @Override
    protected void init() {

        for (int i=0;i<titles.length;i++) {
            fragments.add(new FansFragment());
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }

        pagerAdapter = new CommPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
