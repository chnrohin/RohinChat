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

package rohin.pers.rohinchat.chat.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.R2;
import rohin.pers.rohinchat.chat.adapter.MsgExRecyclerViewAdapter;
import rohin.pers.rohinchat.data.eneity.Message;
import rohin.pers.rohinchat.chat.contract.ChatContract;
import rohin.pers.rohinchat.data.eneity.SimpleChat;
import rohin.pers.rohinchat.data.dummy.MessageList;
import rohin.pers.rohinchat.common.listener.OnDoubleClickListener;
import rohin.pers.rohinchat.chat.presenter.ChatWithPresenter;
import rohin.pers.rohinchat.common.util.ToastUtils;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ChatWithFragment extends BaseFragment implements ChatContract.View,
        View.OnClickListener, TextWatcher {

    private static final String KEY = "FRAGMENT";

    private ChatContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private List<Message> mMessageList = MessageList.getInstance().getMessages();

    @BindView(R2.id.et_chat_inputMsg)
    EditText mInputMsg;
    @BindView(R2.id.iv_chat_sendMore)
    ImageView mSendMore;
    @BindView(R2.id.btn_chat_sendText)
    Button mSendText;
    @BindView(R2.id.srl_chat_loadMoreMsgEx)
    SwipeRefreshLayout mLoadMoreMsgEx;

    private OnDoubleClickListener.ICallBack mDoubleClickListener = () ->
            mRecyclerView.scrollToPosition(mMessageList.size());

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

    private void init() {
        initView();
        initListener();
    }

    private void initView() {
        mInputMsg = view.findViewById(R.id.et_chat_inputMsg);
        mSendMore = view.findViewById(R.id.iv_chat_sendMore);
        mSendText = view.findViewById(R.id.btn_chat_sendText);
        mLoadMoreMsgEx = view.findViewById(R.id.srl_chat_loadMoreMsgEx);
    }

    private void initListener() {
        mLoadMoreMsgEx.setOnRefreshListener(() -> ToastUtils.showToast(getContext(), "加载中..."));
    }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Toolbar toolbar = getActivity() != null
                ? getActivity().findViewById(R.id.toolbar)
                : view.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new OnDoubleClickListener(mDoubleClickListener));

        mRecyclerView = view.findViewById(R.id.rv_chat_msgEx);
        mRecyclerView.setAdapter(new MsgExRecyclerViewAdapter(mMessageList));

        mPresenter = new ChatWithPresenter();

        init();

        return view;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.chat_fragment_chat_with;
    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        mPresenter = presenter;
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

    @Override
    public void showLoadingIndicator(boolean active) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showChatList(List<SimpleChat> tList) {

    }
}
