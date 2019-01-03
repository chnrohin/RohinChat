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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.base.BaseMvpFragment;
import com.github.chnrohin.rohinchat.common.util.MatchesUtils;
import com.github.chnrohin.rohinchat.common.util.ToastUtils;
import com.github.chnrohin.rohinchat.common.design.widget.ClearEditText;
import com.github.chnrohin.rohinchat.contract.RegisterContract;
import com.github.chnrohin.rohinchat.presenter.RegisterPresenter;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class RegisterFragment extends BaseMvpFragment<RegisterContract.View, RegisterPresenter>
        implements RegisterContract.View, View.OnClickListener, TextWatcher {

    @BindView(R2.id.ll_user_registerPin)
    LinearLayout mLlPin;
    @BindView(R2.id.til_user_registerNickname)
    TextInputLayout mNicknameTil;
    @BindView(R2.id.til_user_registerPhone)
    TextInputLayout mPhoneTil;
    @BindView(R2.id.til_user_registerPasw)
    TextInputLayout mPaswTil;
    @BindView(R2.id.cet_user_registerNickname)
    ClearEditText mNicknameCet;
    @BindView(R2.id.cet_user_registerPhone)
    ClearEditText mPhoneCet;
    @BindView(R2.id.cet_user_register_pasw)
    ClearEditText mPaswCet;
    @BindView(R2.id.cet_user_registerSmsVerify)
    ClearEditText mSmsverifyCet;
    @BindView(R2.id.btn_user_register)
    Button mRegisterBtn;
    @BindString(R2.string.action_next)
    String actionNext;
    @BindString(R2.string.action_register)
    String actionRegister;

    public static RegisterFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString("FRAGMENT", value);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.user_fragment_register;
    }

    @Override
    protected void init() {
        super.init();

        setErrorToNull();
    }

    @OnClick(R2.id.btn_user_register)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_register:
                attemptRegister();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(mNicknameCet.getText())
                && TextUtils.isEmpty(mPhoneCet.getText())
                && TextUtils.isEmpty(mPaswCet.getText())) {

            mRegisterBtn.setEnabled(true);
        } else {
            mRegisterBtn.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected RegisterPresenter setPresenter() {
        return new RegisterPresenter();
    }

    public void attemptRegister() {
        String btnRegister = mRegisterBtn.getText().toString().trim();
        if (actionNext.equals(btnRegister)) {
            if (MatchesUtils.isNickname(mNicknameCet) && MatchesUtils.isPhone(mPhoneCet)
                    && MatchesUtils.isPassword(mPaswCet)) {
                mRegisterBtn.setText(actionRegister);
                mNicknameTil.setVisibility(View.GONE);
                mPhoneTil.setVisibility(View.GONE);
                mPaswTil.setVisibility(View.GONE);
                mLlPin.setVisibility(View.VISIBLE);
            }
        } else if (actionRegister.equals(btnRegister)) {
            if (MatchesUtils.isPin(mSmsverifyCet)) {
                ToastUtils.showToast(getContext(), "注册成功");
            }
        }
    }

    private void setErrorToNull() {
        mNicknameCet.setError(null);
        mPhoneCet.setError(null);
        mPaswCet.setError(null);
        mSmsverifyCet.setError(null);
    }
}
