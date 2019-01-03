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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class PromptBar extends View {

    /** @hide */
    @IntDef({LENGTH_INDEFINITE, LENGTH_LONG, LENGTH_SHORT})
    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    private @interface Duration{}

    /**
     * Show the PromptBar for a short period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_INDEFINITE = 0;

    /**
     * Show the PromptBar for a short period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_LONG = 2;

    /**
     * Show the PromptBar for a short period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = 1;

    /** @hide */
    @IntDef({ACTION_TYPE_ICON, ACTION_TYPE_TEXT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ActionType {}

    /**
     *
     */
    public static final int ACTION_TYPE_ICON = 2;

    /**
     *
     */
    public static final int ACTION_TYPE_TEXT = 1;

    private int mDuration;
    private int mActionType;
//    private int mResId;
//    private Drawable mDrawable;
    private Bitmap mBitmap;

    public PromptBar(Context context) {
        super(context);
    }

    public PromptBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PromptBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PromptBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置显示时长。
     * Set how long to show the view for.
     * @see #LENGTH_INDEFINITE
     * @see #LENGTH_LONG
     * @see #LENGTH_SHORT
     */
    public PromptBar setDuration(@Duration int duration) {
        mDuration = duration;
        return this;
    }

    /**
     * 设置可让promptBar最右侧action->dismiss为图标或是文字。
     * @see #ACTION_TYPE_ICON
     * @see #ACTION_TYPE_TEXT
     */
    private PromptBar setActionType(@ActionType int actionType) {
        mActionType = actionType;
        return this;
    }

//    @Contract("_ -> !null")
    public PromptBar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    public PromptBar setText(@NonNull CharSequence message) {
//        final TextView tv =
        return this;
    }

    public PromptBar setAction(@DrawableRes int resId, @Nullable Resources.Theme theme) {
        return this.setAction(getContext().getResources().getDrawable(resId, theme));
    }

    public PromptBar setAction(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return this.setAction(((BitmapDrawable) drawable).getBitmap());
        } else {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            drawable.draw(canvas);
            return this.setAction(bitmap);
        }
    }

    public PromptBar setAction(Bitmap bitmap) {
        mBitmap = bitmap;
        return this;
    }

}
