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
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rohin.pers.rohinchat.base.BaseActivity;
import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.R2;
import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.common.design.widget.ClearEditText;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class AddContactsActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.tv_toolbar_add_contacts_my_qrcode)
    TextView mMyQrCode;
    @BindView(R2.id.cet_toolbar_add_contacts_input)
    ClearEditText mInput;
    @BindView(R2.id.dtv_toolbar_add_contacts_search)
    TextView mSearch;

    public static Intent newIntent(Context packageContext) {
//        Intent intent = new Intent(packageContext, AddContactsActivity.class);
        return new Intent(packageContext, AddContactsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearch.setVisibility(View.GONE);
    }

    @OnClick(R2.id.tv_toolbar_add_contacts_my_qrcode)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_toolbar_add_contacts_my_qrcode:
                break;
        }
    }

    @OnTextChanged(R2.id.cet_toolbar_add_contacts_input)
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(R2.id.cet_toolbar_add_contacts_input)
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mSearch.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
        highlightSearchContent(s);
    }

    @OnTextChanged(R2.id.cet_toolbar_add_contacts_input)
    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.social_activity_add_contacts;
    }

    @Override
    protected void handleToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle(R.string.action_add_contacts);
        toolbarHelper.setNavigationIcon(R.drawable.toolbar_icon_back_nor);
    }

    @Override
    protected BaseFragment getFragmentInstance() {
        return null;
    }

    @Override
    protected int getContainerViewId() {
        return 0;
    }

    /**
     * 高亮显示要搜索的内容
     */
    private void highlightSearchContent(CharSequence s) {
        String searchContent = String.format(getString(R.string.action_search_contacts), s);
        SpannableStringBuilder spannable = new SpannableStringBuilder(searchContent);
        int color;
        if (Build.VERSION.SDK_INT >= 23) {
            color = getColor(R.color.colorLogo);
        } else {
            color = 0x0050CD36;
        }
        // 忽略默认字符串 -> "搜索："，索引从3开始。
        int startIndex = 3;
        int endIndex = 3 + s.length();
        spannable.setSpan(new ForegroundColorSpan(color), startIndex, endIndex,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mSearch.setText(spannable);
    }

}
