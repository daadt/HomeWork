package com.example.ain;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNameEt;
    private EditText mPsdEt;
    private Button mLoginBtn;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            //如果自动登录了,就不要再显示登录页面
        if(EMClient.getInstance().isLoggedInBefore()){
             goMain();
        }
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mNameEt = (EditText) findViewById(R.id.et_name);
        mPsdEt = (EditText) findViewById(R.id.et_psd);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(this);
        mRegisterBtn = (Button) findViewById(R.id.btn_register);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                // TODO 20/02/19
                login();
                break;
            case R.id.btn_register:
                // TODO 20/02/19

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
//登录
    private void login() {

        final String name = mNameEt.getText().toString().trim();
        final String psd = mPsdEt.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psd)) {
            showToast("用户名或密码不能为空");
            return;
        }
        EMClient.getInstance().login(name,psd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                showToast("登录成功");
                goMain();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
                showToast("登录失败");
            }
        });
    }

    private void goMain() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private  void showToast(final String msg){
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
