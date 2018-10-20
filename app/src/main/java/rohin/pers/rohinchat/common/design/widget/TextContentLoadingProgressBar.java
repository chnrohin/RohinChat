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

package rohin.pers.rohinchat.common.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import rohin.pers.rohinchat.R;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class TextContentLoadingProgressBar extends ProgressBar {

    private static final int MIN_SHOW_TIME = 500;
    private static final int MIN_DELAY = 500;

    long mStartTime = -1;

    boolean mPostedHide = false;

    boolean mPostedShow = false;

    boolean mDismissed = false;

    private Rect mRect;

    private Paint mPaint;

    private boolean mNoInvalidate;

    private String mText;

    private int mTextSize;

    private int mTextColor;

    private int mX, mY;

    private final Runnable mDelayedHide = () -> {
        mPostedHide = false;
        mStartTime = -1;
        setVisibility(View.GONE);
    };

    private final Runnable mDelayedShow = () -> {
        mPostedShow = false;
        if (!mDismissed) {
            mStartTime = System.currentTimeMillis();
            setVisibility(View.VISIBLE);
        }
    };

    public TextContentLoadingProgressBar(Context context) {
        this(context, null);
    }

    public TextContentLoadingProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextContentLoadingProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TextContentLoadingProgressBar(Context context, AttributeSet attrs,
                                         int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TextContentLoadingProgressBar, defStyleAttr, defStyleRes);

        init();

        mNoInvalidate = true;

        mText = a.getString(R.styleable.TextContentLoadingProgressBar_text);

        mTextSize = a.getDimensionPixelSize(
                R.styleable.TextContentLoadingProgressBar_textSize, mTextSize);

        mTextColor = a.getInt(R.styleable.TextContentLoadingProgressBar_textColor, Color.BLACK);

        mNoInvalidate = false;

        a.recycle();

        loadingText();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mText, mX, mY, mPaint);
    }

    @Override
    public void postInvalidate() {
        if (!mNoInvalidate) {
            super.postInvalidate();
        }
    }

    private void init() {
        mText = getResources().getString(R.string.loading_prompt);
        mTextSize = getResources().getDimensionPixelSize(R.dimen.text_size);
        if (mRect == null ) mRect = new Rect();
        if (mPaint == null ) mPaint = new Paint();
    }

    private void loadingText() {
        mPaint.getTextBounds(mText, 0, mText.length(), mRect);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mPaint.setAntiAlias(true);
    }

    private void removeCallbacks() {
        removeCallbacks(mDelayedHide);
        removeCallbacks(mDelayedShow);
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     */
    public synchronized void hide() {
        mDismissed = true;
        removeCallbacks(mDelayedShow);
        mPostedShow = false;
        long diff = System.currentTimeMillis() - mStartTime;
        if (diff >= MIN_SHOW_TIME || mStartTime == -1) {
            // The progress spinner has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            setVisibility(View.GONE);
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME - diff);
                mPostedHide = true;
            }
        }
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     */
    public synchronized void show() {
        // Reset the start time.
        mStartTime = -1;
        mDismissed = false;
        removeCallbacks(mDelayedHide);
        mPostedHide = false;
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY);
            mPostedShow = true;
        }
    }

}
