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

package com.github.chnrohin.rohinchat.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import com.github.chnrohin.rohinchat.R2;

/**
 * @author Rohin
 * @date 2018/11/1
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void init() {
        super.init();

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            ToolbarHelper mToolbarHelper = new ToolbarHelper(mToolbar);
            handleToolbar(mToolbarHelper);
        }
    }

    /**
     * 子类用此方法可灵活操作 toolbar。
     * @param toolbarHelper toolbar相关操作。
     */
    protected abstract void handleToolbar(ToolbarHelper toolbarHelper);

    protected class ToolbarHelper {

        private Toolbar mToolbar;

        ToolbarHelper(Toolbar toolbar) {
            mToolbar = toolbar;
        }

        public Toolbar getToolbar() {
            return mToolbar;
        }

        public void setTitle(@StringRes int resId) {
            mToolbar.setTitle(resId);
        }

        public void setTitle(String title) {
            mToolbar.setTitle(title);
        }

        public void setNavigationIcon(@DrawableRes int resId) {
            mToolbar.setNavigationIcon(resId);
            setNavigationOnClickListener();
        }

        public void setNavigationIcon(Drawable icon) {
            mToolbar.setNavigationIcon(icon);
            setNavigationOnClickListener();
        }

        private void setNavigationOnClickListener() {
            if (mToolbar != null) {
                mToolbar.setNavigationOnClickListener((item) -> finish());
            }
        }

    }
}
