package com.example.tak.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tak.MainActivity;
import com.example.tak.R;
import com.example.tak.base.BaseActivity;
import com.example.tak.base.BasePre;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {









    private int time = 60;



    Handler handler=nmTitleBarTitleew Handler();

    private EditText loginUsername;
    private EditText loginPassWord;
    private EditText loginCode;
    private Button loginCodeBt;
    private Button loginSubmit;
    private Button loginOk;
    private ImageView mTitleBarBack;
    private TextView mTitleBarTitle;
    private ImageView mTitleOptionsImg;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        loginUsername = findViewById(R.id.login_username);
        loginPassWord = findViewById(R.id.login_passWord);
        loginCode = findViewById(R.id.login_code);
        loginCodeBt = findViewById(R.id.login_code_bt);
        loginSubmit = findViewById(R.id.login_submit);
        loginOk = findViewById(R.id.login_ok);
        mTitleBarBack = findViewById(R.id.title_bar_back);
        mTitleBarTitle = findViewById(R.id.title_bar_title);
        mTitleOptionsImg = findViewById(R.id.title_options_img);

        sharedPrefHelper = SharedPrefHelper.getInstance();
        if (!sharedPrefHelper.getUserId().equals("")) {
            loginUsername1.setText(sharedPrefHelper.getUserId());
        }

        mTitleBarBack.setVisibility(View.GONE);
        mTitleOptionsImg.setVisibility(View.GONE);
        mTitleBarTitle.setText("注册登陆");
    }

    @Override
    protected BasePre initPre() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.login_code_bt, R.id.login_submit, R.id.login_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_code_bt:
                break;
            case R.id.login_submit:
                //注册
                JMessageClient.register(loginUsername.getText().toString(), loginPassWord.getText().toString(), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        Log.e("s=======1:", i + "，" + s);
                        switch (i) {
                            case 0:

                                break;
                            case 898001:

                                break;
                            case 871301:
                                break;
                            case 871304:
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            case R.id.login_ok:
                //登陆
//                Log.e("info2============",""+JMessageClient.getMyInfo());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initLogin(loginUsername.getText().toString(),loginPassWord.getText().toString(),0);
                    }
                }, 500);
                break;
            default:break;

        }
    }

    /**
     *
     * @param userName
     * @param passWord
     */
    private void initLogin(String userName, String passWord, final int type){

        JMessageClient.login(userName, passWord, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                dismissProgressDialog();
                switch (i) {
                    case 801003:
                        break;
                    case 871301:
                        break;
                    case 801004:
                        handler.sendEmptyMessage(-1);
                        break;
                    case 0:
                        break;
                    default:

                        break;
                }

            }

            private void dismissProgressDialog() {

            }
        });
    }
    //初始化个人资料
    public void initUserInfo(String id, final int type){
        showProgressDialog("正在初始化数据");
        JMessageClient.getUserInfo(id, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                dismissProgressDialog();
                if (i==0) {
//                    Log.e("info-Login", ""+JMessageClient.getMyInfo()+"\n"+JMessageClient.getConversationList()+"\n"+userInfo);
                    Intent intent = new Intent(LoginActivity.this
                            , MainActivity.class);
                    intent.putExtra("LOGINTYPE", type);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
    }


    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        System.exit(0);
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    public void showToast(String msg) {

    }
}
