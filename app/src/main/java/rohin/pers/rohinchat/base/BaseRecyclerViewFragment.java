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

package rohin.pers.rohinchat.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.common.listener.OnDoubleClickListener;
import rohin.pers.rohinchat.common.util.LogUtils;
import rohin.pers.rohinchat.common.base.Preconditions;
import rohin.pers.rohinchat.common.util.TextUtils;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public abstract class BaseRecyclerViewFragment extends BaseFragment {

    protected HashMap<String, RecyclerView> mRecyclerViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        String key = TextUtils.checkNotEmpty(getRecyclerViewKey());
        RecyclerView recyclerView = Preconditions.checkNotNull(getRecyclerView());

        // 初始化HashMap
        if (mRecyclerViews == null) {
            mRecyclerViews = new HashMap<>();
        }

        // 更新RecyclerView
        if (mRecyclerViews.containsKey(key)) {
            if (Build.VERSION.SDK_INT >= 24) {
                mRecyclerViews.replace(key, recyclerView);
            } else {
                mRecyclerViews.remove(key);
                mRecyclerViews.put(key, recyclerView);
            }
        } else {
            mRecyclerViews.put(key, recyclerView);
        }

        Toolbar toolbar;
        try {
            toolbar = getActivity().findViewById(R.id.toolbar);
        } catch (NullPointerException e) {
            LogUtils.e("Can not findViewById(R.id.toolbar) from getActivity()");
            toolbar = view.findViewById(R.id.toolbar);
        }
        toolbar.setOnClickListener(
                new OnDoubleClickListener(() -> recyclerView.scrollToPosition(0)));

        Context context = getContext();
        if (null != context) {
            // 自定义分割线
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerViews.remove(getRecyclerViewKey());
    }

    @Override
    protected abstract int getLayoutRes();

    protected abstract String getRecyclerViewKey();

    protected abstract RecyclerView getRecyclerView();

}
