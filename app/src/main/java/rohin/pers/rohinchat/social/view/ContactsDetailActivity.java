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

package rohin.pers.rohinchat.social.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import rohin.pers.rohinchat.base.BaseActivity;
import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.R2;
import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.data.eneity.Contacts;
import rohin.pers.rohinchat.common.util.ImageUtils;
import rohin.pers.rohinchat.common.util.LogUtils;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ContactsDetailActivity extends BaseActivity {

    public static final String EXTRA_PARCELABLE_JAVABEAN_CONTACTS = "JavaBean.Contacts";
    public static final String EXCEPTION_NPE_CONTACTS = "npe";

    private Contacts mContacts;

    @BindView(R2.id.iv_social_contactsProfilePic)
    ImageView mContactsProfilePic;
    @BindView(R2.id.tv_social_remarkName)
    TextView mRemarkName;
    @BindView(R2.id.tv_social_nickname)
    TextView mNickname;
    @BindView(R2.id.tv_social_chatId)
    TextView mChatId;
    @BindView(R2.id.tv_social_location)
    TextView mLocation;

    public static Intent newIntent(Context packageContext, Contacts contacts) {
        Intent intent = new Intent(packageContext, ContactsDetailActivity.class);
        intent.putExtra(EXTRA_PARCELABLE_JAVABEAN_CONTACTS, contacts);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContacts = getIntent().getParcelableExtra(EXTRA_PARCELABLE_JAVABEAN_CONTACTS);
        super.onCreate(savedInstanceState);

        byte[] bytes = mContacts.getProfilePic();
        if (null != bytes && bytes.length > 0) {
            mContactsProfilePic.setImageBitmap(ImageUtils.convertBytesToBitmap(bytes, null));
        }
        mRemarkName.setText(mContacts.getNickname());
        mChatId.setText(mContacts.getUuid() == null ? "罗信号：error" : mContacts.getUuid());
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.social_activity_contacts_detail;
    }

    @Override
    protected void handleToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setNavigationIcon(R.drawable.toolbar_icon_back_nor);
        if (null != mContacts) {
            String contactName = mContacts.getNickname();
            toolbarHelper.setTitle("".equals(contactName)
                    ? getResources().getString(R.string.error_unKnow_contact)
                    : mContacts.getNickname());
        } else {
            LogUtils.e("未获取到联系人，弹出警告并留在原界面");
            Intent intent = new Intent();
            intent.putExtra(EXCEPTION_NPE_CONTACTS, R.string.error_null_contact);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected BaseFragment getFragmentInstance() {
        return null;
    }

    @Override
    protected int getContainerViewId() {
        return 0;
    }
}
