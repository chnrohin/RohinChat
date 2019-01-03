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

package com.github.chnrohin.rohinchat.common.util;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ToastUtils {

    public static final String TAG = "ToastUtils";

    /** @hide */
    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration{}

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     */
    public static final int LENGTH_SHORT = 0;

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     */
    public static final int LENGTH_LONG = 1;


    private static Toast toast = null;

    /**
     * 保存旧Toast与新内容进行重复性对比，若在指定时间内重复则不重新显示。
     */
    private static CharSequence oldMsg;

    /**
     * Toast 位于屏幕X轴的位置
     */
    private static final int xOffset = 0;

    /**
     * Toast 位于屏幕Y轴的位置
     */
    private static final int yOffset = new DisplayMetrics().heightPixels / 8;

    /**
     * Toast指定时间重复要求显示内容相同，则不显示。
     */
    private static long nTime = 0, oTime = 0;

    private ToastUtils() {throw new AssertionError("禁止实例化！");}

    public static void showToast(Context context, @StringRes int resId) {
        makeText(context, context.getText(resId), LENGTH_SHORT);
    }

    public static void showToast(Context context, CharSequence text) {
        makeText(context, text, ToastUtils.LENGTH_SHORT);
    }

    public static void showToast(Context context, @StringRes int resId, @Duration int duration) {
        makeText(context, context.getText(resId), duration);
    }

    public static void showToast(Context context, CharSequence text, @Duration int duration ) {
        makeText(context, text, duration);
    }

    private static void makeText(Context context,@NonNull CharSequence text, @Duration int duration) {
        int y = yOffset == 0 ? 300 : yOffset;
        if (null == toast) {
            toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.BOTTOM, xOffset, y);
            toast.show();
            oTime = System.currentTimeMillis();
        } else {
            nTime = System.currentTimeMillis();
            toast.setGravity(Gravity.BOTTOM, xOffset, y);
            if (text.equals(oldMsg)) {
                if (nTime - oTime > duration) {
                    toast.show();
                }
            } else {
                oldMsg = text;
                toast.setText(text);
                toast.show();
            }
        }
        oTime = nTime;
    }

}
