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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.social.adapter.ContactRecyclerViewAdapter;
import rohin.pers.rohinchat.data.eneity.Contacts;
import rohin.pers.rohinchat.social.contract.ContactsContract;
import rohin.pers.rohinchat.common.design.recyclerview.itemdecoration.MultipleDividerItemDecoration;
import rohin.pers.rohinchat.common.design.widget.SlideSideBar;
import rohin.pers.rohinchat.common.listener.OnDoubleClickListener;
import rohin.pers.rohinchat.social.presenter.ContactsPresenter;
import rohin.pers.rohinchat.common.util.ToastUtils;

import static rohin.pers.rohinchat.common.base.Preconditions.checkNotNull;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ContactsFragment extends BaseFragment implements ContactsContract.View {

    public static final String KEY = "FRAGMENT";

    public static final int REQUEST_CODE = 1333;

    private OnContactItemClickListener mContactItemClickListener;

    private ContactsContract.Presenter mPresenter;

    RecyclerView mRecyclerView;

    Toolbar toolbar;

    SlideSideBar mSideNavigation;

    TextView mCodePrompt;

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

    private void init() {
        initView();
        initSlideSideBar();
    }

    private void initView() {
        mSideNavigation = view.findViewById(R.id.ssb_main_sideNavigation);
        mCodePrompt = view.findViewById(R.id.tv_main_codePrompt);
    }

    private void initSlideSideBar() {
        mSideNavigation.setCodePrompt(mCodePrompt);
        mSideNavigation.setOnTouchCodesChangeListener((code -> turnCode = code));
    }

    private void showSlideSideBar() {
        mSideNavigation.setVisibility(View.VISIBLE);
    }

    private void hideSlideSideBar() {
        mSideNavigation.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        读取通讯录权限
//        PermissionUtils permission = PermissionUtils.getInstance(CHAT_WITH_REQUEST_CODE);
//        permission.getDefaultPermissionInContacts(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        mRecyclerView = view instanceof RecyclerView
                ? (RecyclerView) view
                : view.findViewById(R.id.rv_main_list_contacts);

        toolbar = null == getActivity()
                ? view.findViewById(R.id.toolbar)
                : getActivity().findViewById(R.id.toolbar);

        mRecyclerView.addItemDecoration(
                new MultipleDividerItemDecoration.Builder(
                        MultipleDividerItemDecoration.VERTICAL_TOP)
                        .height(50)
                        .build()
        );

        mRecyclerView.addItemDecoration(
                new MultipleDividerItemDecoration.Builder(
                        MultipleDividerItemDecoration.VERTICAL_BOTTOM)
                        .item(MultipleDividerItemDecoration.LIST_ITEM)
                        .matchPadding(true)
                        .build()
        );

        mPresenter = new ContactsPresenter(this);

        init();

        return view;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.social_fragment_contacts;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setOnClickListener(
                new OnDoubleClickListener(() -> mRecyclerView.scrollToPosition(0)));
        mPresenter.subscribe();
        showSlideSideBar();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
        hideSlideSideBar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnContactItemClickListener) {
            mContactItemClickListener = (OnContactItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implements OnContactItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContactItemClickListener = null;
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

    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
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
