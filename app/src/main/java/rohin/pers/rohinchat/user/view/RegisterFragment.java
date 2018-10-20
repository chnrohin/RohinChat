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

package rohin.pers.rohinchat.user.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.R2;
import rohin.pers.rohinchat.common.util.TextUtils;
import rohin.pers.rohinchat.user.contract.UserContract;
import rohin.pers.rohinchat.common.util.MatchesUtils;
import rohin.pers.rohinchat.common.util.ToastUtils;
import rohin.pers.rohinchat.common.design.widget.ClearEditText;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class RegisterFragment extends BaseFragment implements UserContract.View, View.OnClickListener, TextWatcher {

    @BindView(R2.id.ll_user_register_pin) LinearLayout lay_pin;
    @BindView(R2.id.til_user_register_nickname) TextInputLayout lay_nickname;
    @BindView(R2.id.til_user_register_phone) TextInputLayout lay_phone;
    @BindView(R2.id.til_user_register_pasw) TextInputLayout lay_pasw;
    @BindView(R2.id.cet_user_register_nickname) ClearEditText cet_nickname;
    @BindView(R2.id.cet_user_register_phone) ClearEditText cet_phone;
    @BindView(R2.id.cet_user_register_pasw) ClearEditText cet_pasw;
    @BindView(R2.id.cet_user_register_sms_verify) ClearEditText cet_smsVerify;
    @BindView(R2.id.btn_user_register) Button btn_register;
    @BindString(R2.string.action_next) String actionNext;
    @BindString(R2.string.action_register) String actionRegister;

    private UserContract.Presenter mPresenter;

    public static RegisterFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString("FRAGMENT", value);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        return view;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.user_fragment_register;
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
        if (TextUtils.isEmpty(cet_nickname.getText())
                && TextUtils.isEmpty(cet_phone.getText())
                && TextUtils.isEmpty(cet_pasw.getText())) {

            btn_register.setEnabled(true);
        } else {
            btn_register.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void attemptRegister() {
        String btnRegister = btn_register.getText().toString().trim();
        if (actionNext.equals(btnRegister)) {
            if (MatchesUtils.isNickname(cet_nickname) && MatchesUtils.isPhone(cet_phone)
                    && MatchesUtils.isPassword(cet_pasw)) {
                btn_register.setText(actionRegister);
                lay_nickname.setVisibility(View.GONE);
                lay_phone.setVisibility(View.GONE);
                lay_pasw.setVisibility(View.GONE);
                lay_pin.setVisibility(View.VISIBLE);
            }
        } else if (actionRegister.equals(btnRegister)) {
            if (MatchesUtils.isPin(cet_smsVerify)) {
                ToastUtils.showToast(getContext(), "注册成功");
            }
        }
    }

    private void init() {
        setErrorToNull();
    }

    private void setErrorToNull() {
        cet_nickname.setError(null);
        cet_phone.setError(null);
        cet_pasw.setError(null);
        cet_smsVerify.setError(null);
    }
}
