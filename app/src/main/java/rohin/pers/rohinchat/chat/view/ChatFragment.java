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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.chat.adapter.ChatRecyclerViewAdapter;
import rohin.pers.rohinchat.chat.presenter.ChatPresenter;
import rohin.pers.rohinchat.data.eneity.SimpleChat;
import rohin.pers.rohinchat.chat.contract.ChatContract;
import rohin.pers.rohinchat.common.design.recyclerview.itemdecoration.MultipleDividerItemDecoration;
import rohin.pers.rohinchat.common.design.widget.SavePoiRecyclerView;
import rohin.pers.rohinchat.common.listener.OnDoubleClickListener;
import rohin.pers.rohinchat.common.util.ToastUtils;

import static rohin.pers.rohinchat.common.base.Preconditions.checkNotNull;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ChatFragment extends BaseFragment implements ChatContract.View {

    private static final String TAG = "ChatFragment";

    private static final String SAVE_INSTANCE_STATE_KEY = "chatRvPoi";

    private OnSimpleChatItemClickListener mListener;

    private ChatContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private Toolbar toolbar;

    private ContentLoadingProgressBar mContentLoadingProgressBar;

    public static ChatFragment getInstance() {
        return new ChatFragment();
    }

    public static ChatFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString("FRAGMENT", value);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        mContentLoadingProgressBar = view.findViewById(R.id.progress_bar_loading);

        mRecyclerView = view instanceof RecyclerView
                ? (SavePoiRecyclerView) view
                : view.findViewById(R.id.rv_main_list_simplechat);

        toolbar = null == getActivity()
                ? view.findViewById(R.id.toolbar)
                : getActivity().findViewById(R.id.toolbar);

        mRecyclerView.addItemDecoration(
                new MultipleDividerItemDecoration.Builder(
                        MultipleDividerItemDecoration.VERTICAL_BOTTOM).build());

        mPresenter = new ChatPresenter(this);

        return view;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.chat_fragment_chat;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setOnClickListener(
                new OnDoubleClickListener(() -> mRecyclerView.scrollToPosition(0)));
        mPresenter.subscribe();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView.setAdapter(null);
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
    public void setPresenter(ChatContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
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
        ToastUtils.showToast(getContext(), "无聊天信息！");
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
