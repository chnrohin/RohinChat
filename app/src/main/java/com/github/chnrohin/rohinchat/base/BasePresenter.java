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
 * @date 2018/11/3
 */
public abstract class BasePresenter<V extends BaseMvpView> {

    protected V mView;

    void bindView(V view) {
        mView = view;
    }

    void unbindView() {
        mView = null;
    }

    /**
     * 订阅
     */
    protected void subscribe() {}

    /**
     * 取消订阅
     */
    protected void unsubscribe() {}
}
