package com.example.butans;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Mbutton mMbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mMbs = (Mbutton) findViewById(R.id.mbs);
        mMbs.setOnClickListener(this);

        mMbs.setTranslationX(getSharedPreferences("name",MODE_PRIVATE).getInt("x",0));
        mMbs.setTranslationY(getSharedPreferences("name",MODE_PRIVATE).getInt("y",0));

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences.Editor edit = name.edit();
        float lastX = mMbs.getX();
        float lastY = mMbs.getY();
        edit.putInt("x", (int) lastX);
        edit.putInt("y", (int) lastY);
        edit.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mbs:
                // TODO 20/06/22

                break;
            default:
                break;
        }
    }


}
