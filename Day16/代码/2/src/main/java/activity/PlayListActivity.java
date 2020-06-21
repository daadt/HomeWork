package activity;


import com.example.douts.R;

import base.BaseActivity;
import fragment.RecommendFragment;

/**
 * create by libo
 * create on 2020-05-24
 * description 视频全屏播放页
 */
public class PlayListActivity extends BaseActivity {
    public static int initPos;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_play_list;
    }

    @Override
    protected void init() {
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, new RecommendFragment()).commit();
    }
}
