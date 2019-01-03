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

package com.github.chnrohin.rohinchat.common.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.github.chnrohin.rohinchat.R;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener,
        TextWatcher, View.OnTouchListener, TextView.OnEditorActionListener,
        Animation.AnimationListener {

    private Drawable mDrawable;

    private static final long DURATION_MILLIS = 1000L;
    private static final float CYCLES = 5.0f;
    private static final float TO_X_DELTA = 20.0f;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDrawable = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (hasFocus) {
        setClearIconVisible(s.length() > 0);
//        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 根据源码可知右侧Drawable索引值为2
            int rightDrawable = 2;
            if (getCompoundDrawables()[rightDrawable] != null) {
                int startX = getWidth() - getTotalPaddingRight();
                int endX = getWidth();
                boolean touchable = event.getX() > startX && event.getX() < endX;
                if (touchable) {
                    this.setText("");
                    setClearIconVisible(false);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
//        this.hasFocus = hasFocus;
        if (!hasFocus) {
            closeSoftKeyBoard();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (getText() != null && "".equals(getText().toString().trim())) {
            setShakeAnimation();
        }
        return false;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        this.clearAnimation();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    /**
     * 初始化
     */
    private void init() {
        // 初始化图标样式
        initClearIcon();

        // 默认隐藏图标
        setClearIconVisible(false);

        // 注册监听
        setOnTouchListener(this);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        setOnEditorActionListener(this);
    }

    /**
     * 若XML未设置自定义图标样式，则默认获取ic_cet_clear图标
     */
    private void initClearIcon() {
        mDrawable = getCompoundDrawables()[2];
        if (mDrawable == null) {
            mDrawable = getResources().getDrawable(
                    R.drawable.design_widget_ic_cet_clear_24dp, getContext().getTheme());
        }
        mDrawable.setBounds(0, 0,
                mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible - 隐藏/显示 清除图标
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                right, getCompoundDrawables()[3]);
    }

    /**
     * 调用super.setAnimation实现动画，默认参数
     */
    public void setShakeAnimation() {
        setShakeAnimation(CYCLES, DURATION_MILLIS);
    }

    /**
     * 调用super.setAnimation实现动画，默认参数
     */
    public void setShakeAnimation(float cycles) {
        setShakeAnimation(cycles, DURATION_MILLIS);
    }

    /**
     * 调用super.setAnimation实现动画，默认参数
     */
    public void setShakeAnimation(long durationMillis) {
        setShakeAnimation(CYCLES, durationMillis);
    }

    /**
     * 调用super.setAnimation实现动画，自定义参数
     *
     * @param cycles - 弹动次数
     */
    private void setShakeAnimation(float cycles, long durationMillis) {
        setAnimation(shakeAnimation(cycles, durationMillis));
    }

    /**
     * Android动画中cycleInterpolator定义属性android:cycles可以是一个动画反复执行，
     * 从而达到一个动画左右、垂直或者其他如斜方向上的反复弹动（摆动）。
     *
     * @param cycles 弹动次数
     * @return 动画对象
     */
    private Animation shakeAnimation(float cycles, long durationMillis) {
        Animation translateAnimation = new TranslateAnimation(0,
                TO_X_DELTA, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(cycles));
        translateAnimation.setDuration(durationMillis);
        return translateAnimation;
    }

    public void closeSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager)
                this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive() && getWindowToken() != null) {
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }
}
