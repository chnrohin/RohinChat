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

package com.github.chnrohin.rohinchat.acts;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.base.BaseActivity;
import com.github.chnrohin.rohinchat.base.BaseFragment;
import com.github.chnrohin.rohinchat.common.util.AdaptScreen;
import com.github.chnrohin.rohinchat.data.source.local.IInitLocalDb;
import com.github.chnrohin.rohinchat.data.source.local.InitLocalDb;

import butterknife.BindString;
import butterknife.BindView;

/**
 * @author Rohin
 * @date 2018/11/23
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_splash_countDown)
    TextView mCountDownTv;

    @BindString(R.string.splash_count_down)
    String text;

    private long millisInFuture = 3000;
    private long countDownInterval = 1000;

    private SplashCountDownTimer mSplashCountDownTimer;

    @Override
    protected int getLayoutResID() {
        return R.layout.splash_activity;
    }

    @Override
    protected BaseFragment getFragmentInstance() {
        return null;
    }

    @Override
    protected int getContainerViewId() {
        return 0;
    }

    @Override
    protected void init() {
        AdaptScreen.Companion.cancelAdaptScreen(getApplication(), this);
        // 全屏
        Window window = getWindow();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        window.getDecorView().setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);

        super.init();

//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("demo-pool-%d").build();
//        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//
//        singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
//        singleThreadPool.shutdown();

        int t = 0;
        Thread thread = new Thread(() -> {
            mSplashCountDownTimer = new SplashCountDownTimer(millisInFuture, countDownInterval);
            mSplashCountDownTimer.start();
        });
        mCountDownTv.post(thread);
    }

    @Override
    protected void release() {
        super.release();
        mSplashCountDownTimer.cancel();
    }

    /**
     * 初始化数据库
     */
    private void initDb() {
        IInitLocalDb localDb = new InitLocalDb();
        localDb.init();
    }

    /**
     * 跳转至主界面
     */
    private void jump2Main() {
        MainActivity.newIntent(this);
    }

    class SplashCountDownTimer extends CountDownTimer {

        SplashCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mCountDownTv.setText(String.format(text, millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            jump2Main();
        }
    }

}
