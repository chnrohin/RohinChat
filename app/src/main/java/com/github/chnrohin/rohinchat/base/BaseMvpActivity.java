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

/**
 * @author Rohin
 * @date 2018/11/2
 */
public abstract class BaseMvpActivity<V extends BaseMvpView, P extends BasePresenter<V>>
        extends BaseActivity implements BaseMvpView {

    private V mView;

    private P mPresenter;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        attachPresenter();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    /**
     * 指定presenter。
     */
    protected abstract void attachPresenter();
}
