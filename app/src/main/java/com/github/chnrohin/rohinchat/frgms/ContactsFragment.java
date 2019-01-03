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

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.adapter.ContactRecyclerViewAdapter;
import com.github.chnrohin.rohinchat.base.BaseMvpFragment;
import com.github.chnrohin.rohinchat.contract.ContactsContract;
import com.github.chnrohin.rohinchat.data.entity.Contacts;
import com.github.chnrohin.rohinchat.common.design.recyclerview.itemdecoration.MultiItemDecoration;
import com.github.chnrohin.rohinchat.common.design.widget.SlideSideBar;
import com.github.chnrohin.rohinchat.common.util.ToastUtils;
import com.github.chnrohin.rohinchat.presenter.ContactsPresenter;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ContactsFragment extends BaseMvpFragment<ContactsContract.View, ContactsPresenter>
        implements ContactsContract.View {

    public static final String KEY = "ContactsFragment";

    public static final int REQUEST_CODE = 1333;

    private OnContactItemClickListener mContactItemClickListener;

    @BindView(R2.id.rv_social_list_contacts)
    RecyclerView mRecyclerView;
    @BindView(R2.id.ssb_social_sideNavigation)
    SlideSideBar mSideNavigationSsb;
    @BindView(R2.id.tv_social_codePrompt)
    TextView mCodePromptTv;

    private char turnCode;

    public static ContactsFragment getInstance() {
        return new ContactsFragment();
    }

    public static ContactsFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString(KEY, value);
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.subscribe();
        mSideNavigationSsb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
        mSideNavigationSsb.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnContactItemClickListener) {
            mContactItemClickListener = (OnContactItemClickListener) context;
        } else {
            String e = context.toString() + " must implements OnContactItemClickListener";
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContactItemClickListener = null;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.social_fragment_contacts;
    }

    @Override
    protected void init() {
        super.init();

        mRecyclerView.addItemDecoration(new MultiItemDecoration.Builder()
                .fill(MultiItemDecoration.FILL_LI_WITHOUT_PADDING)
                .build());

        mSideNavigationSsb.setCodePrompt(mCodePromptTv);
        mSideNavigationSsb.setOnTouchCodesChangeListener(code -> turnCode = code);
    }

    @Override
    protected ContactsPresenter setPresenter() {
        return new ContactsPresenter();
    }

    @Override
    public void showContactsList(List<Contacts> contactsList) {
        mRecyclerView.setAdapter(
                new ContactRecyclerViewAdapter(contactsList, mContactItemClickListener));
    }

    @Override
    public void showLoadingIndicator(boolean active) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showNoData() {
        ToastUtils.showToast(getContext(), "无联系人信息！");
    }


    /**
     * 装载此 Fragment的 Activity必须实现此接口的方法，否则将抛运行时异常。
     */
    public interface OnContactItemClickListener {
        /**
         * 此方法将{@link Contacts}传递给 Activity或者潜在的其他 Fragment 进行交互。
         *
         * @param contacts 联系人实体
         */
        void onContactItemClick(Contacts contacts);
    }

}
