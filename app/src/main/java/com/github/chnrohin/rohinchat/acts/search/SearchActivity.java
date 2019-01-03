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

package com.github.chnrohin.rohinchat.acts.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnTextChanged;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.base.BaseActivity;
import com.github.chnrohin.rohinchat.base.BaseFragment;
import com.github.chnrohin.rohinchat.common.design.widget.ClearEditText;
import com.github.chnrohin.rohinchat.common.util.ToastUtils;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class SearchActivity extends BaseActivity implements TextWatcher {

    @BindView(R2.id.iv_toolbar_back)
    ImageView mBack;
    @BindView(R2.id.cet_toolbar_action_search_content)
    ClearEditText mSearchContent;
    @BindView(R2.id.tv_toolbar_back)
    TextView mSearchOrBack;

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, SearchActivity.class);
    }

    @Override
    protected void init() {
        super.init();

        mBack.setOnClickListener(item -> finish());
        mSearchOrBack.setOnClickListener(item -> {
            if("取消".equals(mSearchOrBack.getText().toString())) {
                finish();
            } else {
                ToastUtils.showToast(this, "action search");
            }
        });
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.search_activity_search;
    }

    @Override
    protected BaseFragment getFragmentInstance() {
        return null;
    }

    @Override
    protected int getContainerViewId() {
        return 0;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(R2.id.cet_toolbar_action_search_content)
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mSearchOrBack.setText(s.length() > 0 ? R.string.action_search : R.string.action_cancel);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
