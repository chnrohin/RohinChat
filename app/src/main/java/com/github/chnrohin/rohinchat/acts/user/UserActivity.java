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

package com.github.chnrohin.rohinchat.acts.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import com.github.chnrohin.rohinchat.base.BaseActivity;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.base.BaseFragment;
import com.github.chnrohin.rohinchat.common.design.widget.SlideViewPager;
import com.github.chnrohin.rohinchat.frgms.LoginFragment;
import com.github.chnrohin.rohinchat.frgms.RegisterFragment;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class UserActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.iv_user_account_close) ImageView mIvBack;
    @BindView(R2.id.tv_user_account_login) TextView mTvLogin;
    @BindView(R2.id.tv_user_account_register) TextView mRegisterTextView;
    @BindView(R2.id.svp_user_login_contentFragment)
    SlideViewPager mViewPager;

    /**
     * 标识跟踪要申请的权限，并在{@link BaseActivity#onRequestPermissionsResult}方法中使用该值。
     */
    protected static final int REQUEST_CODE = 1714;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 登录和注册界面禁止截屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);

        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(mFragmentPagerAdapter);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.user_activity_login;
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
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    @OnClick({R2.id.iv_user_account_close, R2.id.tv_user_account_retrieve, R2.id.tv_user_account_login, R2.id.tv_user_account_register})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_account_close:
                finish();
                break;
            case R.id.tv_user_account_login:
                mViewPager.setCurrentItem(0);
                mTvLogin.setVisibility(View.GONE);
                mRegisterTextView.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_user_account_register:
                mViewPager.setCurrentItem(1);
                mTvLogin.setVisibility(View.VISIBLE);
                mRegisterTextView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private ArrayList<Fragment> mFragmentList = getFragmentList();

    private ArrayList<Fragment> getFragmentList() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(LoginFragment.newInstance("Login"));
        mFragmentList.add(RegisterFragment.newInstance("Register"));
        return mFragmentList;
    }

    private FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    };
}