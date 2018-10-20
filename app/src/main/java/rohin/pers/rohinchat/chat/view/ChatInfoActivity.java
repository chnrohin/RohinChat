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
import android.content.Intent;
import android.os.Bundle;

import rohin.pers.rohinchat.base.BaseActivity;
import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.data.eneity.SimpleChat;
import rohin.pers.rohinchat.common.util.ActivityUtils;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ChatInfoActivity extends BaseActivity {

    public static final String EXTRA_PARCELABLE_JAVABEAN_SIMPLECHAT = "JavaBean.SimpleChat";

    private SimpleChat mSimpleChat;

    public static Intent newIntent(Context packageContext, SimpleChat simpleChat) {
        Intent intent = new Intent(packageContext, ChatInfoActivity.class);
        intent.putExtra(EXTRA_PARCELABLE_JAVABEAN_SIMPLECHAT, simpleChat);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSimpleChat = getIntent().getParcelableExtra(ChatWithActivity.EXTRA_PARCELABLE_JAVABEAN_SIMPLECHAT);
        super.onCreate(savedInstanceState);
//        mToolbar.setNavigationOnClickListener((item) -> finish());
    }

    @Override
    public int getLayoutResID() {
        return R.layout.chat_activity_chat_info;
    }

    @Override
    public void handleToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle(R.string.title_chat_info);
        toolbarHelper.setNavigationIcon(R.drawable.toolbar_icon_back_nor);
    }

    @Override
    protected BaseFragment getFragmentInstance() {
        return ChatInfoFragment.getInstance();
    }

    @Override
    protected int getContainerViewId() {
        return R.id.sv_chat_info_contentFrame;
    }
}
