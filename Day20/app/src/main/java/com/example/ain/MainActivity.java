package com.example.ain;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTvUser;
    private RecyclerView mRlv;
    private RlvContactAdapter adaptert;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inPers();
        initView();
        initData();
    }

    private void inPers() {
        String[] pers = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        };
        ActivityCompat.requestPermissions(this,pers,100);
    }

    private void initData() {
        //获取好友列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<String> usernames = EMClient.getInstance()
                            .contactManager()
                            .getAllContactsFromServer();
                    System.out.println(usernames.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adaptert.addData(usernames);
                    }
                });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        mTvUser = (TextView) findViewById(R.id.tvUser);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        String currentUser = EMClient.getInstance().getCurrentUser();
        mTvUser.setText("用户为:"+currentUser);

        mRlv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();


        adaptert = new RlvContactAdapter(list);
        mRlv.setAdapter(this.adaptert);
       adaptert.notifyDataSetChanged();
        this.adaptert.setOnItemClickListenr(new BaseRlvAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                    gochat(position);
            }
        });
    }
    private void gochat(int position) {
        Intent intent = new Intent(this, CharActivity.class);
        intent.putExtra("frind",adaptert.mList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 0, 0, "退出登录");
        menu.add(1, 1, 0, "群聊");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                logout();
                break;
            case 1:
                go2GroupChat();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //跳转群聊页面
    private void go2GroupChat() {
        startActivity(new Intent(this,GroupChatActivity.class));
    }

    private void logout() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                showToast("退出登录成功");
                goLog1();

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                showToast("退出登录失败");
            }
        });
    }

    private void goLog1() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
