package com.example.jgjz.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jgjz.BaseApp;
import com.example.jgjz.R;
import com.example.jgjz.bean.SearchBean;
import com.example.jgjz.greendaodemo.db.SearchBeanDao;
import com.google.android.material.internal.FlowLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mShareQqCommon;
    private EditText mShareEdCommon;
    private TextView mShareTeCommon;
    private ImageView mClearSearch;
    private ConstraintLayout mFlowcon;
    private RecyclerView mRecySearch;
    private ConstraintLayout mNetcon;
    private SearchBeanDao searchBeanDao;
    private FlowLayout mFlowSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initListener();
        updateDataBase();
    }


    private void initListener() {

        mShareEdCommon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mFlowcon.setVisibility(View.VISIBLE);
                    mNetcon.setVisibility(View.GONE);
                } else {
                    mNetcon.setVisibility(View.VISIBLE);
                    mFlowcon.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateDataBase() {
        List<SearchBean> searchBeans = searchBeanDao.loadAll();
        mFlowSearch.removeAllViews();

        if (searchBeans != null && searchBeans.size() > 0) {
            Collections.sort(searchBeans, new Comparator<SearchBean>() {
                @Override
                public int compare(SearchBean o1, SearchBean o2) {
                    return (int) (o2.getTime() - o1.getTime());
                }
            });
            for (int i = 0; i < searchBeans.size(); i++) {
                TextView inflate = (TextView) LayoutInflater.from(this).inflate(R.layout.item_label, null);
                inflate.setText(searchBeans.get(i).getKerword());
                mFlowSearch.addView(inflate);
            }
        }
    }

    private void initView() {
        mShareQqCommon = (ImageView) findViewById(R.id.common_share_qq);
        mShareEdCommon = (EditText) findViewById(R.id.common_share_ed);
        mShareTeCommon = (TextView) findViewById(R.id.common_share_te);
        mClearSearch = (ImageView) findViewById(R.id.search_clear);
        mFlowSearch = (FlowLayout) findViewById(R.id.search_flow);
        mFlowcon = (ConstraintLayout) findViewById(R.id.flowcon);
        mRecySearch = (RecyclerView) findViewById(R.id.search_recy);
        mNetcon = (ConstraintLayout) findViewById(R.id.netcon);

        searchBeanDao = BaseApp.getInstance().getDaoSession().getSearchBeanDao();


        mShareEdCommon.setOnClickListener(this);
        mShareTeCommon.setOnClickListener(this);
        mClearSearch.setOnClickListener(this);
        mFlowcon.setOnClickListener(this);
        mRecySearch.setOnClickListener(this);
        mNetcon.setOnClickListener(this);
        mShareQqCommon.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_share_ed:
                // TODO 20/06/18
                break;
            case R.id.common_share_te:
                String trim = mShareEdCommon.getText().toString().trim();
                SearchBean searchBean = new SearchBean(trim, System.currentTimeMillis());
                searchBeanDao.insertOrReplace(searchBean);
                updateDataBase();
                // TODO 20/06/18
                break;
            case R.id.search_clear:

                searchBeanDao.deleteAll();
                // TODO 20/06/18
                break;
            case R.id.search_flow:
                // TODO 20/06/18
                break;
            case R.id.flowcon:
                // TODO 20/06/18
                break;
            case R.id.search_recy:
                // TODO 20/06/18
                break;
            case R.id.netcon:
                // TODO 20/06/18
                break;
            case R.id.common_share_qq:// TODO 20/06/18
                finish();
                break;
            default:
                break;
        }
    }

  /*  @Override
    public void onClick(View v) {

    }*/
}
