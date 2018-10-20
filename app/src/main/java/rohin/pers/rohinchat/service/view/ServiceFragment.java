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

package rohin.pers.rohinchat.service.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.R;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ServiceFragment extends BaseFragment {

    public static ServiceFragment getInstance() {
        return new ServiceFragment();
    }

    public static ServiceFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString("FRAGMENT", value);
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ContentLoadingProgressBar loadingBar = view.findViewById(R.id.progress_bar_loading);
        loadingBar.show();
        return view;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.service_fragment_service;
    }

}
