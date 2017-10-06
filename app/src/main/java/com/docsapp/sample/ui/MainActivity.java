package com.docsapp.sample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.docsapp.sample.Factory;
import com.docsapp.sample.R;
import com.docsapp.sample.concurrency.ExecutorUtils;
import com.docsapp.sample.models.RootObject;
import com.docsapp.sample.models.User;
import com.docsapp.sample.models.UserMessage;
import com.docsapp.sample.utils.Constants;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mBtn;
    private EditText mEt;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private ListenableFuture<String> mFuture;
    List<UserMessage> mUserList = new ArrayList<>(100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        mBtn = (Button) findViewById(R.id.button_chatbox_send);
        mBtn.setOnClickListener(this);
        mEt = (EditText) findViewById(R.id.edittext_chatbox);
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);

    }

    private void sendAndReceiveMessage(String msg) {
        mFuture = Factory.sendMessageService().sendMessageToChatbot(msg);

        Futures.addCallback(mFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, result);

                Gson gson = new GsonBuilder().create();
                RootObject rootObject = gson.fromJson(result, RootObject.class);
                if (rootObject != null) {
                    createUserMessageAndAddtoList(rootObject.getMessage().getMessage(), Constants.RECEIVER, rootObject.getMessage().getChatBotName(), "");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        }, ExecutorUtils.getUIThread());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_chatbox_send) {
            if (!TextUtils.isEmpty(mEt.getText().toString())) {
                String msg = mEt.getText().toString().trim();
                createUserMessageAndAddtoList(msg, Constants.SENDER, "Abhi", "https://plus.google.com/u/1/photos/102520040000109889848/albums/profile/5697902589696355810");
                sendAndReceiveMessage(msg);
                mEt.setText("");
            }
        }

    }

    private void createUserMessageAndAddtoList(String msg, String userId, String nickname, String profileUrl) {
        UserMessage userMessage = new UserMessage();
        userMessage.setCreatedAt(System.currentTimeMillis());
        userMessage.setMessage(msg);
        User user = new User();
        user.setNickname(nickname);
        user.setProfileUrl(profileUrl);
        user.setUserId(userId);

        mUserList.add(userMessage);
        mMessageAdapter = new MessageListAdapter(this, mUserList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
    }
}
