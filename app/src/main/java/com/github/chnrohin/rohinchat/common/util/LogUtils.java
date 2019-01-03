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

import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class LogUtils {

    private LogUtils() {
        throw new AssertionError("禁止实例化！");
    }

    /**
     * log日志文件前缀
     */
    private static String tagPrefix = "RohinChat";

    private static boolean debug = true;

    public static void i(CharSequence msg) {
        logger("i", msg);
    }

    public static void d(CharSequence msg) {
        logger("d", msg);
    }

    public static void e(CharSequence msg) {
        logger("e", msg);
    }

    public static void w(CharSequence msg) {
        logger("w", msg);
    }

    /**
     * @param type logger级别
     * @param o    logger内容
     */
    private static void logger(String type, CharSequence o) {
        if (!debug) {
            return;
        }
        String msg = o + "";
        String tag = getTag(getCallerStackTraceElement());
        switch (type) {
            case "i":
                Log.i(tag, msg);
            case "d":
                Log.d(tag, msg);
                break;
            case "e":
                Log.e(tag, msg);
                break;
            case "w":
                Log.w(tag, msg);
                break;
            default:
                Log.d(tag, msg);
                break;
        }
    }

    private static String getTag(StackTraceElement element) {
        // 占位符
        String tag = "%s.%s(Line:%d)";
        // 获取类名
        String callerClazzName = element.getClassName();

        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        // 替换
        tag = String.format(Locale.CHINA, tag, callerClazzName, element.getMethodName(),
                element.getLineNumber());
        tag = TextUtils.isEmpty(tagPrefix) ? tag : tagPrefix + ":"
                + tag;
        return tag;
    }

    /**
     * 获取线程状态
     */
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[5];
    }

}
