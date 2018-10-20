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

package rohin.pers.rohinchat.chat.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import rohin.pers.rohinchat.data.eneity.SimpleChat;
import rohin.pers.rohinchat.chat.contract.ChatContract;
import rohin.pers.rohinchat.chat.model.ChatModel;
import rohin.pers.rohinchat.chat.model.ChatModelImpl;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class ChatPresenter implements ChatContract.Presenter {

    @NonNull
    private final ChatContract.View mView;

    @NonNull
    private ChatModel mModel;

    private boolean mFirstLoad = true;

    public ChatPresenter(@NonNull ChatContract.View view) {
        mView = view;
        mModel = new ChatModelImpl();
        mView.setPresenter(this);
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
        if (showLoadingUI) {
            mView.showLoadingIndicator(true);
        }
        if (forceUpdate) {
            mView.refresh();
        }
        List<SimpleChat> simpleChats = mModel.fetchSimpleChatList();
        if (simpleChats != null) {
            mView.showLoadingIndicator(false);
            mView.showChatList(simpleChats);
        } else {
            mView.showNoData();
        }
    }
}
