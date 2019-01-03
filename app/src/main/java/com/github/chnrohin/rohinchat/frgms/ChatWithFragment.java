/*
 * Copyright © Rohin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.chnrohin.rohinchat.frgms;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.adapter.ChatWithRecyclerViewAdapter;
import com.github.chnrohin.rohinchat.base.BaseMvpFragment;
import com.github.chnrohin.rohinchat.contract.ChatWithContract;
import com.github.chnrohin.rohinchat.data.entity.Message;
import com.github.chnrohin.rohinchat.data.dummy.MessageList;
import com.github.chnrohin.rohinchat.common.listener.OnDoubleClickListenerKt;
import com.github.chnrohin.rohinchat.common.util.ToastUtils;
import com.github.chnrohin.rohinchat.presenter.ChatWithPresenter;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ChatWithFragment extends BaseMvpFragment<ChatWithContract.View, ChatWithPresenter>
        implements ChatWithContract.View, View.OnClickListener, TextWatcher {

    private static final String KEY = "ChatWithFragment";

    @BindView(R2.id.et_chat_inputMsg)
    EditText mInputMsg;
    @BindView(R2.id.iv_chat_sendMore)
    ImageView mSendMore;
    @BindView(R2.id.btn_chat_sendText)
    Button mSendText;
    @BindView(R2.id.srl_chat_loadMoreMsgEx)
    SwipeRefreshLayout mLoadMoreMsgEx;
    @BindView(R2.id.rv_chat_msgEx)
    RecyclerView mRecyclerView;

    private List<Message> mMessageList = MessageList.getInstance().getMessages();

    /**
     * 聊天记录 - 倒序排列
     */
    private OnDoubleClickListenerKt.ICallBack mDoubleClickListener
            = () -> mRecyclerView.scrollToPosition(mMessageList.size());

    public static ChatWithFragment getInstance() {
        return new ChatWithFragment();
    }

    public static ChatWithFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString(KEY, value);
        ChatWithFragment fragment = new ChatWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.chat_fragment_chat_with;
    }

    @Override
    protected void init() {
        super.init();

        Toolbar toolbar = null == getActivity()
                ? view.findViewById(R.id.toolbar)
                : getActivity().findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new OnDoubleClickListenerKt(mDoubleClickListener));

//        mRecyclerView = view.findViewById(R.id.rv_chat_msgEx);
        mRecyclerView.setAdapter(new ChatWithRecyclerViewAdapter(mMessageList));

        mLoadMoreMsgEx.setOnRefreshListener(() -> ToastUtils.showToast(getContext(), "加载中..."));
    }

    @Override
    protected ChatWithPresenter setPresenter() {
        return new ChatWithPresenter();
    }

    @OnClick(R2.id.btn_chat_sendText)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chat_sendText:
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(R2.id.et_chat_inputMsg)
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            mSendMore.setVisibility(View.GONE);
            mSendText.setVisibility(View.VISIBLE);
        } else {
            mSendMore.setVisibility(View.VISIBLE);
            mSendText.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
