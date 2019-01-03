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

import com.github.chnrohin.rohinchat.common.Constants;
import com.github.chnrohin.rohinchat.common.util.LogUtils;
import com.github.chnrohin.rohinchat.common.util.ToastUtils;

/**
 * @author Rohin
 * @date 2018/11/3
 */
public abstract class BaseMvpFragment<V extends BaseMvpView, P extends BasePresenter<V>>
        extends BaseFragment implements BaseMvpView {

    protected P mPresenter;

    protected V mView;

    @Override
    protected void init() {
        super.init();

        if (mPresenter == null) {
            mPresenter = setPresenter();
        }

        try {
            mView = (V) this;
        } catch (ClassCastException e) {
            ToastUtils.showToast(getActivity(), "APP运行异常，错误代码："
                    + Constants.ERROR_CLASS_CAST_0306117);
            LogUtils.e("Class Cast Error!!");
            System.exit(0);
        }

        if (mPresenter != null) {
            mPresenter.bindView(mView);
        }
    }

    @Override
    protected void release() {
        super.release();

        mPresenter.unbindView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    /**
     * 为view设置presenter。
     * @return new Presenter具体类.
     */
    protected abstract P setPresenter();

    protected P getPresenter() {
        return mPresenter;
    }
}
