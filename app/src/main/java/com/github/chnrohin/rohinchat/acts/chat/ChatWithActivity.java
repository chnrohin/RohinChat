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

package com.github.chnrohin.rohinchat.acts.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.base.BaseFragment;
import com.github.chnrohin.rohinchat.base.BaseToolbarActivity;
import com.github.chnrohin.rohinchat.frgms.ChatWithFragment;
import com.github.chnrohin.rohinchat.data.entity.SimpleChat;
import com.github.chnrohin.rohinchat.common.util.LogUtils;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ChatWithActivity extends BaseToolbarActivity {

    public static final String EXTRA_PARCELABLE_JAVABEAN_SIMPLECHAT = "JavaBean.SimpleChat";
    public static final String EXCEPTION_NPE_CONTACTS = "npe";

    private SimpleChat mSimpleChat;

    public static Intent newIntent(Context packageContext, SimpleChat simpleChat) {
        Intent intent = new Intent(packageContext, ChatWithActivity.class);
        intent.putExtra(EXTRA_PARCELABLE_JAVABEAN_SIMPLECHAT, simpleChat);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSimpleChat = getIntent().getParcelableExtra(EXTRA_PARCELABLE_JAVABEAN_SIMPLECHAT);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.chat_activity_chat_with;
    }

    @Override
    public void handleToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setNavigationIcon(R.drawable.toolbar_icon_back_nor);
        if (null != mSimpleChat) {
            String contactName = mSimpleChat.getContacts().getNickname();
            toolbarHelper.setTitle("".equals(contactName)
                    ? getResources().getString(R.string.error_unKnow_contact)
                    : mSimpleChat.getContacts().getNickname());
        } else { // 未获取到联系人，返回主界面
            LogUtils.e("未获取到联系人，弹出警告并留在原界面");
            Intent intent = new Intent();
            intent.putExtra(EXCEPTION_NPE_CONTACTS, R.string.error_null_contact);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_toolbar_chat_with, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chat_info:
                startActivity(ChatInfoActivity.newIntent(this, mSimpleChat));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected BaseFragment getFragmentInstance() {
        return ChatWithFragment.getInstance();
    }

    @Override
    protected int getContainerViewId() {
        return R.id.fl_chat_contentFrame;
    }
}
