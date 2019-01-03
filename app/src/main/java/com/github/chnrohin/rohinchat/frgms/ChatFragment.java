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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;

import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.adapter.ChatRecyclerViewAdapter;
import com.github.chnrohin.rohinchat.base.BaseMvpFragment;
import com.github.chnrohin.rohinchat.contract.ChatContract;
import com.github.chnrohin.rohinchat.data.entity.SimpleChat;
import com.github.chnrohin.rohinchat.common.design.recyclerview.itemdecoration.MultiItemDecoration;
import com.github.chnrohin.rohinchat.common.listener.OnDoubleClickListenerKt;
import com.github.chnrohin.rohinchat.presenter.ChatPresenter;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ChatFragment extends BaseMvpFragment<ChatContract.View, ChatPresenter>
        implements ChatContract.View {

    private static final String KEY = "ChatFragment";

    private OnSimpleChatItemClickListener mListener;

    @BindView(R2.id.rv_chat_simplechat)
    RecyclerView mRecyclerView;
    @BindView(R2.id.progress_bar_loading)
    ContentLoadingProgressBar mContentLoadingProgressBar;

    public static ChatFragment getInstance() {
        return new ChatFragment();
    }

    public static ChatFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString(KEY, value);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSimpleChatItemClickListener) {
            mListener = (OnSimpleChatItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement OnSimpleChatItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.chat_fragment_chat;
    }

    @Override
    protected void init() {
        super.init();

        if (null != getActivity()) {
            getActivity().findViewById(R.id.toolbar).setOnClickListener(
                    new OnDoubleClickListenerKt(() -> mRecyclerView.scrollToPosition(0)));
        }

        mRecyclerView.addItemDecoration(new MultiItemDecoration.Builder().build());
    }

    @Override
    protected void release() {
        super.release();
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        if (active) {
            mContentLoadingProgressBar.show();
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mContentLoadingProgressBar.hide();
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showChatList(List<SimpleChat> simpleChats) {
        mRecyclerView.setAdapter(new ChatRecyclerViewAdapter(simpleChats, mListener));
    }

    @Override
    public void refresh() {

    }

    @Override
    protected ChatPresenter setPresenter() {
        return new ChatPresenter();
    }

    /**
     * google mvp架构demo原文解释：
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * 装载此 Fragment的 Activity必须实现此接口的方法，否则将抛运行时异常。
     */
    public interface OnSimpleChatItemClickListener {
        /**
         * 此方法将{@link SimpleChat}传递给 Activity或者潜在的其他 Fragment 进行交互。
         *
         * @param simpleChat 聊天列表实体
         */
        void onSimpleChatItemClick(SimpleChat simpleChat);
    }

}
