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

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

import com.github.chnrohin.rohinchat.common.util.ActivityUtils;
import com.github.chnrohin.rohinchat.common.util.AdaptScreen;
import com.github.chnrohin.rohinchat.common.util.PermissionUtils;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        不要在 Activity#onDestroy()内执行释放资源的工作，例如一些工作线程的
        销毁和停止，因为 onDestroy()执行的时机可能较晚。可根据实际需要，在
        Activity#onPause()/onStop()中结合 isFinishing() 的判断来执行。
         */
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        /*
        添加 Fragment 时，确保 FragmentTransaction#commit()在
        Activity#onPostResume()或者 FragmentActivity#onResumeFragments()内调用。
        不要随意使用 FragmentTransaction#commitAllowingStateLoss()来代替，任何
        commitAllowingStateLoss()的使用必须经过 code review，确保无负面影响。
         */
        if (getFragmentInstance() != null && getContainerViewId() != 0) {
            attachFragment();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 子类继承并实现此方法指定 LayoutRes。
     * @return res/layout
     */
    @LayoutRes
    protected abstract int getLayoutResID();

    /**
     * 要绑定的fragment的实例。
     * @return extends BaseFragment
     */
    protected abstract BaseFragment getFragmentInstance();

    /**
     * 要装载fragment的容器的Id。
     * @return IdRes
     */
    @IdRes
    protected abstract int getContainerViewId();

    /**
     * 绑定fragment。
     */
    private void attachFragment() {
        ActivityUtils.Companion.addFragmentToActivity(getSupportFragmentManager(),
                getFragmentInstance(),
                getContainerViewId());
    }

    protected void init() {
        // 默认适配水平宽度
        AdaptScreen.Companion.adaptScreen4Horizontal(getApplication(), this);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
    }

    protected void release() {

    }

}
