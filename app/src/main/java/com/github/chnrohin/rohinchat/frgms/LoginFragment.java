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

package com.github.chnrohin.rohinchat.frgms;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.base.BaseMvpFragment;
import com.github.chnrohin.rohinchat.common.util.ToastUtils;
import com.github.chnrohin.rohinchat.acts.MainActivity;
import com.github.chnrohin.rohinchat.common.util.MatchesUtils;
import com.github.chnrohin.rohinchat.common.design.widget.ClearEditText;
import com.github.chnrohin.rohinchat.contract.LoginContract;
import com.github.chnrohin.rohinchat.contract.UserContract;
import com.github.chnrohin.rohinchat.presenter.LoginPresenter;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class LoginFragment extends BaseMvpFragment<LoginContract.View, LoginPresenter>
        implements UserContract.View, View.OnClickListener {

    @BindView(R2.id.ll_user_login_sms_verify)
    LinearLayout mLlSmsVerify;
    @BindView(R2.id.til_user_login_pasw)
    TextInputLayout mTilPasw;
    @BindView(R2.id.tv_user_toggle_login_type)
    TextView mTvType;
    @BindView(R2.id.cet_uesr_login_phone)
    ClearEditText mCetPhone;
    @BindView(R2.id.cet_user_login_pasw)
    ClearEditText mCetPasw;
    @BindView(R2.id.cet_user_login_sms_verify)
    ClearEditText mCetSmsVerify;
    @BindView(R2.id.btn_user_login)
    Button mBtnLogin;
    @BindString(R2.string.toggle_type_pasw)
    String mToggleTypePasw;
    @BindString(R2.string.toggle_type_sms_verify)
    String mToggleTypeSmsVerify;
    @BindString(R2.string.action_next)
    String mActionNext;
    @BindString(R2.string.account_login)
    String mActionLogin;

    private UserContract.Presenter mPresenter;

    public static LoginFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString("FRAGMENT", value);
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.user_fragment_login;
    }

    @OnClick({R2.id.tv_user_toggle_login_type, R2.id.btn_user_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user_toggle_login_type:
                togglePaswOrSmsVerify();
                break;
            case R.id.btn_user_login:
                attemptLogin();
                break;
            default:
                break;
        }
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter();
    }

    public void attemptLogin() {
        if (mActionNext.equals(mBtnLogin.getText().toString())) {
            if (MatchesUtils.isPhone(mCetPhone)) {
                mTvType.setVisibility(View.VISIBLE);
                mTilPasw.setVisibility(View.VISIBLE);
                mTvType.setText(mToggleTypeSmsVerify);
                mBtnLogin.setText(mActionLogin);
            }
        } else if (mActionLogin.equals(mBtnLogin.getText().toString())) {
            if (mCetPasw.getVisibility() == View.VISIBLE) {
                if (MatchesUtils.isPhone(mCetPhone) && MatchesUtils.isPassword(mCetPasw)) {
                    ToastUtils.showToast(getContext(), "密码登录成功");
                }
            } else if (mCetSmsVerify.getVisibility() == View.VISIBLE) {
                if (MatchesUtils.isPhone(mCetPhone) && MatchesUtils.isPin(mCetSmsVerify)) {
                    ToastUtils.showToast(getContext(), "短信登录成功");
                }
            }
        }
    }

    public void turnToMain() {
        startActivity(MainActivity.newIntent(getContext()));
    }

    private void togglePaswOrSmsVerify() {
        String toggleText = mTvType.getText().toString();
        if (mToggleTypePasw.equals(toggleText)) {
            mTvType.setText(mToggleTypeSmsVerify);
            mTilPasw.setVisibility(View.VISIBLE);
            mLlSmsVerify.setVisibility(View.GONE);
        } else if (mToggleTypeSmsVerify.equals(toggleText)) {
            mTvType.setText(mToggleTypePasw);
            mTilPasw.setVisibility(View.GONE);
            mLlSmsVerify.setVisibility(View.VISIBLE);
        }
    }
}