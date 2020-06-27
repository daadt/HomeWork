package com.example.ain;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CharActivity";
    private String frind;
    private TextView mFriendTv;
    private RecyclerView mRlv;
    private EditText mContentEt;
    private Button mSendBtn;
    private Button mRecordBtn;
    private Button mSendVoiceBtn;
    private Button mVoiceChatBtn;
    private Button mVideoChatBtn;
    private RlvChatAdatper mAdapter;
    private String mPath;
    private long mDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);
        frind = getIntent().getStringExtra("frind");
        initView();
        initListener();
    }

    private void initListener() {
        //正常来讲,这个监听应该是在服务中设置的,
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

        //记得在不需要的时候移除listener，如在activity的onDestroy()时
        //EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(final List<EMMessage> messages) {
            //收到消息,只能收到别人发送的消息,自己的收不到
            Log.d(TAG, "onMessageReceived: "+messages.toString());
            //子线程
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   mAdapter.addMsg(messages);
                }
            });
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    private void initView() {
        mFriendTv = (TextView) findViewById(R.id.tv_friend);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mContentEt = (EditText) findViewById(R.id.et_content);
        mSendBtn = (Button) findViewById(R.id.btn_send);
        mSendBtn.setOnClickListener(this);
        mRecordBtn = (Button) findViewById(R.id.btn_record);
        mRecordBtn.setOnClickListener(this);
        mSendVoiceBtn = (Button) findViewById(R.id.btn_send_voice);
        mSendVoiceBtn.setOnClickListener(this);
        mVoiceChatBtn = (Button) findViewById(R.id.btn_voice_chat);
        mVoiceChatBtn.setOnClickListener(this);
        mVideoChatBtn = (Button) findViewById(R.id.btn_video_chat);
        mVideoChatBtn.setOnClickListener(this);

        mFriendTv.setText("和"+frind+"热聊中....");
        ArrayList<EMMessage> list = new ArrayList<>();
        mAdapter = new RlvChatAdatper(list);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mRlv.setAdapter(mAdapter);
        mRlv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        mAdapter.setOnItemClickListenr(new BaseRlvAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                EMMessage emMessage = mAdapter.mList.get(position);
                EMVoiceMessageBody body = (EMVoiceMessageBody)emMessage.getBody();
                String localUrl = body.getLocalUrl();
                if (!TextUtils.isEmpty(localUrl)){
                    startVoice(localUrl);
                }
            }
        });

    }

    private void startVoice(String localUrl) {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(localUrl);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                // TODO 20/02/20

                sendTextMsg();
                break;
            case R.id.btn_record:
                // TODO 20/02/20
                if (AudioUtil.isRecording){
                    //停止
                    AudioUtil.stopRecord();
                    mRecordBtn.setText("开始录音");
                }else {
                    //录音
                    AudioUtil.startRecord(new AudioUtil.ResultCallBack() {
                        @Override
                        public void onFail(String s) {

                        }

                        @Override
                        public void onSuccess(String absolutePath, long duration) {
                            mPath = absolutePath;
                            mDuration = duration;
                        }
                    });
                    mRecordBtn.setText("停止录音");
                }
                break;
            case R.id.btn_send_voice:
                // TODO 20/02/20

                sendVoicMsg();
                break;
            case R.id.btn_voice_chat:
                // TODO 20/02/20
                break;
            case R.id.btn_video_chat:
                // TODO 20/02/20
                break;
            default:
                break;
        }
    }

    private void sendVoicMsg() {
        if (TextUtils.isEmpty(mPath)){
            showToast("请先录音");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //filePath为语音文件路径，length为录音时间(秒)
                EMMessage message = EMMessage.createVoiceSendMessage(mPath, (int) mDuration, frind);
                EMClient.getInstance().chatManager().sendMessage(message);
                //将发送完的语音路径置空
                mPath = "";
                //只能收到别人发送的消息,自己的收不到,需要展示自己发送的消息
                addSingleMsg(message);
            }
        }).start();
    }

    private void sendTextMsg() {
        final String content = mContentEt.getText().toString().trim();
        if (TextUtils.isEmpty(content)){
            showToast("内容不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, frind);

                EMClient.getInstance().chatManager().sendMessage(message);

                //只能收到别人发送的消息,自己的收不到,需要展示自己发送的消息
               addSingleMsg(message);
            }


        }).start();
    }
    private void addSingleMsg(final EMMessage message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addSingleMsg(message);
            }
        });
    }
    private void showToast(final String msg) {
        //切线程方式
        //1. runOnUiThread()
        //2.handler
        //3.view.post()
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //主线程
                Toast.makeText(CharActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

