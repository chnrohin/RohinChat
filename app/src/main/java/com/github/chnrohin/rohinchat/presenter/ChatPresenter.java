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

package com.github.chnrohin.rohinchat.presenter;

import android.support.annotation.NonNull;

import com.github.chnrohin.rohinchat.base.BasePresenter;
import com.github.chnrohin.rohinchat.contract.ChatContract;
import com.github.chnrohin.rohinchat.model.ChatModel;
import com.github.chnrohin.rohinchat.model.ChatModelImpl;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class ChatPresenter extends BasePresenter<ChatContract.View>
        implements ChatContract.Presenter {

    @NonNull
    private ChatModel mModel = new ChatModelImpl();

    private boolean mFirstLoad = true;

    public ChatPresenter() {
    }

    @Override
    public void subscribe() {
        loadingChatList(true);
    }

    @Override
    public void unsubscribe() {
        mModel.clear();
    }

    @Override
    public void loadingChatList(boolean forceUpdate) {
        loadingChatList(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadingChatList(final boolean forceUpdate, final boolean showLoadingUI) {
        mView.showLoadingIndicator(showLoadingUI);
        if (forceUpdate) {
            mView.refresh();
        }
        mModel.fetchSimpleChatList(simpleChats -> {
            if (simpleChats != null) {
                mView.showLoadingIndicator(false);
                mView.showChatList(simpleChats);
            } else {
                mView.showNoData();
            }
        });
    }
}
