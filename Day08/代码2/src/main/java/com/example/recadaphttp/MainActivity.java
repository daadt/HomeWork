package com.example.recadaphttp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recadaphttp.adapter.MianAdapter;
import com.example.recadaphttp.api.Consts;
import com.example.recadaphttp.entity.Artcas;
import com.example.recadaphttp.util.GsonUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,LinearLayoutManager.VERTICAL));

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
       builder.connectTimeout(10, TimeUnit.SECONDS).
       readTimeout (10, TimeUnit.SECONDS);
        Request.Builder request = new Request.Builder().url(Consts.GONGZHONGHAO_URLs);
        Log.e("dsfsdfsdfdsfsdfsdfsd", "获取数据失败" + request.toString());
        Call call = builder.build().newCall(request.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "获取数据失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strin = response.body().string();
                Artcas artcas = GsonUtil.str2Bean(strin, Artcas.class);
                final Artcas.DataBean data = artcas.getData();
                final List<Artcas.DataBean.DatasBean> datas = data.getDatas();

                for (int i = 0; i < datas.size(); i++) {
                    Artcas.DataBean.DatasBean datasBean = datas.get(i);
                    if (i == 0) {
                        datasBean.setItemtType(Artcas.DataBean.DatasBean.MUL_1);
                    } else if (i % 2 == 0) {
                        datasBean.setItemtType(Artcas.DataBean.DatasBean.MUL_2);
                    } else {
                        datasBean.setItemtType(Artcas.DataBean.DatasBean.MUL_3);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MianAdapter adapter = new MianAdapter(MainActivity.this, datas);
                        mRecyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }
}
