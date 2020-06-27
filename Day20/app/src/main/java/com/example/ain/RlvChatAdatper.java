package com.example.ain;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RlvChatAdatper extends BaseRlvAdapter {
    public ArrayList<EMMessage> mList;

    public RlvChatAdatper(ArrayList<EMMessage> list) {
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, null, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        EMMessage emMessage = mList.get(position);
        //消息来自谁
        String from = emMessage.getFrom();
        //消息发送给谁
        String to = emMessage.getTo();
        //消息体
        EMMessageBody body = emMessage.getBody();
        //消息发送的日期毫秒值
        long msgTime = emMessage.getMsgTime();

        //格式化日期的
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = format.format(new Date(msgTime));

        vh.mTv.setText("消息:"+from+",时间:"+date+",内容:"+body.toString());

        //区分是语音还是文本消息,如果是语音,播放,文本不做处理
        //文本:new EMTextMessageBody
        //语音:new EMVoiceMessageBody
        if (body instanceof EMVoiceMessageBody){
            //语音消息
            vh.mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(v,position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addMsg(List<EMMessage> messages) {
        mList.addAll(messages);
        notifyDataSetChanged();
    }

    public void addSingleMsg(EMMessage message) {
        mList.add(message);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {

        private final TextView mTv;

        public VH(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv_friend);
        }
    }
}
