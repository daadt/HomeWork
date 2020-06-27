package com.example.ain;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNameEt;
    private EditText mPsdEt;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mNameEt = (EditText) findViewById(R.id.et_name);
        mPsdEt = (EditText) findViewById(R.id.et_psd);
        mRegisterBtn = (Button) findViewById(R.id.btn_register);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:

                register();
                // TODO 20/02/19
                break;
            default:
                break;
        }
    }

    private void register() {
        final String name = mNameEt.getText().toString().trim();
        final String psd = mPsdEt.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psd)) {
            showToast("用户名或密码不能为空");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                //注册失败会抛出HyphenateException
                try {
                    EMClient.getInstance().createAccount(name, psd);//同步
                    showToast("注册成功");
                    finish();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    showToast("注册失败");
                }
            }
        }).start();
    }
    private  void showToast(final String msg){
        mRegisterBtn.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
