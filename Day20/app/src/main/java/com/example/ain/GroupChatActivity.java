package com.example.ain;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GroupActivity";
    private RecyclerView mRlv;
    private EditText mEtContent;
    /**
     * 发送
     */
    private Button mBtnSend;
    /**
     * 开始录音
     */
    private Button mBtnRecord;
    /**
     * 发送语音
     */
    private Button mBtnSendVoice;
    /**
     * 语音聊天
     */
    private Button mBtnVoiceChat;
    /**
     * 视频聊天
     */
    private Button mBtnVideoChat;
    private RlvChatAdatper mAdapter;
    private String mPath;
    private long mDuration;
    /**
     * 请输入群组名称
     */
    private EditText mEtGroupName;
    /**
     * 创建群
     */
    private Button mBtnCreate;
    /**
     * 请输入群组id
     */
    private EditText mEtGroupId;
    /**
     * 加入群聊
     */
    private Button mBtnJoin;
    private String mGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
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
            Log.d(TAG, "onMessageReceived: " + messages.toString());
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
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mBtnRecord = (Button) findViewById(R.id.btn_record);
        mBtnRecord.setOnClickListener(this);
        mBtnSendVoice = (Button) findViewById(R.id.btn_send_voice);
        mBtnSendVoice.setOnClickListener(this);
        mBtnVoiceChat = (Button) findViewById(R.id.btn_voice_chat);
        mBtnVoiceChat.setOnClickListener(this);
        mBtnVideoChat = (Button) findViewById(R.id.btn_video_chat);
        mBtnVideoChat.setOnClickListener(this);

        ArrayList<EMMessage> list = new ArrayList<>();
        mAdapter = new RlvChatAdatper(list);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mRlv.setAdapter(mAdapter);
        mRlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter.setOnItemClickListenr(new BaseRlvAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                EMMessage emMessage = mAdapter.mList.get(position);
                EMVoiceMessageBody body = (EMVoiceMessageBody) emMessage.getBody();
                String localUrl = body.getLocalUrl();
                if (!TextUtils.isEmpty(localUrl)) {
                    startVoice(localUrl);
                }
            }
        });
       /* mAdapter.setOnItemClickListener(new BaseRlvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //语音消息
                EMMessage emMessage = mAdapter.mList.get(position);
                EMVoiceMessageBody body = (EMVoiceMessageBody) emMessage.getBody();
                String localUrl = body.getLocalUrl();
                if (!TextUtils.isEmpty(localUrl)) {
                    startVoice(localUrl);
                }
            }
        });*/
        mEtGroupName = (EditText) findViewById(R.id.et_group_name);
        mBtnCreate = (Button) findViewById(R.id.btn_create);
        mBtnCreate.setOnClickListener(this);
        mEtGroupId = (EditText) findViewById(R.id.et_group_id);
        mBtnJoin = (Button) findViewById(R.id.btn_join);
        mBtnJoin.setOnClickListener(this);
    }

    //播放语音
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
            default:
                break;
            case R.id.btn_send:
                sendTextMsg();
                break;
            case R.id.btn_record:
                //录音+停止录音的按钮
                /*if (当前正在录音){
                    //停止

                }else {
                    //录音
                }*/
                if (AudioUtil.isRecording) {
                    //停止
                    AudioUtil.stopRecord();
                    mBtnRecord.setText("开始录音");
                } else {
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
                    mBtnRecord.setText("停止录音");
                }
                break;
            case R.id.btn_send_voice:
                sendVoiceMsg();
                break;
            case R.id.btn_voice_chat:
                break;
            case R.id.btn_video_chat:
                break;
            case R.id.btn_create:
                createGroup();
                break;
            case R.id.btn_join:
                joinGroup();
                break;
        }
    }

    private void joinGroup() {
        mGroupId = mEtGroupId.getText().toString().trim();
        if (TextUtils.isEmpty(mGroupId)) {
            showToast("群组id不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加入群聊,正常来讲,加群只需要加入一次,但是这里为了获取groupid,每次都加入一些
                //如果群开群是自由加入的，即group.isMembersOnly()为false，直接join
                try {
                    EMClient.getInstance().groupManager().joinGroup(mGroupId);//需异步处理
                    showToast("加入成功");
                    //需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
                    //EMClient.getInstance().groupManager().applyJoinToGroup(mGroupId, "求加入");//需异步处理
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    showToast("加入失败");
                }

            }
        }).start();

    }

    private void createGroup() {
        final String groupName = mEtGroupName.getText().toString().trim();
        if (TextUtils.isEmpty(groupName)) {
            showToast("请输入群名称");
            return;
        }
        /**
         * 创建群组
         * @param groupName 群组名称
         * @param desc 群组简介
         * @param allMembers 群组初始成员，如果只有自己传空数组即可
         * @param reason 邀请成员加入的reason
         * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
         *               option.inviteNeedConfirm表示邀请对方进群是否需要对方同意，默认是需要用户同意才能加群的。
         *               option.extField创建群时可以为群组设定扩展字段，方便个性化订制。
         * EMGroupStylePrivateOnlyOwnerInvite——私有群，只有群主可以邀请人；
         * EMGroupStylePrivateMemberCanInvite——私有群，群成员也能邀请人进群；
         * EMGroupStylePublicJoinNeedApproval——公开群，加入此群除了群主邀请，只能通过申请加入此群；
         * EMGroupStylePublicOpenJoin ——公开群，任何人都能加入此群。
         * @return 创建好的group
         * @throws HyphenateException
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                EMGroupOptions option = new EMGroupOptions();
                option.maxUsers = 200;
                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;

                try {
                    EMGroup group = EMClient.getInstance().groupManager().createGroup(groupName, "", new String[]{}, "", option);
                    showToast("创建成功");
                    Log.d(TAG, "run: " + group.getGroupId());
                    mGroupId = group.getGroupId();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    showToast("创建失败");
                }
            }
        }).start();

    }

    //语音消息
    private void sendVoiceMsg() {
        if (TextUtils.isEmpty(mPath)) {
            showToast("请先录音");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //filePath为语音文件路径，length为录音时间(秒)
                EMMessage message = EMMessage.createVoiceSendMessage(mPath, (int) mDuration, mGroupId);
//如果是群聊，设置chattype，默认是单聊
                message.setChatType(EMMessage.ChatType.GroupChat);
                EMClient.getInstance().chatManager().sendMessage(message);

                //将发送完的语音路径置空
                mPath = "";
                //只能收到别人发送的消息,自己的收不到,需要展示自己发送的消息
                addSingleMsg(message);
            }
        }).start();
    }

    //发送文本消息
    private void sendTextMsg() {
        final String content = mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("内容不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, mGroupId);
                //如果是群聊，设置chattype，默认是单聊

                message.setChatType(EMMessage.ChatType.GroupChat);
                //发送消息
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
                Toast.makeText(GroupChatActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
