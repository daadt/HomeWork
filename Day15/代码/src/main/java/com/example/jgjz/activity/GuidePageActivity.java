package com.example.jgjz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.jgjz.MainActivity;
import com.example.jgjz.R;


public class GuidePageActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                for (int i = 0; i < 3; i++) {
                    count++;
                    try {
                        Thread.sleep(1000);
                        mHandler.sendEmptyMessage(0);

//                       TODO 应改为 if (count > 2){
                        if (count >= 1){
                            startActivity(new Intent(GuidePageActivity.this, MainActivity.class));
                            break;
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }
}
