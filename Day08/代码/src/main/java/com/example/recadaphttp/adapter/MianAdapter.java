package com.example.recadaphttp.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.recadaphttp.R;
import com.example.recadaphttp.api.Consts;
import com.example.recadaphttp.entity.Artcas;

import java.util.List;

public class MianAdapter extends BaseMultiItemQuickAdapter<Artcas.DataBean.DatasBean, BaseViewHolder> {

    private Context mContext;

    public MianAdapter(Context context, List<Artcas.DataBean.DatasBean> data) {
        super(data);
        addItemType(Artcas.DataBean.DatasBean.MUL_1, R.layout.item1);
        addItemType(Artcas.DataBean.DatasBean.MUL_2, R.layout.item2);
        addItemType(Artcas.DataBean.DatasBean.MUL_3, R.layout.item3);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, Artcas.DataBean.DatasBean datasBean) {
        switch (datasBean.getItemType()){
            case Artcas.DataBean.DatasBean
                .MUL_1:
                Glide.with(mContext).load(datasBean.getEnvelopePic()).into((ImageView) helper.getView(R.id.multi1_ivt));
                break;
            case Artcas.DataBean.DatasBean.MUL_2:
                helper.setText(R.id.multi2_tv ,datasBean.getDesc());
                Glide.with(mContext).load(datasBean.getEnvelopePic()).
                        into((ImageView) helper.getView(R.id.multi2_ivs));

            case Artcas.DataBean.DatasBean.MUL_3:
                helper.setText(R.id.mu_tv, datasBean.getId());
                Glide.with(mContext).load(datasBean.getEnvelopePic()).
                        into((ImageView) helper.getView(R.id.mu_im));
                break;

        }
    }
}
